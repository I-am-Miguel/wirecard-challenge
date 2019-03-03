package com.wirecard.payments.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.gson.Gson;
import com.wirecard.payments.models.Payment;
import com.wirecard.payments.service.PaymentService;
import com.wirecard.payments.service.exception.BadRequestException;
import com.wirecard.payments.service.exception.WireCardException;

import io.swagger.annotations.Api;

@Api(value = "Controller Payments", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(path = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping
	public List<Payment> listAll() throws WireCardException{
		return paymentService.listAllPayments();
	}

	@PostMapping
	public ResponseEntity<String> paymentGenerate(@Valid @RequestBody Payment payment, BindingResult result) throws WireCardException {
		if (result.hasErrors()) {
			throw new BadRequestException(result.getAllErrors());
		}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(payment.getId()).toUri();
		Optional<String> response = paymentService.createPayment(payment);

		if(response.isPresent())
			return ResponseEntity.created(uri).body(new Gson().toJson(response.get()));
		return ResponseEntity.created(uri).build();
	}

	@GetMapping(path = "/{payment_id}")
	public Payment paymentStatus(@PathVariable("payment_id") String payment_id) throws WireCardException {
		return paymentService.findPaymentById(payment_id);
	}
}
