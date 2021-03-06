package com.hexalab.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexalab.controller.feign.TransactionAuthorizationServiceClient;
import com.hexalab.dto.output.ExtractOutputDTO;
import com.hexalab.entity.AccountEntity;
import com.hexalab.entity.TransactionEntity;
import com.hexalab.enums.ExtractTypeEnum;
import com.hexalab.enums.TransactionTypeEnum;
import com.hexalab.exceptions.AccountNotFoundException;
import com.hexalab.exceptions.SenderAccountBalanceInsufficientException;
import com.hexalab.exceptions.TransactionNotFoundException;
import com.hexalab.exceptions.TransactionPasswordIncorrect;
import com.hexalab.exceptions.TransferNotAuthorizedException;
import com.hexalab.repository.AccountRepository;
import com.hexalab.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionAuthorizationServiceClient transactionAuthorizationServiceClient;

	@Transactional
	public TransactionEntity save(TransactionEntity transaction) {
		this.prepareTransactionToSave(transaction);
		return transactionRepository.save(transaction);
	}

	@Transactional
	public List<TransactionEntity> saveAll(List<TransactionEntity> transactions) {
		transactions.forEach(this::prepareTransactionToSave);
		return transactionRepository.saveAll(transactions);
	}

	public TransactionEntity findById(UUID id) {
		return transactionRepository.findById(id).orElseThrow(
				() -> new TransactionNotFoundException("Transaction with ID: " + id.toString() + " cannot be found!"));
	}

	public List<TransactionEntity> findAll() {
		return transactionRepository.findAll();
	}

	public List<TransactionEntity> findBySender(AccountEntity sender) {
		return transactionRepository.findBySender(sender);
	}

	public List<TransactionEntity> findByReceiver(AccountEntity receiver) {
		return transactionRepository.findByReceiver(receiver);
	}

	public Map<ExtractTypeEnum, List<TransactionEntity>> getExtractsListsByAccountId(UUID accountId) {
		AccountEntity account = accountRepository.findById(accountId).orElseThrow(
				() -> new AccountNotFoundException("Account with ID: " + accountId.toString() + " cannot be found!"));

		EnumMap<ExtractTypeEnum, List<TransactionEntity>> extractsLists = new EnumMap<>(ExtractTypeEnum.class);

		List<TransactionEntity> exits = findBySender(account).stream().map(e -> {
			if (e.getType().equals(TransactionTypeEnum.DEPOSIT)) {
				return null;
			}
			return e;
		}).filter(Objects::nonNull).toList();

		extractsLists.put(ExtractTypeEnum.EXIT, exits);
		extractsLists.put(ExtractTypeEnum.ENTRY, findByReceiver(account));

		return extractsLists;
	}

	public List<ExtractOutputDTO> convertExtractsListsToSortedList(
			Map<ExtractTypeEnum, List<TransactionEntity>> extractsLists) {
		List<ExtractOutputDTO> extract = new ArrayList<>();

		extract.addAll(extractsLists.get(ExtractTypeEnum.EXIT).stream()
				.map(e -> e.toOutputExtractDTO(ExtractTypeEnum.EXIT)).toList());
		extract.addAll(extractsLists.get(ExtractTypeEnum.ENTRY).stream()
				.map(e -> e.toOutputExtractDTO(ExtractTypeEnum.ENTRY)).toList());
		extract.sort((e1, e2) -> e1.getTransactionMadeOn().compareTo(e2.getTransactionMadeOn()));
		return extract;
	}

	private void prepareTransactionToSave(TransactionEntity transaction) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		UUID senderId = transaction.getSender().getId();
		AccountEntity sender = accountRepository.findById(senderId).orElseThrow(
				() -> new AccountNotFoundException("Account with ID: " + senderId.toString() + " cannot be found!"));

		UUID receiverId = transaction.getReceiver().getId();
		AccountEntity receiver = accountRepository.findById(receiverId).orElseThrow(
				() -> new AccountNotFoundException("Account with ID: " + receiverId.toString() + " cannot be found!"));

		if (transaction.getType().equals(TransactionTypeEnum.TRANSFER)) {
			if (!bCryptPasswordEncoder.matches(transaction.getSender().getTransactionPassword(), sender.getTransactionPassword())) {
				throw new TransactionPasswordIncorrect("Transfer password is incorrect!");
			}
			
			authorizeTransfer();

			BigDecimal differenceValueSender = sender.getBalance().subtract(transaction.getValue());

			if (differenceValueSender.signum() < 0) {
				throw new SenderAccountBalanceInsufficientException(
						"Sender account balance is insufficient for transfer!");
			}

			sender.setBalance(differenceValueSender);
			receiver.setBalance(receiver.getBalance().add(transaction.getValue()));
		} else if (transaction.getType().equals(TransactionTypeEnum.DEPOSIT)) {
			receiver.setBalance(receiver.getBalance().add(transaction.getValue()));
		}

		transaction.setSender(sender);
		transaction.setReceiver(receiver);
	}

	private void authorizeTransfer() {
		ResponseEntity<Object> response = transactionAuthorizationServiceClient.authorizeTransfer();

		if (!response.getStatusCode().equals(HttpStatus.OK)) {
			throw new TransferNotAuthorizedException("Transfer was not authorized!");
		}
	}

}
