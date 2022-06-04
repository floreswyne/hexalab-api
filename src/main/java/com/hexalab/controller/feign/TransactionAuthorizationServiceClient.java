package com.hexalab.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "transaction-authorization-service", url = "${services.transaction-authorization-service.url}")
public interface TransactionAuthorizationServiceClient {

	@PostMapping
	public ResponseEntity<Object> authorizeTransfer();

}
