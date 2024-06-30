package com.springboot.maybank.demo.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.springboot.maybank.demo.dto.BaseDTO;
import com.springboot.maybank.demo.dto.Result;
import com.springboot.maybank.demo.dto.TransactionDTO;
import com.springboot.maybank.demo.entity.Person;
import com.springboot.maybank.demo.entity.Transaction;
import com.springboot.maybank.demo.repository.PersonRepository;
import com.springboot.maybank.demo.repository.TransactionRepository;
import com.springboot.maybank.demo.util.JsonFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	private ModelMapper modelMapper;

	public static int PAGE_SIZE = 10;

	@Value("${third.party.userdata.url}")
	private String thirdPartyUserdataUrl;

	@Transactional
	public ResponseEntity<BaseDTO> createPerson(BaseDTO dto) throws Exception {

		long start = System.currentTimeMillis();
		log.info("---------- [/api/v1/person/createPerson] ----------");
		log.info("Request:\n{}", JsonFactory.toJson(dto));

		List<Person> personT = personRepository.findByEmail(dto.getEmail());
		if (personT.isEmpty()) {
			// no exist
			Person person = new Person();
			person = modelMapper.map(dto, Person.class);
			personRepository.save(person);

			dto.setPersonId(person.getId());
			dto.setResult(Result.SUCCESS.getCode());
			dto.setMessage(Result.SUCCESS.getMessage());
		} else {
			// existed
			dto = new BaseDTO();
			dto.setResult(Result.DUPLICATE.getCode());
			dto.setMessage(Result.DUPLICATE.getMessage());
			log.error("Person duplicated email found");
		}

		log.info("Response:\n{}", JsonFactory.toJson(dto));
		log.info("---------- [END] tooks {} ms ----------", ((System.currentTimeMillis() - start)));
		return ResponseEntity.ok().body(dto);
	}

	@Transactional
	public ResponseEntity<BaseDTO> getPerson(Long personId, HttpServletRequest request) throws Exception {

		long start = System.currentTimeMillis();
		log.info("---------- [/api/v1/person/getPerson] ----------");
		log.info("Request:\n{}", request.getRequestURI());

		BaseDTO dto = new BaseDTO();
		Optional<Person> personT = personRepository.findById(personId);
		if (personT.isPresent()) {
			dto = modelMapper.map(personT.get(), BaseDTO.class);

			dto.setResult(Result.SUCCESS.getCode());
			dto.setMessage(Result.SUCCESS.getMessage());
		} else {
			// result not found
			dto = new BaseDTO();
			dto.setResult(Result.FAILED.getCode());
			dto.setMessage(Result.FAILED.getMessage());
			log.error("Person id {} not found", personId);
		}

		log.info("Response:\n{}", JsonFactory.toJson(dto));
		log.info("---------- [END] tooks {} ms ----------", ((System.currentTimeMillis() - start)));
		return ResponseEntity.ok().body(dto);
	}

	@Transactional
	public ResponseEntity<BaseDTO> updatePerson(BaseDTO dto) throws Exception {

		long start = System.currentTimeMillis();
		log.info("---------- [/api/v1/person/updatePerson] ----------");
		log.info("Request:\n{}", JsonFactory.toJson(dto));

		Optional<Person> personT = personRepository.findById(dto.getPersonId());
		if (personT.isPresent()) {
			Person person = personT.get();

			if (StringUtils.hasText(dto.getName()))
				person.setName(dto.getName());
			if (StringUtils.hasText(dto.getEmail()))
				person.setEmail(dto.getEmail());
			if (StringUtils.hasText(dto.getPhoneNo()))
				person.setPhoneNo(dto.getPhoneNo());
			if (StringUtils.hasText(dto.getCity()))
				person.setCity(dto.getCity());

			personRepository.save(person);
			dto.setResult(Result.SUCCESS.getCode());
			dto.setMessage(Result.SUCCESS.getMessage());
		} else {
			// result not found
			dto = new BaseDTO();
			dto.setResult(Result.FAILED.getCode());
			dto.setMessage(Result.FAILED.getMessage());
			log.error("Person id {} not found", dto.getPersonId());
		}

		log.info("Response:\n{}", JsonFactory.toJson(dto));
		log.info("---------- [END] tooks {} ms ----------", ((System.currentTimeMillis() - start)));
		return ResponseEntity.ok().body(dto);
	}

	@Transactional
	public ResponseEntity<BaseDTO> createTransaction(BaseDTO dto) throws Exception {

		long start = System.currentTimeMillis();
		log.info("---------- [/api/v1/person/createTransaction] ----------");
		log.info("Request:\n{}", JsonFactory.toJson(dto));

		Optional<Person> personT = personRepository.findById(dto.getPersonId());
		if (personT.isPresent()) {
			Person person = personT.get();
			Transaction transaction = new Transaction();
			mapDTOtoTransaction(dto, person, transaction);
			transactionRepository.save(transaction);
			dto.setResult(Result.SUCCESS.getCode());
			dto.setMessage(Result.SUCCESS.getMessage());
		} else {
			// result not found
			dto = new BaseDTO();
			dto.setResult(Result.FAILED.getCode());
			dto.setMessage(Result.FAILED.getMessage());
			log.error("Person id {} not found", dto.getPersonId());
		}

		log.info("Response:\n{}", JsonFactory.toJson(dto));
		log.info("---------- [END] tooks {} ms ----------", ((System.currentTimeMillis() - start)));
		return ResponseEntity.ok().body(dto);
	}

	@Transactional
	public ResponseEntity<BaseDTO> getTransactionByPerson(Long personId, int page, HttpServletRequest request) throws Exception {

		long start = System.currentTimeMillis();
		log.info("---------- [/api/v1/person/getTransactionByPerson] ----------");
		log.info("Request:\n{}", request.getRequestURI());

		BaseDTO dto = new BaseDTO();
		Optional<Person> personT = personRepository.findById(personId);
		if (personT.isPresent()) {
			Pageable pageable = PageRequest.of(page, PAGE_SIZE);
			List<Transaction> transactionList = transactionRepository.findByPerson(personT.get(), pageable);
			List<TransactionDTO> transactionDTOList = transactionList.stream()
					.map(obj -> new TransactionDTO(obj.getId(), obj.getAmount(), parseDateTime(obj.getTransactionDate())))
					.collect(Collectors.toList());
			dto.setTransactionList(transactionDTOList);
			dto.setResult(Result.SUCCESS.getCode());
			dto.setMessage(Result.SUCCESS.getMessage());
		} else {
			// result not found
			dto = new BaseDTO();
			dto.setResult(Result.FAILED.getCode());
			dto.setMessage(Result.FAILED.getMessage());
			log.info("Person id {} not found", personId);
		}

		log.info("Response:\n{}", JsonFactory.toJson(dto));
		log.info("---------- [END] tooks {} ms ----------", ((System.currentTimeMillis() - start)));
		return ResponseEntity.ok().body(dto);
	}

	public ResponseEntity<BaseDTO> retrieve3rdPartyUsers(HttpServletRequest request) throws Exception {

		long start = System.currentTimeMillis();
		log.info("---------- [/api/v1/person/retrieve3rdPartyUsers] ----------");
		log.info("Request:\n{}", request.getRequestURI());

		BaseDTO dto = new BaseDTO();

		RestTemplate rs = new RestTemplate();
		ResponseEntity<String> response = rs.exchange(thirdPartyUserdataUrl, HttpMethod.GET, null, String.class);
		String responseStr = response.getBody();

		if (StringUtils.hasText(responseStr)) {
			dto.setThirdPartyUsers(responseStr);
			dto.setResult(Result.SUCCESS.getCode());
			dto.setMessage(Result.SUCCESS.getMessage());
		} else {
			// result not found
			dto = new BaseDTO();
			dto.setResult(Result.FAILED.getCode());
			dto.setMessage(Result.FAILED.getMessage());
			log.info("Third Party return empty result");
		}

		log.info("Response:\n{}", JsonFactory.toJson(dto));
		log.info("---------- [END] tooks {} ms ----------", ((System.currentTimeMillis() - start)));
		return ResponseEntity.ok().body(dto);
	}

	private void mapDTOtoTransaction(BaseDTO dto, Person person, Transaction transaction) {
		transaction.setTransactionId(UUID.randomUUID().toString());
		transaction.setTransactionDate(new Timestamp(System.currentTimeMillis()));
		transaction.setAmount(dto.getAmount());
		transaction.setPerson(person);
	}

	private String parseDateTime(Timestamp t) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		Date date = new Date(t.getTime());
		return sdf.format(date);
	}
}
