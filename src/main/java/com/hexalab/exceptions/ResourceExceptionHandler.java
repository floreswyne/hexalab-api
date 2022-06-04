package com.hexalab.exceptions;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<StandartError> senderAccountBalanceInsufficient(SenderAccountBalanceInsufficientException e,
			HttpServletRequest request) {
		StandartError error = new StandartError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.CONFLICT.value());
		error.setError("Insufficient balance for transfer!");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(error.getStatus()).body(error);
	}

	@ExceptionHandler
	public ResponseEntity<StandartError> accountNotFound(AccountNotFoundException e, HttpServletRequest request) {
		StandartError error = new StandartError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("Account not found!");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(error.getStatus()).body(error);
	}

	@ExceptionHandler
	public ResponseEntity<StandartError> transactionNotFound(TransactionNotFoundException e,
			HttpServletRequest request) {
		StandartError error = new StandartError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("Transaction not found!");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(error.getStatus()).body(error);
	}

	@ExceptionHandler
	public ResponseEntity<StandartError> userNotFound(UserNotFoundException e, HttpServletRequest request) {
		StandartError error = new StandartError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("User not found!");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(error.getStatus()).body(error);
	}

	@ExceptionHandler
	public ResponseEntity<StandartError> userAlreadyExists(UserAlreadyExistsException e, HttpServletRequest request) {
		StandartError error = new StandartError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.CONFLICT.value());
		error.setError("There is already a user in an unique field!");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(error.getStatus()).body(error);
	}

	@ExceptionHandler
	public ResponseEntity<StandartError> userLoginCredentialsInvalid(BadCredentialsException e,
			HttpServletRequest request) {
		StandartError error = new StandartError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		error.setError("User credentials are invalid!");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(error.getStatus()).body(error);
	}

	@ExceptionHandler
	public ResponseEntity<StandartError> userDataNotAuthenticated(UserDataNotAuthenticatedException e,
			HttpServletRequest request) {
		StandartError error = new StandartError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		error.setError("User data was not authenticated!");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(error.getStatus()).body(error);
	}

	@ExceptionHandler
	public ResponseEntity<StandartError> transferNotAuthorized(TransferNotAuthorizedException e,
			HttpServletRequest request) {
		StandartError error = new StandartError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		error.setError("Transfer not authorized!");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(error.getStatus()).body(error);
	}

}
