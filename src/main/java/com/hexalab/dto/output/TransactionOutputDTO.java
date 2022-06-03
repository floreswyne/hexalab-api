package com.hexalab.dto.output;

import java.math.BigDecimal;
import java.util.UUID;

import com.hexalab.entity.TransactionEntity;
import com.hexalab.enums.TransactionTypeEnum;

public class TransactionOutputDTO {

	private UUID id;

	private TransactionTypeEnum type;

	private BigDecimal value;

	private AccountOutputDTO sender;

	private AccountOutputDTO receiver;
	
	public TransactionOutputDTO (TransactionEntity transaction) {
		convertEntityToDTO(transaction);
	}
	
	public TransactionOutputDTO (TransactionEntity transaction, boolean canShowBalance) {
		convertEntityToDTO(transaction, canShowBalance);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public TransactionTypeEnum getType() {
		return type;
	}

	public void setType(TransactionTypeEnum type) {
		this.type = type;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public AccountOutputDTO getSender() {
		return sender;
	}

	public void setSender(AccountOutputDTO sender) {
		this.sender = sender;
	}

	public AccountOutputDTO getReceiver() {
		return receiver;
	}

	public void setReceiver(AccountOutputDTO receiver) {
		this.receiver = receiver;
	}
	
	private void convertEntityToDTO(TransactionEntity transaction) {
		setId(transaction.getId());
		setType(transaction.getType());
		setValue(transaction.getValue());
		setSender(new AccountOutputDTO(transaction.getSender()));
		setReceiver(new AccountOutputDTO(transaction.getReceiver()));
	}
	
	private void convertEntityToDTO(TransactionEntity transaction, boolean canShowBalance) {
		setId(transaction.getId());
		setType(transaction.getType());
		setValue(transaction.getValue());
		setSender(new AccountOutputDTO(transaction.getSender(), canShowBalance));
		setReceiver(new AccountOutputDTO(transaction.getReceiver(), canShowBalance));
	}

}
