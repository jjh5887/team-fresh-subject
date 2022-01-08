package com.example.teamfreshsubject.penalty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.example.teamfreshsubject.penalty.response.PenaltyResponse;
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
public class Penalty {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, length = 200)
	private String description;

	@Column(nullable = false)
	private Boolean checked;

	@Column(nullable = false)
	private Boolean objection;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "voc_id")
	private Voc voc;

	public PenaltyResponse toResponse() {
		return PenaltyResponse.builder()
			.id(id)
			.description(description)
			.checked(checked)
			.objection(objection)
			.build();
	}

}
