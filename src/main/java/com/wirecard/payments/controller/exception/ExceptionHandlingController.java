package com.wirecard.payments.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wirecard.payments.service.exception.BadRequestException;
import com.wirecard.payments.service.exception.ClientNotFoundException;
import com.wirecard.payments.service.exception.PaymentNotFoundException;
import com.wirecard.payments.service.exception.WireCardException;
import com.wirecard.payments.service.processor.erros.PaymentUnregisteredException;

@ControllerAdvice
public class ExceptionHandlingController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    private static ErrorDetails generateErrorDetails(HttpStatus status, WireCardException e) {
        return ErrorDetails.builder()
                .status(status.value())
                .title(status.name())
                .timestamp(System.currentTimeMillis())
                .message(e.getMessage())
                .cause(e.getAllErros())
                .build();
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlerPaymentNotFoundException(WireCardException e, HttpServletRequest request) {
        ErrorDetails error = generateErrorDetails(HttpStatus.NOT_FOUND, e);

        logger.error("non-existing payment requested");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlerClientNotFoundException(WireCardException e, HttpServletRequest request) {
        ErrorDetails error = generateErrorDetails(HttpStatus.NOT_FOUND, e);

        logger.error("non-existing client requested");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PaymentUnregisteredException.class)
    public ResponseEntity<ErrorDetails> handlerPaymentUnregisteredException(WireCardException e, HttpServletRequest request) {
        ErrorDetails error = generateErrorDetails(HttpStatus.BAD_REQUEST, e);

        logger.error("Unregistered Payment");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handlerBadRequestException(WireCardException e, HttpServletRequest request) {
        ErrorDetails error = generateErrorDetails(HttpStatus.BAD_REQUEST, e);

        logger.error("Request With Invalid Syntax!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
