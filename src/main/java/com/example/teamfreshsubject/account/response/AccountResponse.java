package com.example.teamfreshsubject.account.response;

import com.example.teamfreshsubject.voc.PartyCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

	private Long id;

	private String name;

	private String company;

	private String contact_num;

	private PartyCode partyCode;
}
