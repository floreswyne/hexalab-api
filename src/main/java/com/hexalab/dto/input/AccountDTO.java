package com.hexalab.dto.input;

import javax.validation.constraints.NotBlank;

public class AccountDTO {

	@NotBlank(message = "Transaction password cannot be blank!")
	private String transctionPassword;

	public String getTransctionPassword() {
		return transctionPassword;
	}

	public void setTransctionPassword(String transctionPassword) {
		this.transctionPassword = transctionPassword;
	}

}
