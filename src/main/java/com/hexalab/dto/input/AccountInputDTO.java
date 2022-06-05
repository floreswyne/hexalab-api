package com.hexalab.dto.input;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.hexalab.entity.AccountEntity;

public class AccountInputDTO {

	private UUID accountId;

	@NotBlank(message = "Agency cannot be blank!")
	private String agency;

	@NotBlank(message = "Account cannot be blank!")
	private String accountNumber;

	@NotBlank(message = "Balance cannot be blank!")
	private BigDecimal balance;

	@NotBlank(message = "Transaction password cannot be blank!")
	private String transactionPassword;

	public UUID getAccountId() {
		return accountId;
	}

	public void setAccountId(UUID accountId) {
		this.accountId = accountId;
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

	public String getTransactionPassword() {
		return transactionPassword;
	}

	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}

	public AccountEntity toEntity() {
		AccountEntity account = new AccountEntity();
		BeanUtils.copyProperties(this, account);
		return account;
	}

}
