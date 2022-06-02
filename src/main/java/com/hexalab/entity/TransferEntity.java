package com.hexalab.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.hexalab.dto.output.ExtractOutputDTO;
import com.hexalab.dto.output.TransferOutputDTO;
import com.hexalab.enums.TransactionTypeEnum;
import com.hexalab.enums.TransferTypeEnum;

@Entity(name = "Transfer")
@Table(name = "TB_TRANSFER")
public class TransferEntity implements Serializable {

	private static final long serialVersionUID = 5048903213512791844L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transfer_id")
	private UUID id;
	
	@Column(nullable = false)
	private TransferTypeEnum type;
	
	@Column(nullable = false)
	private BigDecimal value;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private AccountEntity sender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private AccountEntity receiver;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdAt;

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

	public AccountEntity getSender() {
		return sender;
	}

	public void setSender(AccountEntity sender) {
		this.sender = sender;
	}

	public AccountEntity getReceiver() {
		return receiver;
	}

	public void setReceiver(AccountEntity receiver) {
		this.receiver = receiver;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@PrePersist
    protected void prePersist() {
        if (getCreatedAt() == null) setCreatedAt(LocalDateTime.now());
    }
	
	public TransferOutputDTO toOutputDTO() {
		return new TransferOutputDTO(this);
	}
	
	public ExtractOutputDTO toOutputExtractDTO(TransactionTypeEnum type) {
		return new ExtractOutputDTO(this, type);
	}

}
