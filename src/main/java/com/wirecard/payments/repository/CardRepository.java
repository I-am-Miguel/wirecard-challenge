package com.wirecard.payments.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wirecard.payments.models.Card;

public interface CardRepository extends JpaRepository<Card, Integer>{

	public Optional<Card> findFirstByNumber(String number);
}
