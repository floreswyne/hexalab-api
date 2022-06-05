package com.hexalab.dto.output;

import java.util.UUID;

import com.hexalab.entity.UserEntity;

public class UserOutputDTO {

	private UUID userId;

	private String name;

	private AccountOutputDTO account;

	public UserOutputDTO(UserEntity user) {
		convertEntityToDTO(user);
	}

	public UserOutputDTO(UserEntity user, boolean canShowBalance) {
		convertEntityToDTO(user, canShowBalance);
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
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
		setUserId(user.getId());
		setName(user.getName());
		setAccount(new AccountOutputDTO(user.getAccount()));
	}

	private void convertEntityToDTO(UserEntity user, boolean canShowBalance) {
		setUserId(user.getId());
		setName(user.getName());
		setAccount(new AccountOutputDTO(user.getAccount(), canShowBalance));
	}

}
