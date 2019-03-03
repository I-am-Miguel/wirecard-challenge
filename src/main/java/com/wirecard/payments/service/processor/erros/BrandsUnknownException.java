package com.wirecard.payments.service.processor.erros;

public class BrandsUnknownException extends PaymentUnregisteredException {

	private static final long serialVersionUID = 1L;

	public BrandsUnknownException() {
		super("Unidentified card Brands");
	}
}
