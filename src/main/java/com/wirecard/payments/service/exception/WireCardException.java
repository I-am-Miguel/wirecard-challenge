package com.wirecard.payments.service.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class WireCardException extends Exception {

    private static final long serialVersionUID = 1L;
    protected List<ObjectError> allErros;

    public WireCardException(String message) {
        super(message);
    }

    public List<ObjectError> getAllErros() {
        return allErros;
    }
}
