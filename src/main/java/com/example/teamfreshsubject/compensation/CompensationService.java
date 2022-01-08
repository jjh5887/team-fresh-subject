package com.example.teamfreshsubject.compensation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.teamfreshsubject.compensation.request.CompensationRequest;
import com.example.teamfreshsubject.compensation.response.CompensationCreateResponse;
import com.example.teamfreshsubject.compensation.response.CompensationDetailResponse;
import com.example.teamfreshsubject.exception.ErrorCode;
import com.example.teamfreshsubject.exception.VocException;
import com.example.teamfreshsubject.voc.PartyCode;
import com.example.teamfreshsubject.voc.Voc;
import com.example.teamfreshsubject.voc.VocService;
import com.example.teamfreshsubject.voc.VocStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompensationService {

	private final CompensationRepository compensationRepository;
	private final CompensationQueryRepository compensationQueryRepository;
	private final VocService vocService;

	@Transactional(readOnly = true)
	public List<Compensation> getAll() {
		return compensationQueryRepository.findAll();
	}

	public List<CompensationDetailResponse> getAllResponses() {
		return getAll().stream().map(Compensation::toDetailResponse).collect(Collectors.toList());
	}

	@Transactional
	public Compensation save(Compensation compensation) {
		return compensationRepository.save(compensation);
	}

	@Transactional
	public CompensationCreateResponse create(CompensationRequest request) {
		Voc voc = vocService.get(request.getVocId());
		if (voc.getParty() == PartyCode.CLIENT) {
			throw new VocException(ErrorCode.AttributablePartyCanNotRegistCompensation);
		}
		if (voc.getStatus() != VocStatus.CREATE) {
			throw new VocException(ErrorCode.VocHasAlreadyBeenRegisteredCompensation);
		}
		voc.setStatus(VocStatus.COMPENSATION);
		vocService.save(voc);
		Compensation compensation = save(request.toEntity());
		return compensation.toCreateResponse();
	}
}
