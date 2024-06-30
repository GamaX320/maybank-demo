package com.springboot.maybank.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(value = Include.NON_NULL)
@Setter
@Getter
@AllArgsConstructor
public class TransactionDTO {

	private Long transactionId;
	private Long amount;
	private String transactionDate;
}
