package com.wirecard.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wirecard.payments.models.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
