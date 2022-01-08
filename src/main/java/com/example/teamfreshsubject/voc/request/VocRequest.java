package com.example.teamfreshsubject.voc.request;

import com.example.teamfreshsubject.account.Account;
import com.example.teamfreshsubject.voc.PartyCode;
import com.example.teamfreshsubject.voc.Voc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "VOC 등록 요청")
public class VocRequest {

	@ApiModelProperty(value = "귀책 당사자")
	private PartyCode party;

	@ApiModelProperty(value = "귀책 내용")
	private String description;

	@ApiModelProperty(value = "운송 기사 id")
	private Long driverId;

	@ApiModelProperty(value = "담당자 id")
	private Long managerId;

	public Voc toEntity() {
		return Voc.builder()
			.party(party)
			.description(description)
			.driver(Account.builder().id(driverId).build())
			.manager(Account.builder().id(managerId).build())
			.build();
	}
}
