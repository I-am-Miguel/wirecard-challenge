package com.wirecard.payments.service.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class PaymentNotFoundException extends WireCardException {

	private static final long serialVersionUID = 1L;

	public PaymentNotFoundException() {
		super("Payment not Found!");
	}

	public PaymentNotFoundException(List<ObjectError> allErrors) {
		this();
		super.allErros = allErrors;
	}
}
