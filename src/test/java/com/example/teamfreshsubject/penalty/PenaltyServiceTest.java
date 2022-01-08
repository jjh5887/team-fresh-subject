package com.example.teamfreshsubject.penalty;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.teamfreshsubject.exception.VocException;
import com.example.teamfreshsubject.penalty.request.PenaltyRequest;
import com.example.teamfreshsubject.penalty.response.PenaltyResponse;
import com.example.teamfreshsubject.voc.PartyCode;
import com.example.teamfreshsubject.voc.Voc;
import com.example.teamfreshsubject.voc.VocService;
import com.example.teamfreshsubject.voc.VocStatus;

@SpringBootTest
class PenaltyServiceTest {

	@Autowired
	PenaltyService penaltyService;

	@Autowired
	VocService vocService;

	@Test
	public void create() {
		// When
		Long vocId = -1L;
		for (Voc voc : vocService.getAll()) {
			if (voc.getParty() == PartyCode.CARRIER && voc.getStatus() == VocStatus.COMPENSATION) {
				vocId = voc.getId();
				break;
			}
		}

		PenaltyRequest penaltyRequest = PenaltyRequest.builder()
			.description("패널티 금액: 50000원")
			.vocId(vocId)
			.build();

		PenaltyResponse penaltyResponse = penaltyService.create(penaltyRequest);
		Penalty penalty = penaltyService.get(penaltyResponse.getId());
		Voc voc = vocService.get(vocId);

		// Then
		assertThat(penalty.getDescription()).isEqualTo(penaltyRequest.getDescription());
		assertThat(penalty.getChecked()).isEqualTo(false);
		assertThat(penalty.getObjection()).isEqualTo(false);
		assertThat(penalty.getVoc().getId()).isEqualTo(penaltyRequest.getVocId());
		assertThat(voc.getStatus()).isEqualTo(VocStatus.PENALTY);
	}

	@Test
	public void create_empty_description() {
		// When
		Long vocId = -1L;
		for (Voc voc : vocService.getAll()) {
			if (voc.getParty() == PartyCode.CARRIER && voc.getStatus() == VocStatus.COMPENSATION) {
				vocId = voc.getId();
				break;
			}
		}

		assertThat(vocId).isNotEqualTo(-1L);
		PenaltyRequest penaltyRequest = PenaltyRequest.builder()
			.vocId(vocId)
			.build();

		// Then
		assertThrows(DataIntegrityViolationException.class, () -> {
			penaltyService.create(penaltyRequest);
		});
	}

	@Test
	public void create_at_client_party_voc() {
		// When
		Long vocId = -1L;
		for (Voc voc : vocService.getAll()) {
			if (voc.getParty() == PartyCode.CLIENT && voc.getStatus() == VocStatus.COMPENSATION) {
				vocId = voc.getId();
				break;
			}
		}

		PenaltyRequest penaltyRequest = PenaltyRequest.builder()
			.description("패널티 금액: 5000원")
			.vocId(vocId)
			.build();

		// Then
		assertThrows(VocException.class, () -> {
			penaltyService.create(penaltyRequest);
		});
	}

	@Test
	public void create_at_has_penalty() {
		// When
		Long vocId = -1L;
		for (Voc voc : vocService.getAll()) {
			if (voc.getParty() == PartyCode.CLIENT && voc.getStatus() == VocStatus.PENALTY) {
				vocId = voc.getId();
				break;
			}
		}

		PenaltyRequest penaltyRequest = PenaltyRequest.builder()
			.description("패널티 금액: 5000원")
			.vocId(vocId)
			.build();

		// Then
		assertThrows(VocException.class, () -> {
			penaltyService.create(penaltyRequest);
		});
	}

	@Test
	public void create_at_does_not_have_compensation() {
		// When
		Long vocId = -1L;
		for (Voc voc : vocService.getAll()) {
			if (voc.getParty() == PartyCode.CLIENT && voc.getStatus() == VocStatus.CREATE) {
				vocId = voc.getId();
				break;
			}
		}

		PenaltyRequest penaltyRequest = PenaltyRequest.builder()
			.description("패널티 금액: 5000원")
			.vocId(vocId)
			.build();

		// Then
		assertThrows(VocException.class, () -> {
			penaltyService.create(penaltyRequest);
		});
	}

	@Test
	public void check() {
		// When
		Long vocId = -1L;
		for (Voc voc : vocService.getAll()) {
			if (voc.getParty() == PartyCode.CARRIER && voc.getStatus() == VocStatus.COMPENSATION) {
				vocId = voc.getId();
				break;
			}
		}

		PenaltyRequest penaltyRequest = PenaltyRequest.builder()
			.description("패널티 금액: 5000원")
			.vocId(vocId)
			.build();
		PenaltyResponse penaltyResponse = penaltyService.create(penaltyRequest);
		penaltyService.check(penaltyResponse.getId(), false);
		Penalty penalty = penaltyService.get(penaltyResponse.getId());

		// Then
		assertThat(penalty.getObjection()).isEqualTo(false);
		assertThat(penalty.getChecked()).isEqualTo(true);
	}
}