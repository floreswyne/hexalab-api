package com.hexalab.dto.input;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.hexalab.entity.AccountEntity;
import com.hexalab.entity.TransactionEntity;
import com.hexalab.enums.TransactionTypeEnum;

public class TransactionInputDTO {
	
	private UUID id;

	@DecimalMin(value = "0.0", inclusive = true, message = "Value cannot be less than 0!")
	@Digits(integer = 6, fraction = 2, message = "Value cannot be blank!")
	@Min(value = 3, message = "Value need to be more than 3!")
	private BigDecimal value;

	@NotBlank(message = "Sender cannot be blank!")
	private String senderId;

	@NotBlank(message = "Receiver cannot be blank!")
	private String receiverId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	
	public TransactionEntity toEntity() {
		TransactionEntity transaction = new TransactionEntity();
		BeanUtils.copyProperties(this, transaction);
		
		AccountEntity sender = new AccountEntity();
		sender.setId(UUID.fromString(this.getSenderId()));
		transaction.setSender(sender);
		
		AccountEntity receiver = new AccountEntity();
		receiver.setId(UUID.fromString(this.getReceiverId()));
		transaction.setReceiver(receiver);
		
		return transaction;
	}
	
	public TransactionEntity toEntity(TransactionTypeEnum type) {
		TransactionEntity transaction = this.toEntity();

		transaction.setType(type);
		
		return transaction;
	}

}
