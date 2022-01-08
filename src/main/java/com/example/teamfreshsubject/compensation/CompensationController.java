package com.example.teamfreshsubject.compensation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamfreshsubject.compensation.request.CompensationRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "배상 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/compensation", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompensationController {

	private final CompensationService compensationService;

	@ApiOperation(value = "배상정보 목록")
	@GetMapping
	public ResponseEntity getAll() {
		return ResponseEntity.ok(compensationService.getAllResponses());
	}

	@ApiOperation(value = "배상 등록")
	@PostMapping
	public ResponseEntity create(@RequestBody CompensationRequest request) {
		return ResponseEntity.ok(compensationService.create(request));
	}
}
