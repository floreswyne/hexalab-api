package com.hexalab.controller;

import java.util.List;
import java.util.Map;
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

import com.hexalab.dto.input.TransactionInputDTO;
import com.hexalab.dto.output.ExtractOutputDTO;
import com.hexalab.dto.output.TransactionOutputDTO;
import com.hexalab.entity.TransactionEntity;
import com.hexalab.enums.ExtractTypeEnum;
import com.hexalab.enums.TransactionTypeEnum;
import com.hexalab.service.TransactionService;

@RestController
@RequestMapping(value = "/api/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping(value = "/{transactionId}")
	public ResponseEntity<Object> findById(@PathVariable(value = "transactionId") UUID transactionId) {
		boolean canShowBalance = false;
		TransactionOutputDTO transaction = transactionService.findById(transactionId).toOutputDTO(canShowBalance);
		return ResponseEntity.status(HttpStatus.FOUND).body(transaction);
	}

	@GetMapping
	public ResponseEntity<Object> findAll() {
		boolean canShowBalance = false;
		List<TransactionOutputDTO> transactions = transactionService.findAll().stream()
				.map(t -> t.toOutputDTO(canShowBalance)).toList();
		return ResponseEntity.status(HttpStatus.FOUND).body(transactions);
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid TransactionInputDTO dto) {
		boolean canShowBalance = false;
		TransactionEntity newTransaction = dto.toEntity(TransactionTypeEnum.TRANSFER);
		TransactionOutputDTO createdTransaction = transactionService.save(newTransaction).toOutputDTO(canShowBalance);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
	}

	@PostMapping(value = "/transactions")
	public ResponseEntity<Object> saveAll(@RequestBody List<@Valid TransactionInputDTO> dtos) {
		boolean canShowBalance = false;
		List<TransactionEntity> newTransactions = dtos.stream().map(TransactionInputDTO::toEntity).toList();
		List<TransactionOutputDTO> createdTransactions = transactionService.saveAll(newTransactions).stream()
				.map(t -> t.toOutputDTO(canShowBalance)).toList();
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTransactions);
	}

	@PostMapping(value = "/deposit")
	public ResponseEntity<Object> depositAmountIntoAccount(@RequestBody @Valid TransactionInputDTO dto) {
		boolean canShowBalance = false;
		TransactionEntity newDeposit = dto.toEntity(TransactionTypeEnum.DEPOSIT);
		TransactionOutputDTO createdDeposit = transactionService.save(newDeposit).toOutputDTO(canShowBalance);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdDeposit);
	}

	@GetMapping(value = "/extract/{accountId}")
	public ResponseEntity<Object> getExtract(@PathVariable(value = "accountId") UUID accountId) {
		Map<ExtractTypeEnum, List<TransactionEntity>> extractsLists = transactionService
				.getExtractsListsByAccountId(accountId);
		List<ExtractOutputDTO> extract = transactionService.convertExtractsListsToSortedList(extractsLists);
		return ResponseEntity.status(HttpStatus.FOUND).body(extract);
	}

}
