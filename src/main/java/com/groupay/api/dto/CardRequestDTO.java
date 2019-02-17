package com.groupay.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardRequestDTO {

	@JsonProperty("holder_name")
	private String holderName;
	
	@JsonProperty("expiration_month")
	private String expirationMonth;
	
	@JsonProperty("expiration_year")
	private String expirationYear;
	
	@JsonProperty("security_code")
	private String securityCode;
	
	@JsonProperty("card_number")
	private String cardNumber;
	
	@JsonProperty("token")
	private String token;
	
	@JsonProperty("customer")
	private String customer;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public String getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
}
