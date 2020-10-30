package com.wirecard.payments.service.processor.erros;

import java.util.List;

import org.springframework.validation.ObjectError;

import com.wirecard.payments.service.exception.WireCardException;

public class PaymentUnregisteredException extends WireCardException {

    private static final long serialVersionUID = 1L;

    public PaymentUnregisteredException(String message) {
        super(message);
    }

    public PaymentUnregisteredException() {
        super("Unregistered Payment!");
    }

    public PaymentUnregisteredException(List<ObjectError> allErrors) {
        this();
        super.allErros = allErrors;
    }
}
