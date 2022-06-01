package com.hexalab.controller;

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

import com.hexalab.dto.input.UserDTO;
import com.hexalab.entity.AccountEntity;
import com.hexalab.entity.UserEntity;
import com.hexalab.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<Object> findById(@RequestBody UUID id) {
		Optional<UserEntity> userOptional = userService.findById(id);
		
		if (!userOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User cannot be found!");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
	}
	
	@GetMapping
	public ResponseEntity<Object> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid UserDTO dto) {
		if (userService.existsByEmail(dto.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail already registered!");
		}
		
		if (userService.existsByPhone(dto.getPhone())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phone already registered!");
		}
		
		if (userService.existsByCpfCnpj(dto.getCpfCnpj())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CPF/CNPJ already registered!");
		}
		
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(dto, user);
		user.setAccount(new AccountEntity());
		user.getAccount().setTransactionPassword(dto.getTransactionPassword());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
	}
	
}
