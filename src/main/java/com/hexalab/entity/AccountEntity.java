package com.hexalab.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.hexalab.dto.output.AccountOutputDTO;

@Entity(name = "Account")
@Table(name = "TB_ACCOUNT")
public class AccountEntity implements Serializable {

	private static final long serialVersionUID = -5183503657125607116L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_id")
	private UUID id;

	@Column(nullable = false)
	private String agency;

	@Column(nullable = false)
	private String accountNumber;

	@Column(nullable = false)
	private BigDecimal balance;

	@Column(nullable = false)
	private String transactionPassword;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updatedAt;

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

	public String getTransactionPassword() {
		return transactionPassword;
	}

	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
	protected void prePersist() {
		if (getCreatedAt() == null)
			setCreatedAt(LocalDateTime.now());
		if (getUpdatedAt() == null)
			setUpdatedAt(LocalDateTime.now());
	}

	@PreUpdate
	protected void preUpdate() {
		setUpdatedAt(LocalDateTime.now());
	}

	public AccountOutputDTO toOutputDTO() {
		return new AccountOutputDTO(this);
	}

}
