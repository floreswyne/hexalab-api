package com.hexalab.dto.output;

import java.math.BigDecimal;
import java.util.UUID;

import com.hexalab.entity.AccountEntity;

public class AccountOutputDTO {

	private UUID accountId;

	private String agency;

	private String accountNumber;

	private BigDecimal balance;

	public AccountOutputDTO(AccountEntity account) {
		convertEntityToDTO(account);
	}

	public AccountOutputDTO(AccountEntity account, boolean canShowBalance) {
		convertEntityToDTO(account);
		if (!canShowBalance) {
			balance = null;
		}
	}

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

	private void convertEntityToDTO(AccountEntity account) {
		setAccountId(account.getId());
		setAgency(account.getAgency());
		setAccountNumber(account.getAccountNumber());
		setBalance(account.getBalance());
	}

}
