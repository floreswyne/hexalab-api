package com.hexalab.dto.input;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class TransferInputDTO {
	
	private UUID id;

	@DecimalMin(value = "0.0", inclusive = true, message = "Value cannot be less than 0!")
	@Digits(integer = 6, fraction = 2, message = "Value cannot be blank!")
	@Min(value = 3)
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

}
