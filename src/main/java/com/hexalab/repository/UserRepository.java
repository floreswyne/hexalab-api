package com.hexalab.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hexalab.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

	boolean existsByCpfCnpj(String cpfCnpj);

	Optional<UserEntity> findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.account.accountNumber = ?1 AND u.account.agency = ?2")
	Optional<UserEntity> findByAccountNumberAndAgency(String accountNumber, String agency);

}
