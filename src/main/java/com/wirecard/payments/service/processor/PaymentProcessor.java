package com.wirecard.payments.service.processor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import com.wirecard.payments.models.Payment;
import com.wirecard.payments.models.PaymentType;
import com.wirecard.payments.repository.PaymentRepository;
import com.wirecard.payments.service.processor.erros.PaymentUnregisteredException;

@Component
public abstract class PaymentProcessor {

	@Autowired
	protected PaymentRepository paymentRepository;
	
    public abstract Pair<Boolean, Optional<String>> process(Payment payment) throws PaymentUnregisteredException;
    public abstract PaymentType getType();
    public abstract boolean getStatus();

}
