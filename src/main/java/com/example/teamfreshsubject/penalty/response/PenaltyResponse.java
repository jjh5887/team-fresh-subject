package com.example.teamfreshsubject.penalty.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyResponse {

	private Long id;

	private String description;

	private Boolean checked;

	private Boolean objection;
}
