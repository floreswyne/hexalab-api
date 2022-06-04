package com.hexalab.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "user-authentication-service", url = "${services.user-authentication-service.url}")
public interface UserAuthenticationServiceClient {

	@GetMapping
	public ResponseEntity<Object> authenticateUserEmail();

	@GetMapping
	public ResponseEntity<Object> authenticateUserPhone();

}
