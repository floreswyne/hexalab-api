package com.hexalab.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexalab.entity.AccountEntity;
import com.hexalab.exceptions.AccountNotFoundException;
import com.hexalab.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Transactional
	public AccountEntity save(AccountEntity account) {
		return accountRepository.save(account);
	}

	@Transactional
	public List<AccountEntity> saveAll(List<AccountEntity> accounts) {
		return accountRepository.saveAll(accounts);
	}

	public AccountEntity findById(UUID id) {
		return accountRepository.findById(id).orElseThrow(
				() -> new AccountNotFoundException("Account with ID: " + id.toString() + " cannot be found!"));
	}

	public List<AccountEntity> findAll() {
		return accountRepository.findAll();
	}

}
