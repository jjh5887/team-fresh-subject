package com.example.teamfreshsubject.penalty;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamfreshsubject.penalty.request.PenaltyRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "패널티 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/penalty", produces = MediaType.APPLICATION_JSON_VALUE)
public class PenaltyController {

	private final PenaltyService penaltyService;

	@ApiOperation(value = "패널티 등록")
	@PostMapping
	public ResponseEntity create(@RequestBody PenaltyRequest request) {
		return ResponseEntity.ok(penaltyService.create(request));
	}

	@ApiOperation(value = "기사 확인 여부 등록")
	@PutMapping("/{id}/{objection}")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "패널티 id", dataType = "number"),
		@ApiImplicitParam(name = "objection", value = "이의 제기 여부", allowableValues = "true, false")
	})
	public ResponseEntity check(@PathVariable Long id, @PathVariable Boolean objection) {
		return ResponseEntity.ok(penaltyService.check(id, objection));
	}
}
