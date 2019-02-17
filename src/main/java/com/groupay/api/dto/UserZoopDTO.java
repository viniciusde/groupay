package com.groupay.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserZoopDTO {

	private String id;
	private String currentBalance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("current_balance")
	public String getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}

}
