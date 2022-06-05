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

	private UUID transactionId;

	@DecimalMin(value = "0.0", inclusive = true, message = "Value cannot be less than 0!")
	@Digits(integer = 6, fraction = 2, message = "Value cannot be blank!")
	@Min(value = 3, message = "Value need to be more than 3!")
	private BigDecimal value;

	@NotBlank(message = "Sender cannot be blank!")
	private String senderAccountId;

	@NotBlank(message = "Receiver cannot be blank!")
	private String receiverAccountId;

	public UUID getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getSenderAccountId() {
		return senderAccountId;
	}

	public void setSenderAccountId(String senderAccountId) {
		this.senderAccountId = senderAccountId;
	}

	public String getReceiverAccountId() {
		return receiverAccountId;
	}

	public void setReceiverAccountId(String receiverAccountId) {
		this.receiverAccountId = receiverAccountId;
	}

	public TransactionEntity toEntity() {
		TransactionEntity transaction = new TransactionEntity();
		BeanUtils.copyProperties(this, transaction);

		AccountEntity sender = new AccountEntity();
		sender.setId(UUID.fromString(this.getSenderAccountId()));
		transaction.setSender(sender);

		AccountEntity receiver = new AccountEntity();
		receiver.setId(UUID.fromString(this.getReceiverAccountId()));
		transaction.setReceiver(receiver);

		return transaction;
	}

	public TransactionEntity toEntity(TransactionTypeEnum type) {
		TransactionEntity transaction = this.toEntity();

		transaction.setType(type);

		return transaction;
	}

}
