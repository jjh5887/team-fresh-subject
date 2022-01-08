package com.example.teamfreshsubject.compensation;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.example.teamfreshsubject.compensation.request.CompensationRequest;
import com.example.teamfreshsubject.compensation.response.CompensationCreateResponse;
import com.example.teamfreshsubject.exception.VocException;
import com.example.teamfreshsubject.voc.PartyCode;
import com.example.teamfreshsubject.voc.Voc;
import com.example.teamfreshsubject.voc.VocService;
import com.example.teamfreshsubject.voc.VocStatus;

import lombok.SneakyThrows;

@SpringBootTest
class CompensationServiceTest {

	@Autowired
	CompensationService compensationService;

	@Autowired
	CompensationRepository compensationRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	VocService vocService;

	@Test
	public void getAll() {
		// When
		PersistenceUnitUtil util = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
		List<Compensation> all = compensationService.getAll();

		// Then
		assertThat(compensationRepository.findAll().size()).isEqualTo(all.size());
		for (Compensation compensation : all) {
			boolean loadedVoc = util.isLoaded(compensation.getVoc());
			boolean loadedVocDriver = util.isLoaded(compensation.getVoc().getDriver());
			boolean loadedVocManager = util.isLoaded(compensation.getVoc().getManager());
			boolean loadedVocPenalty = util.isLoaded(compensation.getVoc().getPenalty());
			assertThat(loadedVoc && loadedVocDriver && loadedVocManager && loadedVocPenalty).isEqualTo(true);
		}
	}

	@Test
	@SneakyThrows
	public void create() {
		// When
		Long vocId = -1L;
		List<Voc> all = vocService.getAll();
		for (Voc voc : all) {
			if (voc.getParty() == PartyCode.CARRIER && voc.getStatus() == VocStatus.CREATE) {
				vocId = voc.getId();
				break;
			}
		}

		CompensationRequest compensationRequest = CompensationRequest.builder()
			.amount(20000L)
			.vocId(vocId)
			.build();
		CompensationCreateResponse compensationCreateResponse = compensationService.create(compensationRequest);
		Compensation compensation = compensationRepository.findById(compensationCreateResponse.getId())
			.orElseThrow(() -> {
				throw new RuntimeException();
			});

		Voc voc = vocService.get(vocId);

		// Then
		assertThat(compensationRequest.getVocId()).isEqualTo(compensation.getVoc().getId());
		assertThat(compensationRequest.getAmount()).isEqualTo(compensation.getAmount());
		assertThat(voc.getStatus()).isEqualTo(VocStatus.COMPENSATION);

	}

	@Test
	public void create_empty_amount_or_voc() {
		// When
		Long vocId = -1L;
		List<Voc> all = vocService.getAll();
		for (Voc voc : all) {
			if (voc.getParty() == PartyCode.CARRIER && voc.getStatus() == VocStatus.CREATE) {
				vocId = voc.getId();
				break;
			}
		}

		CompensationRequest emptyAmountCompensationRequest = CompensationRequest.builder()
			.vocId(vocId)
			.build();
		CompensationRequest emptyVocCompensationRequest = CompensationRequest.builder()
			.amount(200L)
			.build();

		// Then
		assertThrows(DataIntegrityViolationException.class, () -> {
			compensationService.create(emptyAmountCompensationRequest);
		});
		assertThrows(InvalidDataAccessApiUsageException.class, () -> {
			compensationService.create(emptyVocCompensationRequest);
		});
	}

	@Test
	public void create_at_client_party_voc() {
		// When
		Long vocId = -1L;
		List<Voc> all = vocService.getAll();
		for (Voc voc : all) {
			if (voc.getParty() == PartyCode.CLIENT && voc.getStatus() == VocStatus.CREATE) {
				vocId = voc.getId();
				break;
			}
		}

		CompensationRequest compensationRequest = CompensationRequest.builder()
			.amount(1200L)
			.vocId(vocId)
			.build();

		// Then
		assertThrows(VocException.class, () -> {
			compensationService.create(compensationRequest);
		});
	}

	@Test
	public void create_at_has_compensation() {
		// When
		Long vocId = -1L;
		List<Voc> all = vocService.getAll();
		for (Voc voc : all) {
			if (voc.getParty() == PartyCode.CARRIER && voc.getStatus() == VocStatus.COMPENSATION) {
				vocId = voc.getId();
				break;
			}
		}

		CompensationRequest compensationRequest = CompensationRequest.builder()
			.amount(1200L)
			.vocId(vocId)
			.build();

		// Then
		assertThrows(VocException.class, () -> {
			compensationService.create(compensationRequest);
		});
	}
}