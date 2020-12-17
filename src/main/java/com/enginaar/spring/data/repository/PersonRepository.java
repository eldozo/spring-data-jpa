package com.enginaar.spring.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.enginaar.spring.data.domain.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>{

}
