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

import com.hexalab.dto.input.TransferInputDTO;
import com.hexalab.dto.output.ExtractOutputDTO;
import com.hexalab.dto.output.TransferOutputDTO;
import com.hexalab.entity.TransferEntity;
import com.hexalab.enums.TransactionTypeEnum;
import com.hexalab.enums.TransferTypeEnum;
import com.hexalab.exceptions.AccountNotFoundException;
import com.hexalab.exceptions.SenderAccountBalanceInsufficientException;
import com.hexalab.exceptions.TransferNotFoundException;
import com.hexalab.service.TransferService;

@RestController
@RequestMapping(value = "/api/transfer")
public class TransferController {

	@Autowired
	private TransferService transferService;

	@GetMapping(value = "/{transferId}")
	public ResponseEntity<Object> findById(@PathVariable(value = "transferId") UUID transferId) {
		try {
			TransferOutputDTO transfer = transferService.findById(transferId).toDTO();
			return ResponseEntity.status(HttpStatus.FOUND).body(transfer);
		} catch (TransferNotFoundException transferNotFound) {
			return ResponseEntity.status(transferNotFound.getErrorBody().getStatus())
					.body(transferNotFound.getErrorBody());
		}
	}

	@GetMapping
	public ResponseEntity<Object> findAll() {
		try {
			List<TransferOutputDTO> transfers = transferService.findAll().stream().map(TransferEntity::toDTO).toList();
			return ResponseEntity.status(HttpStatus.FOUND).body(transfers);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error while search was performed!");
		}
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid TransferInputDTO dto) {
		try {
			TransferEntity newTransfer = dto.toEntity(TransferTypeEnum.TRANSFER);
			TransferOutputDTO createdTransfer = transferService.save(newTransfer).toDTO();
			return ResponseEntity.status(HttpStatus.CREATED).body(createdTransfer);
		} catch (AccountNotFoundException accountNotFound) {
			return ResponseEntity.status(accountNotFound.getErrorBody().getStatus())
					.body(accountNotFound.getErrorBody());
		} catch (SenderAccountBalanceInsufficientException balanceInsufficient) {
			return ResponseEntity.status(balanceInsufficient.getErrorBody().getStatus())
					.body(balanceInsufficient.getErrorBody());
		}
	}

	@PostMapping(value = "/transfers")
	public ResponseEntity<Object> saveAll(@RequestBody List<@Valid TransferInputDTO> dtos) {
		try {
			List<TransferEntity> newTransfers = dtos.stream().map(TransferInputDTO::toEntity).toList();
			List<TransferOutputDTO> createdTransfers = transferService.saveAll(newTransfers).stream()
					.map(TransferEntity::toDTO).toList();
			return ResponseEntity.status(HttpStatus.CREATED).body(createdTransfers);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while saving the transfers!");
		}
	}

	@PostMapping(value = "/deposit")
	public ResponseEntity<Object> depositAmountIntoAccount(@RequestBody @Valid TransferInputDTO dto) {
		try {
			TransferEntity newDeposit = dto.toEntity(TransferTypeEnum.DEPOSIT);
			TransferOutputDTO createdDeposit = transferService.save(newDeposit).toDTO();
			return ResponseEntity.status(HttpStatus.CREATED).body(createdDeposit);
		} catch (AccountNotFoundException accountNotFound) {
			return ResponseEntity.status(accountNotFound.getErrorBody().getStatus())
					.body(accountNotFound.getErrorBody());
		} catch (SenderAccountBalanceInsufficientException balanceInsufficient) {
			return ResponseEntity.status(balanceInsufficient.getErrorBody().getStatus())
					.body(balanceInsufficient.getErrorBody());
		}
	}

	@GetMapping(value = "/extract/{accountId}")
	public ResponseEntity<Object> getExtract(@PathVariable(value = "accountId") UUID accountId) {
		try {
			Map<TransactionTypeEnum, List<TransferEntity>> extractsLists = transferService
					.getExtractsListsByAccountId(accountId);
			List<ExtractOutputDTO> extract = transferService.convertExtractsListsToSortedList(extractsLists);
			return ResponseEntity.status(HttpStatus.FOUND).body(extract);
		} catch (AccountNotFoundException accountNotFound) {
			return ResponseEntity.status(accountNotFound.getErrorBody().getStatus())
					.body(accountNotFound.getErrorBody());
		}
	}

}
