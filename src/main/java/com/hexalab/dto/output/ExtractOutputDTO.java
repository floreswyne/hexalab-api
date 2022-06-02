package com.hexalab.dto.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.hexalab.entity.TransferEntity;
import com.hexalab.enums.TransactionTypeEnum;
import com.hexalab.enums.TransferTypeEnum;

public class ExtractOutputDTO {

	private UUID id;
	
	private TransferTypeEnum transferType;
	
	private TransactionTypeEnum transactionType;
	
	private BigDecimal value;
	
	private LocalDateTime transferMadeOn;
	
	public ExtractOutputDTO(TransferEntity extract, TransactionTypeEnum transactionType) {
		convertEntityToDTO(extract);
		this.setTransactionType(transactionType);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public TransferTypeEnum getTransferType() {
		return transferType;
	}

	public void setTransferType(TransferTypeEnum transferType) {
		this.transferType = transferType;
	}

	public TransactionTypeEnum getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionTypeEnum transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public LocalDateTime getTransferMadeOn() {
		return transferMadeOn;
	}

	public void setTransferMadeOn(LocalDateTime transferMadeOn) {
		this.transferMadeOn = transferMadeOn;
	}

	private void convertEntityToDTO(TransferEntity extract) {
		setId(extract.getId());
		setTransferType(extract.getType());
		setValue(extract.getValue());
		setTransferMadeOn(extract.getCreatedAt());
	}
	
}
