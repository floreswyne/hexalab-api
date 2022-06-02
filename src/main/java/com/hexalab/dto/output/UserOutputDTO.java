package com.hexalab.dto.output;

import java.util.UUID;

import com.hexalab.entity.UserEntity;

public class UserOutputDTO {

	private UUID id;

	private String name;
	
	private AccountOutputDTO account;
	
	public UserOutputDTO (UserEntity user) {
		convertEntityToDTO(user);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccountOutputDTO getAccount() {
		return account;
	}

	public void setAccount(AccountOutputDTO account) {
		this.account = account;
	}

	private void convertEntityToDTO(UserEntity user) {
		setId(user.getId());
		setName(user.getName());
		setAccount(new AccountOutputDTO(user.getAccount()));
	}
	
}
