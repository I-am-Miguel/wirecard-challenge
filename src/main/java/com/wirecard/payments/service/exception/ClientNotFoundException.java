package com.wirecard.payments.service.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class ClientNotFoundException extends WireCardException {

	private static final long serialVersionUID = 1L;

	public ClientNotFoundException() {
		super("Client not Found!");
	}

	public ClientNotFoundException(List<ObjectError> allErrors) {
		this();
		super.allErros = allErrors;
	}
}
