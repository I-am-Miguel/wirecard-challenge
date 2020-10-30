package com.wirecard.payments.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wirecard.payments.models.PaymentType;
import com.wirecard.payments.service.processor.PaymentProcessor;
import com.wirecard.payments.service.processor.erros.ProcessorNotFoundException;

@Service
public class ProcessorSerice {

    private Map<PaymentType, List<PaymentProcessor>> processors;

    @Autowired
    private ProcessorSerice(List<PaymentProcessor> allProcessors) {
        this.processors = allProcessors.stream()
                .collect(Collectors.groupingBy(PaymentProcessor::getType));
    }

    public PaymentProcessor activePaymentProcessor(PaymentType type) throws ProcessorNotFoundException {
        return processors.get(type).stream()
                .filter(p -> p.getStatus()).findAny()
                .orElseThrow(() -> new ProcessorNotFoundException());
    }
}
