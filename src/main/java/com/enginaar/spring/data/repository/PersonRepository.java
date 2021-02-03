package com.enginaar.spring.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.enginaar.spring.data.domain.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
	List<Person> findByName(String name);
	List<Person> findByLastName(String lastname);
	
	int countByName(String name);
}
