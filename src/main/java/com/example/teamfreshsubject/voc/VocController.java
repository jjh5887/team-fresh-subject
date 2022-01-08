package com.example.teamfreshsubject.voc;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamfreshsubject.voc.request.VocRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "VOC API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/voc", produces = MediaType.APPLICATION_JSON_VALUE)
public class VocController {

	private final VocService vocService;

	@ApiOperation(value = "VOC 목록")
	@GetMapping
	public ResponseEntity getAll() {
		return ResponseEntity.ok(vocService.getAllResponses());
	}

	@ApiOperation(value = "VOC 등록")
	@PostMapping
	public ResponseEntity create(@RequestBody VocRequest request) {
		return ResponseEntity.ok(vocService.create(request));
	}
}
