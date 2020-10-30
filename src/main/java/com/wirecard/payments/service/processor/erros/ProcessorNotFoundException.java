package com.wirecard.payments.service.processor.erros;

import java.util.List;

import org.springframework.validation.ObjectError;

import com.wirecard.payments.service.exception.WireCardException;

public class ProcessorNotFoundException extends WireCardException {
    private static final long serialVersionUID = 1L;

    public ProcessorNotFoundException() {
        super("Processor not implemented!");
    }

    public ProcessorNotFoundException(List<ObjectError> allErrors) {
        this();
        super.allErros = allErrors;
    }
}
