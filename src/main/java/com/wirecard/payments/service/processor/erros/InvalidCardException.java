package com.wirecard.payments.service.processor.erros;

public class InvalidCardException extends PaymentUnregisteredException {

    private static final long serialVersionUID = 1L;


    public InvalidCardException() {
        super("Invalid Card");
    }
}
