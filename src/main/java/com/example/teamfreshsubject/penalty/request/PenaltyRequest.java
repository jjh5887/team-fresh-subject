package com.example.teamfreshsubject.penalty.request;

import com.example.teamfreshsubject.penalty.Penalty;
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
@ApiModel(value = "패널티 등록 요청")
public class PenaltyRequest {

	@ApiModelProperty(value = "패널티 내용")
	private String description;

	@ApiModelProperty(value = "VOC id")
	private Long vocId;

	public Penalty toEntity() {
		return Penalty.builder()
			.description(description)
			.checked(false)
			.objection(false)
			.voc(Voc.builder()
				.id(vocId)
				.build())
			.build();
	}
}
