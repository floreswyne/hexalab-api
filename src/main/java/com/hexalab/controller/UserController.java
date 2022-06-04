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

import com.hexalab.dto.input.UserInputDTO;
import com.hexalab.dto.output.UserOutputDTO;
import com.hexalab.entity.UserEntity;
import com.hexalab.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/{agency}/{accountNumber}")
	public ResponseEntity<Object> findByAccountNumberAndAgency(@PathVariable(value = "agency") String agency,
			@PathVariable(value = "accountNumber") String accountNumber) {
		boolean canShowBalance = false;
		UserOutputDTO user = userService.findByAccountNumberAndAgency(accountNumber, agency)
				.toOutputDTO(canShowBalance);
		return ResponseEntity.status(HttpStatus.FOUND).body(user);
	}

	@GetMapping(value = "/{userId}")
	public ResponseEntity<Object> findById(@PathVariable(value = "userId") UUID userId) {
		UserOutputDTO user = userService.findById(userId).toOutputDTO();
		return ResponseEntity.status(HttpStatus.FOUND).body(user);
	}

	@GetMapping
	public ResponseEntity<Object> findAll() {
		List<UserOutputDTO> users = userService.findAll().stream().map(UserEntity::toOutputDTO).toList();
		return ResponseEntity.status(HttpStatus.FOUND).body(users);
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid UserInputDTO dto) {
		UserEntity newUser = dto.toEntity();
		UserOutputDTO createdUser = userService.save(newUser).toOutputDTO();
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PostMapping(value = "/users")
	public ResponseEntity<Object> saveAll(@RequestBody List<@Valid UserInputDTO> dtos) {
		List<UserEntity> newUsers = dtos.stream().map(UserInputDTO::toEntity).toList();
		List<UserOutputDTO> createdUsers = userService.saveAll(newUsers).stream().map(UserEntity::toOutputDTO).toList();
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
	}

}
