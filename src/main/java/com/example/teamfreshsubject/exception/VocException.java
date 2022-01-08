package com.example.teamfreshsubject.exception;

import lombok.Data;

@Data
public class VocException extends RuntimeException {

	private int status;

	public VocException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.status = errorCode.getStatus();
	}
}

