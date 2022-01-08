package com.example.teamfreshsubject.voc;

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

import com.example.teamfreshsubject.exception.VocException;
import com.example.teamfreshsubject.voc.request.VocRequest;
import com.example.teamfreshsubject.voc.response.VocCreateResponse;

@SpringBootTest
class VocServiceTest {

	@Autowired
	VocService vocService;

	@Autowired
	VocRepository vocRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Test
	public void getAll() {
		// When
		PersistenceUnitUtil util = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
		List<Voc> all = vocService.getAll();

		// Then
		assertThat(vocRepository.findAll().size()).isEqualTo(all.size());
		for (Voc voc : all) {
			boolean loadedCompensation = util.isLoaded(voc.getCompensation());
			boolean loadedPenalty = util.isLoaded(voc.getPenalty());
			boolean loadedDriver = util.isLoaded(voc.getDriver());
			boolean loadedManager = util.isLoaded(voc.getManager());
			assertThat(loadedManager && loadedDriver && loadedPenalty && loadedCompensation).isEqualTo(true);
		}
	}

	@Test
	public void create() {
		// When
		VocRequest vocRequest = VocRequest.builder()
			.party(PartyCode.CLIENT)
			.description("test VOC")
			.driverId(1L)
			.managerId(10L)
			.build();
		VocCreateResponse vocCreateResponse = vocService.create(vocRequest);
		Voc voc = vocService.get(vocCreateResponse.getId());

		// Then
		assertThat(voc.getId()).isEqualTo(vocCreateResponse.getId());
		assertThat(voc.getDescription()).isEqualTo(vocCreateResponse.getDescription());
		assertThat(voc.getParty()).isEqualTo(vocCreateResponse.getParty());
		assertThat(voc.getDriver().getId()).isEqualTo(vocCreateResponse.getDriverId());
		assertThat(voc.getManager().getId()).isEqualTo(vocCreateResponse.getManagerId());
		assertThat(voc.getStatus()).isEqualTo(vocCreateResponse.getStatus());
	}

	@Test
	public void create_empty_party() {
		// When
		VocRequest vocRequest = VocRequest.builder()
			.description("test VOC")
			.driverId(1L)
			.managerId(10L)
			.build();

		// Then
		assertThrows(DataIntegrityViolationException.class, () -> {
			vocService.create(vocRequest);
		});
	}

	@Test
	public void get_wrong_id_voc() {
		// When
		long id = 99L;
		assertThat(vocRepository.existsById(id)).isEqualTo(false);

		// Then
		assertThrows(VocException.class, () -> {
			vocService.get(id);
		});
	}

}