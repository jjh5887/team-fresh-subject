package com.example.teamfreshsubject.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
	VocNotFound(400, "없는 VOC 입니다."),
	PenaltyNotFound(400, "없는 페널티 입니다."),
	AttributablePartyCanNotRegistCompensation(400, "귀책 당사자는 배상을 등록할 수 없습니다."),
	AttributablePartyIsNotCarrier(400, "귀책이 운송사가 아닙니다."),
	VocHasAlreadyBeenRegisteredPenalty(400, "이미 패널티가 등록되었습니다."),
	VocHasAlreadyBeenRegisteredCompensation(400, "이미 배상이 청구되었습니다."),
	VocDoesNotHaveCompensation(400, "아직 배상이 청구되어 있지 않습니다.");;

	private final int status;
	private final String message;

	ErrorCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
