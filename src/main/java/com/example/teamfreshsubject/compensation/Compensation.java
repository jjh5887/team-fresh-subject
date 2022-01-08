package com.example.teamfreshsubject.compensation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.example.teamfreshsubject.compensation.response.CompensationCreateResponse;
import com.example.teamfreshsubject.compensation.response.CompensationDetailResponse;
import com.example.teamfreshsubject.compensation.response.CompensationResponse;
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
public class Compensation {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private Long amount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "voc_id", nullable = false)
	private Voc voc;

	public CompensationDetailResponse toDetailResponse() {
		return CompensationDetailResponse.builder()
			.id(id)
			.amount(amount)
			.voc(voc.toResponse())
			.build();
	}

	public CompensationCreateResponse toCreateResponse() {
		return CompensationCreateResponse.builder()
			.id(id)
			.amount(amount)
			.vocId(voc.getId())
			.build();
	}

	public CompensationResponse toResponse() {
		return CompensationResponse.builder()
			.id(id)
			.amount(amount)
			.build();
	}

}
