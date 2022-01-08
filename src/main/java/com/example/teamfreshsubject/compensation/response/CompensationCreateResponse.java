package com.example.teamfreshsubject.compensation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompensationCreateResponse {

	private Long id;

	private Long amount;

	private Long vocId;
}
