package com.springboot.maybank.demo.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(value = Include.NON_NULL)
@Setter
@Getter
public class BaseDTO {

	private String result;

	private String message;

	private String name;

	private String email;

	private String phoneNo;

	private String city;

	private Long personId;

	private BigDecimal amount;

	private List<TransactionDTO> transactionList;

	private String thirdPartyUsers;
}
