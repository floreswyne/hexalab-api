package com.hexalab.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexalab.dto.output.AccountOutputDTO;
import com.hexalab.entity.AccountEntity;
import com.hexalab.service.AccountService;

@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping(value = "/{accountId}")
	public ResponseEntity<Object> findById(@PathVariable(value = "accountId") UUID accountId) {
		AccountOutputDTO account = accountService.findById(accountId).toOutputDTO();
		return ResponseEntity.status(HttpStatus.FOUND).body(account);
	}

	@GetMapping
	public ResponseEntity<Object> findAll() {
		List<AccountOutputDTO> accounts = accountService.findAll().stream().map(AccountEntity::toOutputDTO).toList();
		return ResponseEntity.status(HttpStatus.FOUND).body(accounts);
	}

}
