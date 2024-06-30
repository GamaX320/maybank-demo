package com.springboot.maybank.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Result {

	SUCCESS("Success", "Request successful."),
	FAILED("Failed", "Request failed."),
	DUPLICATE("Failed", "Request failed. Duplicate email found."),
	EXCEPTION("Failed", "Hit exception.");

	private final String code;
	private final String message;

}
