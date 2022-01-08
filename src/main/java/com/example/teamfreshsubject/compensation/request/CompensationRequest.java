package com.example.teamfreshsubject.compensation.request;

import com.example.teamfreshsubject.compensation.Compensation;
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
@ApiModel(value = "배상 등록 요청")
public class CompensationRequest {

	@ApiModelProperty(value = "배상금액")
	private Long amount;

	@ApiModelProperty(value = "VOC id")
	private Long vocId;

	public Compensation toEntity() {
		return Compensation.builder()
			.amount(amount)
			.voc(Voc.builder().id(vocId).build())
			.build();
	}
}
