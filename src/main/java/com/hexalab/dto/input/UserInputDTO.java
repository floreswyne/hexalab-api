package com.hexalab.dto.input;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.beans.BeanUtils;

import com.hexalab.entity.AccountEntity;
import com.hexalab.entity.UserEntity;

public class UserInputDTO {

	private UUID id;

	@NotBlank(message = "Name cannot be blank!")
	private String name;

	@Email(message = "E-mail need to be valid!", regexp = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+).(\\.[a-z]{2,3})$")
	@NotBlank(message = "E-mail cannot be blank!")
	private String email;

	@Pattern(message = "Phone need to be valid!", regexp = "^\\s*(\\d{2}|\\d{0})[-. ]?(\\d{5}|\\d{4})[-. ]?(\\d{4})[-. ]?\\s*$")
	@NotBlank(message = "Phone cannot be blank!")
	private String phone;

	@Pattern(message = "CPF/CNPJ need to be valid!", regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)|(^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$)/")
	@NotBlank(message = "CPF/CNPJ cannot be blank!")
	private String cpfCnpj;

	@NotBlank(message = "Password cannot be blank!")
	private String password;

	@NotBlank(message = "Account cannot be null!")
	private String transactionPassword;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTransactionPassword() {
		return transactionPassword;
	}

	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}

	public UserEntity toEntity() {
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(this, user, "password");

		user.setPassword(this.getPassword());
		user.setAccount(new AccountEntity());
		user.getAccount().setTransactionPassword(this.getTransactionPassword());

		return user;
	}

}
