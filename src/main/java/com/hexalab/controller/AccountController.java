package com.hexalab.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexalab.dto.input.AccountInputDTO;
import com.hexalab.dto.output.AccountOutputDTO;
import com.hexalab.entity.AccountEntity;
import com.hexalab.exceptions.AccountNotFoundException;
import com.hexalab.service.AccountService;

@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping(value = "/{accountId}")
	public ResponseEntity<Object> findById(@PathVariable(value = "accountId") UUID accountId) {
		try {
			AccountOutputDTO account = accountService.findById(accountId).toDTO();
			return ResponseEntity.status(HttpStatus.FOUND).body(account);
		} catch (AccountNotFoundException accountNotFound) {
			return ResponseEntity.status(accountNotFound.getErrorBody().getStatus())
					.body(accountNotFound.getErrorBody());
		}
	}

	@GetMapping
	public ResponseEntity<Object> findAll() {
		try {
			List<AccountOutputDTO> accounts = accountService.findAll().stream().map(AccountEntity::toDTO).toList();
			return ResponseEntity.status(HttpStatus.FOUND).body(accounts);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error while search was performed!");
		}
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid AccountInputDTO dto) {
		try {
			AccountEntity newAccount = dto.toEntity();
			AccountOutputDTO createdAccount = accountService.save(newAccount).toDTO();
			return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while saving the account!");
		}
	}

	@PostMapping(value = "/accounts")
	public ResponseEntity<Object> saveAll(@RequestBody List<@Valid AccountInputDTO> dtos) {
		try {
			List<AccountEntity> newAccounts = dtos.stream().map(AccountInputDTO::toEntity).toList();
			List<AccountOutputDTO> createdAccounts = accountService.saveAll(newAccounts).stream().map(AccountEntity::toDTO)
					.toList();
			return ResponseEntity.status(HttpStatus.CREATED).body(createdAccounts);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while saving the accounts!");
		}
	}

}
