package com.example.teamfreshsubject.account;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.teamfreshsubject.account.response.AccountResponse;
import com.example.teamfreshsubject.voc.PartyCode;
import com.example.teamfreshsubject.voc.Voc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 20, nullable = false)
	private String name;

	@Column(length = 50, nullable = false)
	private String company;

	@Column(length = 50, nullable = false, unique = true)
	private String contactNum;

	private PartyCode partyCode;

	@OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
	private Set<Voc> vocsDriver = new HashSet<>();

	@OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
	private Set<Voc> vocsManager = new HashSet<>();

	public AccountResponse toResponse() {
		return AccountResponse.builder()
			.id(id)
			.name(name)
			.company(company)
			.contact_num(contactNum)
			.partyCode(partyCode)
			.build();
	}
}
