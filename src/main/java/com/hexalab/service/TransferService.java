package com.hexalab.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexalab.dto.output.ExtractOutputDTO;
import com.hexalab.entity.AccountEntity;
import com.hexalab.entity.TransferEntity;
import com.hexalab.enums.TransactionTypeEnum;
import com.hexalab.enums.TransferTypeEnum;
import com.hexalab.exceptions.AccountNotFoundException;
import com.hexalab.exceptions.SenderAccountBalanceInsufficientException;
import com.hexalab.exceptions.TransferNotFoundException;
import com.hexalab.repository.AccountRepository;
import com.hexalab.repository.TransferRepository;

@Service
public class TransferService {

	@Autowired
	private TransferRepository transferRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Transactional
	public TransferEntity save(TransferEntity transfer) {
		this.prepareTransferToSave(transfer);
		return transferRepository.save(transfer);
	}

	@Transactional
	public List<TransferEntity> saveAll(List<TransferEntity> transfers) {
		transfers.forEach(this::prepareTransferToSave);
		return transferRepository.saveAll(transfers);
	}

	public TransferEntity findById(UUID id) {
		return transferRepository.findById(id).orElseThrow(() -> new TransferNotFoundException(id.toString()));
	}

	public List<TransferEntity> findAll() {
		return transferRepository.findAll();
	}

	public List<TransferEntity> findBySender(AccountEntity sender) {
		return transferRepository.findBySender(sender);
	}

	public List<TransferEntity> findByReceiver(AccountEntity receiver) {
		return transferRepository.findByReceiver(receiver);
	}

	public Map<TransactionTypeEnum, List<TransferEntity>> getExtractsListsByAccountId(UUID accountId) {
		AccountEntity account = accountRepository.findById(accountId)
				.orElseThrow(() -> new AccountNotFoundException(accountId.toString()));

		EnumMap<TransactionTypeEnum, List<TransferEntity>> extractsLists = new EnumMap<>(TransactionTypeEnum.class);

		List<TransferEntity> exits = findBySender(account).stream().map(e -> {
			if (e.getType().equals(TransferTypeEnum.DEPOSIT)) {
				return null;
			}
			return e;
		}).toList();

		extractsLists.put(TransactionTypeEnum.EXIT, exits);
		extractsLists.put(TransactionTypeEnum.ENTRY, findByReceiver(account));

		return extractsLists;
	}

	public List<ExtractOutputDTO> convertExtractsListsToSortedList(
			Map<TransactionTypeEnum, List<TransferEntity>> extractsLists) {
		List<ExtractOutputDTO> extract = new ArrayList<>();
		extract.addAll(extractsLists.get(TransactionTypeEnum.EXIT).stream()
				.map(e -> e.toExtractDTO(TransactionTypeEnum.EXIT)).toList());
		extract.addAll(extractsLists.get(TransactionTypeEnum.ENTRY).stream()
				.map(e -> e.toExtractDTO(TransactionTypeEnum.ENTRY)).toList());
		extract.sort((e1, e2) -> e1.getTransferMadeOn().compareTo(e2.getTransferMadeOn()));
		return extract;
	}
	
	private void prepareTransferToSave(TransferEntity transfer) {
		UUID senderId = transfer.getSender().getId();
		AccountEntity sender = accountRepository.findById(senderId)
				.orElseThrow(() -> new AccountNotFoundException(senderId.toString()));

		UUID receiverId = transfer.getReceiver().getId();
		AccountEntity receiver = accountRepository.findById(receiverId)
				.orElseThrow(() -> new AccountNotFoundException(receiverId.toString()));

		if (transfer.getType().equals(TransferTypeEnum.TRANSFER)) {
			BigDecimal differenceValueSender = sender.getBalance().subtract(transfer.getValue());

			if (differenceValueSender.signum() < 0) {
				throw new SenderAccountBalanceInsufficientException(
						"Sender account balance is insufficient for transfer!");
			}

			sender.setBalance(differenceValueSender);
			receiver.setBalance(receiver.getBalance().add(transfer.getValue()));
		} else if (transfer.getType().equals(TransferTypeEnum.DEPOSIT)) {
			receiver.setBalance(receiver.getBalance().add(transfer.getValue()));
		}

		transfer.setSender(sender);
		transfer.setReceiver(receiver);
	}

}
