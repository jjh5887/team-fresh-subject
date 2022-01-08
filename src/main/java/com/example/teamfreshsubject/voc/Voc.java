package com.example.teamfreshsubject.voc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import com.example.teamfreshsubject.account.Account;
import com.example.teamfreshsubject.compensation.Compensation;
import com.example.teamfreshsubject.penalty.Penalty;
import com.example.teamfreshsubject.voc.response.VocCreateResponse;
import com.example.teamfreshsubject.voc.response.VocDetailResponse;
import com.example.teamfreshsubject.voc.response.VocResponse;

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
public class Voc {

	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private PartyCode party;

	@Column(length = 200)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private VocStatus status;

	@OneToOne(mappedBy = "voc", fetch = FetchType.LAZY)
	private Compensation compensation;

	@OneToOne(mappedBy = "voc", fetch = FetchType.LAZY)
	private Penalty penalty;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_id")
	private Account driver;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manger_id")
	private Account manager;

	public VocDetailResponse toDetailResponse() {
		VocDetailResponse vocDetailResponse = VocDetailResponse.builder()
			.id(id)
			.party(party)
			.description(description)
			.status(status)
			.driver(driver.toResponse())
			.manager(manager.toResponse())
			.build();
		if (compensation != null) {
			vocDetailResponse.setCompensation(compensation.toResponse());
		}
		if (penalty != null) {
			vocDetailResponse.setPenalty(penalty.toResponse());
		}
		return vocDetailResponse;
	}

	public VocResponse toResponse() {
		VocResponse vocResponse = VocResponse.builder()
			.id(id)
			.party(party)
			.description(description)
			.status(status)
			.driver(driver.toResponse())
			.manager(manager.toResponse())
			.build();
		if (penalty != null) {
			vocResponse.setPenalty(penalty.toResponse());
		}
		return vocResponse;
	}

	public VocCreateResponse toCreateResponse() {
		return VocCreateResponse.builder()
			.id(id)
			.party(party)
			.description(description)
			.status(status)
			.driverId(driver.getId())
			.managerId(manager.getId())
			.build();
	}

	@PrePersist
	private final void setUp() {
		status = VocStatus.CREATE;
	}
}
