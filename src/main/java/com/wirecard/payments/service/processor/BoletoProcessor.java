package com.wirecard.payments.service.processor;

import java.util.Optional;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import com.google.common.collect.Lists;
import com.wirecard.payments.models.Boleto;
import com.wirecard.payments.models.Payment;
import com.wirecard.payments.models.PaymentStatus;
import com.wirecard.payments.models.PaymentType;
import com.wirecard.payments.service.processor.erros.PaymentUnregisteredException;

@Service
public class BoletoProcessor extends PaymentProcessor {

    @Override
    public Pair<Boolean, Optional<String>> process(Payment payment) throws PaymentUnregisteredException {
        payment.setBoleto(Boleto.builder()
                .number("34191.09065 20674.712938 83456.900009 6 69240000029800")
                .build());
        try {
            payment.setStatus(PaymentStatus.APPROVED);
            paymentRepository.save(payment);
            return Pair.of(true, Optional.of(payment.getBoleto().getNumber()));
        } catch (Exception e) {
            throw new PaymentUnregisteredException(
                    Lists.newArrayList(new ObjectError("BOLETO REGISTER", e.getMessage())));
        }
    }

    @Override
    public PaymentType getType() {
        return PaymentType.BOLETO;
    }

    @Override
    public boolean getStatus() {
        return true;
    }
}
