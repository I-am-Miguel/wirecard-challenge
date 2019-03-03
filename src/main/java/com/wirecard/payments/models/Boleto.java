package com.wirecard.payments.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Boleto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "boleto_id")
	private Long id;
	@NotEmpty
	private String number;

}
