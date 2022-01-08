package com.example.teamfreshsubject.voc.response;

import com.example.teamfreshsubject.account.response.AccountResponse;
import com.example.teamfreshsubject.penalty.response.PenaltyResponse;
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
public class VocResponse {

	private Long id;

	private PartyCode party;

	private String description;

	private VocStatus status;

	private AccountResponse driver;

	private AccountResponse manager;

	private PenaltyResponse penalty;
}
