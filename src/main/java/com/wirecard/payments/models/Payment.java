package com.wirecard.payments.models;

import java.math.BigDecimal;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private Integer id;
    @NotNull
    @Min(value = 1)
    private BigDecimal amount;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Valid
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @LazyCollection(LazyCollectionOption.TRUE)
    private Client client;

    @Valid
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "buyer_id", nullable = false)
    @LazyCollection(LazyCollectionOption.TRUE)
    private Buyer buyer;

    @Valid
    @Nullable
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = true)
    @LazyCollection(LazyCollectionOption.TRUE)
    private Card card;

    @Valid
    @Nullable
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "boleto_id", nullable = true)
    @LazyCollection(LazyCollectionOption.TRUE)
    private Boleto boleto;
}
