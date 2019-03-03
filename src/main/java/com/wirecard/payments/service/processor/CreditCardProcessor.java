package com.wirecard.payments.service.processor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.wirecard.payments.models.Card;
import com.wirecard.payments.models.Payment;
import com.wirecard.payments.models.PaymentStatus;
import com.wirecard.payments.models.PaymentType;
import com.wirecard.payments.repository.CardRepository;
import com.wirecard.payments.service.processor.erros.BrandsUnknownException;
import com.wirecard.payments.service.processor.erros.InvalidCardException;
import com.wirecard.payments.service.processor.erros.PaymentUnregisteredException;

import br.com.moip.creditcard.Brands;
import br.com.moip.validators.CreditCard;

@Service
public class CreditCardProcessor extends PaymentProcessor {
	
	@Autowired
	private CardRepository cardRepository;
	
	@Override
	public Pair<Boolean, Optional<String>> process(Payment payment) throws PaymentUnregisteredException {
		try {
			payment.setCard(validateCard(payment.getCard()));
			payment.setStatus(PaymentStatus.APPROVED);
			paymentRepository.save(payment);
			return Pair.of(true, Optional.of(PaymentStatus.APPROVED.getDescription()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new PaymentUnregisteredException();
		}
	}

	private Card validateCard(Card card) throws PaymentUnregisteredException {
		CreditCard creditCard = new CreditCard(card.getNumber());
		
		if(creditCard.getBrand().equals(Brands.UNKNOWN)) {
			throw new BrandsUnknownException();
		}
		if(!creditCard.isValid()) {
			throw new InvalidCardException();
		}
		Optional<Card> cardPresent = cardRepository.findFirstByNumber(card.getNumber());
		if(cardPresent.isPresent()) {
			return cardPresent.get();
		}
		return cardRepository.save(card);
	}

	@Override
    public PaymentType getType() {
        return PaymentType.CREDIT_CARD;
	}

	@Override
	public boolean getStatus() {
		return true;
	}

}
