package com.example.teamfreshsubject.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.teamfreshsubject.exception.response.ErrorResponse;

@RestControllerAdvice("com.example.teamfreshsubject")
public class ExceptionController {

	@ExceptionHandler
	public ResponseEntity errorHandle(VocException exception) {
		return ResponseEntity
			.status(exception.getStatus())
			.body(ErrorResponse.builder().status(exception.getStatus())
				.message(exception.getMessage()).build());
	}
}
