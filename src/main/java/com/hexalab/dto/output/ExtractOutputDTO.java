package com.hexalab.dto.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.hexalab.entity.TransactionEntity;
import com.hexalab.enums.ExtractTypeEnum;
import com.hexalab.enums.TransactionTypeEnum;

public class ExtractOutputDTO {

	private UUID extractId;

	private TransactionTypeEnum transactionType;

	private ExtractTypeEnum extractType;

	private BigDecimal value;

	private LocalDateTime transactionMadeOn;

	public ExtractOutputDTO(TransactionEntity extract, ExtractTypeEnum extractTypeEnum) {
		convertEntityToDTO(extract);
		this.setExtractType(extractTypeEnum);
	}

	public UUID getExtractId() {
		return extractId;
	}

	public void setExtractId(UUID extractId) {
		this.extractId = extractId;
	}

	public TransactionTypeEnum getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionTypeEnum transactionType) {
		this.transactionType = transactionType;
	}

	public ExtractTypeEnum getExtractType() {
		return extractType;
	}

	public void setExtractType(ExtractTypeEnum extractType) {
		this.extractType = extractType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public LocalDateTime getTransactionMadeOn() {
		return transactionMadeOn;
	}

	public void setTransactionMadeOn(LocalDateTime transactionMadeOn) {
		this.transactionMadeOn = transactionMadeOn;
	}

	private void convertEntityToDTO(TransactionEntity extract) {
		setExtractId(extract.getId());
		setTransactionType(extract.getType());
		setValue(extract.getValue());
		setTransactionMadeOn(extract.getCreatedAt());
	}

}
