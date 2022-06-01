package com.hexalab.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexalab.entity.TransferEntity;
import com.hexalab.repository.TransferRepository;

@Service
public class TransferService {

	@Autowired
	private TransferRepository transferRepository;
	
	@Transactional
	public TransferEntity save(TransferEntity transfer) {
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

}
