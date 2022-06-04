package com.hexalab.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexalab.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
	
	Optional<AccountEntity> findFirstByOrderByIdDesc();
	
}
