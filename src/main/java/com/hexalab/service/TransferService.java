package com.hexalab.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexalab.entity.AccountEntity;
import com.hexalab.entity.TransferEntity;
import com.hexalab.enums.TransferTypeEnum;
import com.hexalab.repository.TransferRepository;

@Service
public class TransferService {

	@Autowired
	private TransferRepository transferRepository;
	
	@Transactional
	public TransferEntity save(TransferEntity transfer) {
		if (transfer.getType().equals(TransferTypeEnum.TRANSFER)) {
			AccountEntity sender = transfer.getSender();
			BigDecimal differenceValueSender = sender.getBalance().subtract(transfer.getValue());
			
			if (differenceValueSender.signum() < 0) {
				return null;
			}
			
			sender.setBalance(differenceValueSender);
			
			AccountEntity receiver = transfer.getReceiver();
			
			receiver.setBalance(receiver.getBalance().add(transfer.getValue()));
			
			transfer.setSender(sender);
			transfer.setReceiver(receiver);
		} else if (transfer.getType().equals(TransferTypeEnum.DEPOSIT)) {
			AccountEntity receiver = transfer.getReceiver();
			
			receiver.setBalance(receiver.getBalance().add(transfer.getValue()));
			
			transfer.setReceiver(receiver);
		}
		
		return transferRepository.save(transfer);
	}
	
	@Transactional
	public List<TransferEntity> saveAll(List<TransferEntity> transfers) {
		return transferRepository.saveAll(transfers);
	}
	
	public Optional<TransferEntity> findById(UUID id) {
		return transferRepository.findById(id);
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

}
