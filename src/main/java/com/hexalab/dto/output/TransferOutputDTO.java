package com.hexalab.dto.output;

import java.math.BigDecimal;
import java.util.UUID;

import com.hexalab.entity.TransferEntity;
import com.hexalab.enums.TransferTypeEnum;

public class TransferOutputDTO {

	private UUID id;

	private TransferTypeEnum type;

	private BigDecimal value;

	private AccountOutputDTO sender;

	private AccountOutputDTO receiver;
	
	public TransferOutputDTO (TransferEntity transfer) {
		convertEntityToDTO(transfer);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public TransferTypeEnum getType() {
		return type;
	}

	public void setType(TransferTypeEnum type) {
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
	
	private void convertEntityToDTO(TransferEntity transfer) {
		setId(transfer.getId());
		setType(transfer.getType());
		setValue(transfer.getValue());
		setSender(new AccountOutputDTO(transfer.getSender()));
		setReceiver(new AccountOutputDTO(transfer.getReceiver()));
	}

}
