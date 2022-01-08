package com.example.teamfreshsubject.account;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;

	public Account save(Account account) {
		return accountRepository.save(account);
	}
}
