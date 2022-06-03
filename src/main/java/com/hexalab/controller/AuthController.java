package com.hexalab.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexalab.dto.input.AuthInputDTO;
import com.hexalab.dto.output.AuthOutputDTO;
import com.hexalab.entity.CustomUserEntity;
import com.hexalab.entity.UserEntity;
import com.hexalab.util.JwtTokenUtil;

@RestController
@RequestMapping(path = "/api/login")
public class AuthController {

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@PostMapping
	public ResponseEntity<Object> login(@RequestBody @Valid AuthInputDTO dto) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
		
		CustomUserEntity customUser = (CustomUserEntity) authentication.getPrincipal();
		UserEntity user = customUser.getUser();
		String accessToken = jwtTokenUtil.generateAccessToken(user);
		AuthOutputDTO auth = new AuthOutputDTO(user.getEmail(), accessToken);

		return ResponseEntity.status(HttpStatus.OK).body(auth);
	}

}
