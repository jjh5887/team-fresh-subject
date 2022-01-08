package com.example.teamfreshsubject.voc;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.teamfreshsubject.exception.ErrorCode;
import com.example.teamfreshsubject.exception.VocException;
import com.example.teamfreshsubject.voc.request.VocRequest;
import com.example.teamfreshsubject.voc.response.VocCreateResponse;
import com.example.teamfreshsubject.voc.response.VocDetailResponse;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class VocService {

	private final VocQueryRepository vocQueryRepository;
	private final VocRepository vocRepository;

	@Transactional
	public Voc save(Voc voc) {
		return vocRepository.save(voc);
	}

	public VocCreateResponse create(VocRequest request) {
		return save(request.toEntity()).toCreateResponse();
	}

	@Transactional(readOnly = true)
	public List<Voc> getAll() {
		return vocQueryRepository.findAll();
	}

	public List<VocDetailResponse> getAllResponses() {
		return getAll().stream().map(Voc::toDetailResponse).collect(Collectors.toList());
	}

	@SneakyThrows
	@Transactional(readOnly = true)
	public Voc get(Long id) {
		return vocRepository.findById(id).orElseThrow(() -> {
			throw new VocException(ErrorCode.VocNotFound);
		});
	}
}
