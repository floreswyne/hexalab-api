package com.hexalab.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexalab.dto.input.AccountInputDTO;
import com.hexalab.dto.output.AccountOutputDTO;
import com.hexalab.entity.AccountEntity;
import com.hexalab.service.AccountService;

@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@GetMapping(value = "/{accountId}")
	public ResponseEntity<Object> findById(@RequestBody UUID id) {
		Optional<AccountEntity> accountOptional = accountService.findById(id);
		
		if (!accountOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account cannot be found!");
		}
		
		AccountOutputDTO output = new AccountOutputDTO(accountOptional.get());
		
		return ResponseEntity.status(HttpStatus.OK).body(output);
	}
	
	@GetMapping
	public ResponseEntity<Object> findAll() {
		List<AccountOutputDTO> output = new ArrayList<>();
		
		accountService.findAll().forEach(a -> {
			output.add(new AccountOutputDTO(a));
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(output);
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid AccountInputDTO dto) {	
		AccountEntity account = new AccountEntity();
		BeanUtils.copyProperties(dto, account);
		
		AccountOutputDTO output = new AccountOutputDTO(accountService.save(account));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(output);
	}
	
	@PostMapping(value = "/accounts")
	public ResponseEntity<Object> saveAll(@RequestBody List<@Valid AccountInputDTO> dtos) {
		List<AccountEntity> accounts = new ArrayList<>();
		
		for (AccountInputDTO dto : dtos) {
			AccountEntity account = new AccountEntity();
			BeanUtils.copyProperties(dto, account);
			accounts.add(account);
		}
		
		List<AccountOutputDTO> output = new ArrayList<>();
		
		accountService.saveAll(accounts).forEach(a -> {
			output.add(new AccountOutputDTO(a));
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).body(output);
	}
	
}
