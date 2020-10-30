package com.wirecard.payments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.wirecard.payments.models.Buyer;
import com.wirecard.payments.models.Card;
import com.wirecard.payments.models.Client;
import com.wirecard.payments.models.Payment;
import com.wirecard.payments.models.PaymentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PaymentsApplicationTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void listClients() throws Exception {
        ResponseEntity<Client[]> response = testRestTemplate.getForEntity("/clients", Client[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @Test
    public void getClient() throws Exception {
        ResponseEntity<Client> response = testRestTemplate.getForEntity("/clients/1", Client.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @Test
    public void getClientWithException404() throws Exception {
        ResponseEntity<Client> response = testRestTemplate.getForEntity("/clients/0", Client.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getClientWithException400() throws Exception {
        ResponseEntity<Client> response = testRestTemplate.getForEntity("/clients/w", Client.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPaymentWithBoleto() throws Exception {
        Payment payment = createPayment(PaymentType.BOLETO);
        ResponseEntity<String> response = testRestTemplate.postForEntity("/payments", payment, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotEmpty();
    }


    @Test
    public void createPaymentWithCard() throws Exception {
        Payment payment = createPayment(PaymentType.CREDIT_CARD);
        ResponseEntity<String> response = testRestTemplate.postForEntity("/payments", payment, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void createPaymentWithCardException400() throws Exception {
        Payment payment = createPayment(PaymentType.CREDIT_CARD);
        payment.setAmount(BigDecimal.ZERO);
        ResponseEntity<String> response = testRestTemplate.postForEntity("/payments", payment, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void createPaymentWithBoletoException400() throws Exception {
        Payment payment = createPayment(PaymentType.BOLETO);
        payment.setAmount(BigDecimal.ZERO);
        ResponseEntity<String> response = testRestTemplate.postForEntity("/payments", payment, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotEmpty();
    }

    private Payment createPayment(PaymentType type) throws ParseException {
        if (type.equals(PaymentType.BOLETO))
            return Payment.builder()
                    .amount(BigDecimal.valueOf(100))
                    .buyer(getBuyer())
                    .client(new Client(1))
                    .type(type)
                    .build();

        return Payment.builder()
                .amount(BigDecimal.valueOf(100))
                .buyer(getBuyer())
                .client(new Client(1))
                .card(getCard())
                .type(type)
                .build();
    }

    private Buyer getBuyer() {
        return Buyer.builder()
                .cpf("51170898041")
                .email("email@email.com")
                .name("Name Buyer")
                .build();
    }

    private Card getCard() throws ParseException {
        return Card.builder()
                .cvv("012")
                .expirationDate(new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().plusDays(30).toString()))
                .holderName("Welligton Miguel")
                .number("4929340466625068")
                .build();
    }

}
