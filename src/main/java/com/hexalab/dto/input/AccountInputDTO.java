package com.hexalab.dto.input;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

public class AccountInputDTO {

	private UUID id;

	@NotBlank(message = "Agency cannot be blank!")
	private String agency;

	@NotBlank(message = "Account cannot be blank!")
	private String accountNumber;

	@NotBlank(message = "Balance cannot be blank!")
	private BigDecimal balance;
	
	@NotBlank(message = "Transaction password cannot be blank!")
	private String transctionPassword;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getTransctionPassword() {
		return transctionPassword;
	}

	public void setTransctionPassword(String transctionPassword) {
		this.transctionPassword = transctionPassword;
	}

}
