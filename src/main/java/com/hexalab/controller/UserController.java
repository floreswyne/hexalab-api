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

import com.hexalab.dto.input.UserInputDTO;
import com.hexalab.dto.output.UserOutputDTO;
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
		
		UserOutputDTO output = new UserOutputDTO(userOptional.get());
		
		return ResponseEntity.status(HttpStatus.OK).body(output);
	}
	
	@GetMapping
	public ResponseEntity<Object> findAll() {
		List<UserOutputDTO> output = new ArrayList<>();
		
		userService.findAll().forEach(u -> {
			output.add(new UserOutputDTO(u));
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(output);
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid UserInputDTO dto) {
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

		UserOutputDTO output = new UserOutputDTO(userService.save(user));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(output);
	}
	
	@PostMapping(value = "/users")
	public ResponseEntity<Object> saveAll(@RequestBody List<@Valid UserInputDTO> dtos) {
		List<UserEntity> users = new ArrayList<>();
		
		for (UserInputDTO dto : dtos) {
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
			users.add(user);
		}

		List<UserOutputDTO> output = new ArrayList<>();
		
		userService.saveAll(users).forEach(u -> {
			output.add(new UserOutputDTO(u));
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).body(output);
	}
	
}
