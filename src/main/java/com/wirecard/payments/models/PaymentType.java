package com.wirecard.payments.models;

public enum PaymentType {

	BOLETO(1, "Boleto"), CREDIT_CARD(2, "Credit Card");

	private int code;
	private String description;

	PaymentType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}
}
