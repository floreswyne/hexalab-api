package com.hexalab.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexalab.entity.AccountEntity;
import com.hexalab.entity.TransferEntity;

public interface TransferRepository extends JpaRepository<TransferEntity, UUID> {

	List<TransferEntity> findBySender(AccountEntity sender);
	List<TransferEntity> findByReceiver(AccountEntity receiver);
	
}
