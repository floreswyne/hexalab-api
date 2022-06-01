package com.hexalab.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexalab.entity.TransferEntity;

public interface TransferRepository extends JpaRepository<TransferEntity, UUID> {

}
