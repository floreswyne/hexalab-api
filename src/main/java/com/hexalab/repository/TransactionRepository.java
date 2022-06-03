package com.hexalab.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexalab.entity.AccountEntity;
import com.hexalab.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

	List<TransactionEntity> findBySender(AccountEntity sender);

	List<TransactionEntity> findByReceiver(AccountEntity receiver);

}
