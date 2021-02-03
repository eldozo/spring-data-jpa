package com.enginaar.spring.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.enginaar.spring.data.domain.Flight;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long>{}
