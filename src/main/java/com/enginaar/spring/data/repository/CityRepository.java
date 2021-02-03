package com.enginaar.spring.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.enginaar.spring.data.domain.City;

@Repository
public interface CityRepository extends CrudRepository<City, Long>{}
