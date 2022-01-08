package com.example.teamfreshsubject.penalty;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.teamfreshsubject.exception.ErrorCode;
import com.example.teamfreshsubject.exception.VocException;
import com.example.teamfreshsubject.penalty.request.PenaltyRequest;
import com.example.teamfreshsubject.penalty.response.PenaltyResponse;
import com.example.teamfreshsubject.voc.PartyCode;
import com.example.teamfreshsubject.voc.Voc;
import com.example.teamfreshsubject.voc.VocService;
import com.example.teamfreshsubject.voc.VocStatus;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class PenaltyService {

	private final PenaltyRepository penaltyRepository;
	private final VocService vocService;

	@Transactional
	public Penalty save(Penalty penalty) {
		return penaltyRepository.save(penalty);
	}

	@Transactional
	public PenaltyResponse create(PenaltyRequest request) {
		Voc voc = vocService.get(request.getVocId());
		if (voc.getParty() != PartyCode.CARRIER) {
			throw new VocException(ErrorCode.AttributablePartyIsNotCarrier);
		}
		if (voc.getStatus() == VocStatus.CREATE) {
			throw new VocException(ErrorCode.VocDoesNotHaveCompensation);
		}
		if (voc.getStatus() == VocStatus.PENALTY) {
			throw new VocException(ErrorCode.VocHasAlreadyBeenRegisteredPenalty);
		}
		voc.setStatus(VocStatus.PENALTY);
		vocService.save(voc);
		return save(request.toEntity()).toResponse();
	}

	@SneakyThrows
	@Transactional(readOnly = true)
	public Penalty get(Long id) {
		return penaltyRepository.findById(id).orElseThrow(() -> {
			throw new VocException(ErrorCode.PenaltyNotFound);
		});
	}

	public PenaltyResponse check(Long id, Boolean objection) {
		Penalty penalty = get(id);
		penalty.setChecked(true);
		penalty.setObjection(objection);
		return save(penalty).toResponse();
	}

}
