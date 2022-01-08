package com.example.teamfreshsubject.compensation.response;

import com.example.teamfreshsubject.voc.response.VocResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompensationDetailResponse {

	private Long id;

	private Long amount;

	private VocResponse voc;
}
