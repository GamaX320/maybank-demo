package com.springboot.maybank.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.maybank.demo.dto.BaseDTO;
import com.springboot.maybank.demo.dto.Result;
import com.springboot.maybank.demo.service.PersonService;
import com.springboot.maybank.demo.util.JsonFactory;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/person")
public class PersonController {

	@Autowired
	PersonService personService;

	@PostMapping(value = "/createPerson")
	public @ResponseBody String createPerson(@RequestBody BaseDTO requestDTO) {
		BaseDTO responseDTO = new BaseDTO();
		try {
			ResponseEntity<BaseDTO> response = personService.createPerson(requestDTO);
			responseDTO = response.getBody();
		} catch (Exception e) {
			log.error("Unhandled exception ", e);
			responseDTO.setResult(Result.EXCEPTION.name());
			responseDTO.setMessage(Result.EXCEPTION.getMessage());
		}
		return JsonFactory.toJson(responseDTO);
	}

	@GetMapping("/getPerson/{personId}")
	public @ResponseBody String getPerson(@PathVariable(value = "personId") Long personId, HttpServletRequest request) {
		BaseDTO responseDTO = new BaseDTO();
		try {
			ResponseEntity<BaseDTO> response = personService.getPerson(personId, request);
			responseDTO = response.getBody();
		} catch (Exception e) {
			log.error("Unhandled exception ", e);
			responseDTO.setResult(Result.EXCEPTION.name());
			responseDTO.setMessage(Result.EXCEPTION.getMessage());
		}
		return JsonFactory.toJson(responseDTO);
	}

	@PutMapping("/updatePerson")
	public @ResponseBody String updatePerson(@RequestBody BaseDTO requestDTO) {
		BaseDTO responseDTO = new BaseDTO();
		try {
			ResponseEntity<BaseDTO> response = personService.updatePerson(requestDTO);
			responseDTO = response.getBody();
		} catch (Exception e) {
			log.error("Unhandled exception ", e);
			responseDTO.setResult(Result.EXCEPTION.name());
			responseDTO.setMessage(Result.EXCEPTION.getMessage());
		}
		return JsonFactory.toJson(responseDTO);
	}

	@PostMapping("/createTransaction")
	public @ResponseBody String createTransaction(@RequestBody BaseDTO requestDTO) {
		BaseDTO responseDTO = new BaseDTO();
		try {
			ResponseEntity<BaseDTO> response = personService.createTransaction(requestDTO);
			responseDTO = response.getBody();
		} catch (Exception e) {
			log.error("Unhandled exception ", e);
			responseDTO.setResult(Result.EXCEPTION.name());
			responseDTO.setMessage(Result.EXCEPTION.getMessage());
		}
		return JsonFactory.toJson(responseDTO);
	}

	@GetMapping("/getTransaction/{personId}/page/{page}")
	public @ResponseBody String getTransactionByPerson(@PathVariable(value = "personId") Long personId,
			@PathVariable(value = "page") int page, HttpServletRequest request) {

		BaseDTO responseDTO = new BaseDTO();
		try {
			ResponseEntity<BaseDTO> response = personService.getTransactionByPerson(personId, page, request);
			responseDTO = response.getBody();
		} catch (Exception e) {
			log.error("Unhandled exception ", e);
			responseDTO.setResult(Result.EXCEPTION.name());
			responseDTO.setMessage(Result.EXCEPTION.getMessage());
		}
		return JsonFactory.toJson(responseDTO);
	}

	@GetMapping("/retrieve3rdPartyUsers")
	public @ResponseBody String retrieve3rdPartyUsers(HttpServletRequest request) {
		BaseDTO responseDTO = new BaseDTO();
		try {
			ResponseEntity<BaseDTO> response = personService.retrieve3rdPartyUsers(request);
			responseDTO = response.getBody();
		} catch (Exception e) {
			log.error("Unhandled exception ", e);
			responseDTO.setResult(Result.EXCEPTION.name());
			responseDTO.setMessage(Result.EXCEPTION.getMessage());
		}
		return JsonFactory.toJson(responseDTO);
	}

}
