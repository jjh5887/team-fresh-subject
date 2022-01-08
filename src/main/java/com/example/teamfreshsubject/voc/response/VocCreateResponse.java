package com.example.teamfreshsubject.voc.response;

import com.example.teamfreshsubject.voc.PartyCode;
import com.example.teamfreshsubject.voc.VocStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VocCreateResponse {

	private Long id;

	private PartyCode party;

	private String description;

	private VocStatus status;

	private Long driverId;

	private Long managerId;
}
