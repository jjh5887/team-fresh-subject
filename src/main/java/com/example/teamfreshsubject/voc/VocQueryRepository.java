package com.example.teamfreshsubject.voc;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VocQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Transactional(readOnly = true)
	public List<Voc> findAll() {
		QVoc qVoc = QVoc.voc;
		return jpaQueryFactory
			.select(qVoc)
			.from(qVoc)
			.leftJoin(qVoc.compensation).fetchJoin()
			.leftJoin(qVoc.penalty).fetchJoin()
			.leftJoin(qVoc.driver).fetchJoin()
			.leftJoin(qVoc.manager).fetchJoin()
			.fetch();
	}
}
