package com.example.teamfreshsubject.compensation;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.teamfreshsubject.account.QAccount;
import com.example.teamfreshsubject.penalty.QPenalty;
import com.example.teamfreshsubject.voc.QVoc;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CompensationQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Transactional(readOnly = true)
	public List<Compensation> findAll() {
		QCompensation qCompensation = QCompensation.compensation;
		QAccount qAccount = QAccount.account;
		QVoc qVoc = QVoc.voc;
		QPenalty qPenalty = QPenalty.penalty;

		return jpaQueryFactory
			.select(qCompensation)
			.from(qCompensation)
			.leftJoin(qCompensation.voc, qVoc).fetchJoin()
			.leftJoin(qVoc.driver, qAccount).fetchJoin()
			.leftJoin(qVoc.manager, qAccount).fetchJoin()
			.leftJoin(qVoc.penalty, qPenalty).fetchJoin()
			.fetch();
	}
}
