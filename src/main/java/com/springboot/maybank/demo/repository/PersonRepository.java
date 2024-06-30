package com.springboot.maybank.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.maybank.demo.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	List<Person> findByEmail(String email);

}
