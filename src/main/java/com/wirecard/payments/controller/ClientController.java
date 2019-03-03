package com.wirecard.payments.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wirecard.payments.models.Client;
import com.wirecard.payments.repository.ClientRepository;
import com.wirecard.payments.service.exception.ClientNotFoundException;
import com.wirecard.payments.service.exception.WireCardException;

import io.swagger.annotations.Api;

@Api(value = "Controller Clients", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(path = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

	@Autowired
	private ClientRepository clientRepository;

	@GetMapping
	public List<Client> listAll() throws WireCardException{
		return clientRepository.findAll();
	}

	@GetMapping(path = "/{id}")
	public Client paymentStatus(@PathVariable("id") Integer id) throws WireCardException {
		return clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
	}
}
