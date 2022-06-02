package com.hexalab.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
import com.hexalab.entity.AccountEntity;
import com.hexalab.entity.TransferEntity;
import com.hexalab.enums.TransactionTypeEnum;
import com.hexalab.enums.TransferTypeEnum;
import com.hexalab.service.AccountService;
import com.hexalab.service.TransferService;

@RestController
@RequestMapping(value = "/api/transfer")
public class TransferController {

	@Autowired
	private TransferService transferService;

	@Autowired
	private AccountService accountService;

	@GetMapping(value = "/{transferId}")
	public ResponseEntity<Object> findById(@RequestBody UUID id) {
		Optional<TransferEntity> transferOptional = transferService.findById(id);

		if (!transferOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transfer cannot be found!");
		}

		TransferOutputDTO output = new TransferOutputDTO(transferOptional.get());

		return ResponseEntity.status(HttpStatus.OK).body(output);
	}

	@GetMapping
	public ResponseEntity<Object> findAll() {
		List<TransferOutputDTO> output = new ArrayList<>();

		transferService.findAll().forEach(t -> {
			output.add(new TransferOutputDTO(t));
		});

		return ResponseEntity.status(HttpStatus.OK).body(output);
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid TransferInputDTO dto) {
		Optional<AccountEntity> senderOptional = accountService.findById(UUID.fromString(dto.getSenderId()));

		if (!senderOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sender cannot be found!");
		}

		Optional<AccountEntity> receiverOptional = accountService.findById(UUID.fromString(dto.getReceiverId()));

		if (!receiverOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receiver cannot be found!");
		}

		TransferEntity transfer = new TransferEntity();
		BeanUtils.copyProperties(dto, transfer);
		transfer.setType(TransferTypeEnum.TRANSFER);
		transfer.setSender(senderOptional.get());
		transfer.setReceiver(receiverOptional.get());

		TransferOutputDTO output = new TransferOutputDTO(transferService.save(transfer));

		return ResponseEntity.status(HttpStatus.CREATED).body(output);
	}

	@PostMapping(value = "/transfers")
	public ResponseEntity<Object> saveAll(@RequestBody List<@Valid TransferInputDTO> dtos) {
		List<TransferEntity> transfers = new ArrayList<>();

		for (TransferInputDTO dto : dtos) {
			TransferEntity transfer = new TransferEntity();
			BeanUtils.copyProperties(dto, transfer);
			transfers.add(transfer);
		}

		List<TransferOutputDTO> output = new ArrayList<>();

		transferService.saveAll(transfers).forEach(t -> {
			output.add(new TransferOutputDTO(t));
		});

		return ResponseEntity.status(HttpStatus.CREATED).body(output);
	}

	@PostMapping(value = "/deposit")
	public ResponseEntity<Object> deposit(@RequestBody @Valid TransferInputDTO dto) {
		Optional<AccountEntity> senderOptional = accountService.findById(UUID.fromString(dto.getSenderId()));

		if (!senderOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sender cannot be found!");
		}

		Optional<AccountEntity> receiverOptional = accountService.findById(UUID.fromString(dto.getReceiverId()));

		if (!receiverOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receiver cannot be found!");
		}

		TransferEntity deposit = new TransferEntity();
		BeanUtils.copyProperties(dto, deposit);
		deposit.setType(TransferTypeEnum.DEPOSIT);
		deposit.setSender(senderOptional.get());
		deposit.setReceiver(receiverOptional.get());

		TransferOutputDTO output = new TransferOutputDTO(transferService.save(deposit));

		return ResponseEntity.status(HttpStatus.CREATED).body(output);
	}

	@GetMapping(value = "/extract/{accountId}")
	public ResponseEntity<Object> getExtract(@PathVariable(value = "accountId") UUID id) {
		Optional<AccountEntity> accountOptional = accountService.findById(id);

		if (!accountOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receiver cannot be found!");
		}

		Set<ExtractOutputDTO> extractTemp = new HashSet<>();

		extractTemp.addAll(transferService.findByReceiver(accountOptional.get()).stream()
				.map(t -> new ExtractOutputDTO(t, TransactionTypeEnum.ENTRY)).collect(Collectors.toList()));

		extractTemp.addAll(transferService.findBySender(accountOptional.get()).stream().map(t -> {
			if (t.getType().equals(TransferTypeEnum.DEPOSIT)) {
				return null;
			}
			return new ExtractOutputDTO(t, TransactionTypeEnum.EXIT);
		}).filter(t -> t != null).collect(Collectors.toList()));

		List<ExtractOutputDTO> output = new ArrayList<>(extractTemp);
		Collections.sort(output, (e1, e2) -> e1.getTransferMadeOn().compareTo(e2.getTransferMadeOn()));

		return ResponseEntity.status(HttpStatus.OK).body(output);
	}

}
