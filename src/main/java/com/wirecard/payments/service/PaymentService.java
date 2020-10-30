package com.wirecard.payments.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import com.google.common.collect.Lists;
import com.wirecard.payments.models.Payment;
import com.wirecard.payments.repository.PaymentRepository;
import com.wirecard.payments.service.exception.BadRequestException;
import com.wirecard.payments.service.exception.PaymentNotFoundException;
import com.wirecard.payments.service.exception.WireCardException;
import com.wirecard.payments.service.processor.erros.PaymentUnregisteredException;
import com.wirecard.payments.service.processor.erros.ProcessorNotFoundException;

@Service
public class PaymentService {

    private ProcessorSerice processorSerice;
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ProcessorSerice processorSerice) {
        this.processorSerice = processorSerice;
        this.paymentRepository = paymentRepository;
    }

    public Optional<String> createPayment(Payment payment) throws ProcessorNotFoundException, PaymentUnregisteredException {
        Pair<Boolean, Optional<String>> statusProcessing = processorSerice
                .activePaymentProcessor(payment.getType())
                .process(payment);
        if (statusProcessing.getFirst()) {
            return statusProcessing.getSecond();
        }
        throw new PaymentUnregisteredException();
    }

    public Payment findPaymentById(String payment_id) throws WireCardException {
        boolean isDigit = payment_id.chars().allMatch(Character::isDigit);
        if (!isDigit)
            throw new BadRequestException(
                    Lists.newArrayList(new ObjectError(payment_id, "Field must be numeric")));

        return paymentRepository.findById(Integer.valueOf(payment_id))
                .orElseThrow(PaymentNotFoundException::new);
    }

    public List<Payment> listAllPayments() throws PaymentNotFoundException {
        List<Payment> payments = paymentRepository.findAll();
        if (payments.isEmpty())
            throw new PaymentNotFoundException();
        return payments;
    }

}
