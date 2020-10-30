package com.wirecard.payments.service.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class BadRequestException extends WireCardException {

    private static final long serialVersionUID = 1L;

    public BadRequestException() {
        super("Request With Invalid Syntax");
    }

    public BadRequestException(List<ObjectError> allErrors) {
        this();
        super.allErros = allErrors;
    }

}
