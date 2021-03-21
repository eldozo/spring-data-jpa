package com.enginaar.spring.data.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.enginaar.spring.data.domain.City;
import com.enginaar.spring.data.domain.Person;
import com.enginaar.spring.data.repository.CityRepository;
import com.enginaar.spring.data.repository.FlightRepository;
import com.enginaar.spring.data.repository.PersonRepository;
import com.github.javafaker.Faker;

@Component
public class ApplicationListener {

	@Autowired
	@Qualifier("personRepository")
	private PersonRepository persons;
	@Autowired
	private CityRepository cities;
	@Autowired
	private FlightRepository flights;
	Faker faker = new Faker();

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		initPeople();
		initCities();

	}
         
	private void initCities() {
            if(cities.count() > 50) return;
		for (int i = 0; i < 50; i++) {
			City a = new City();
			a.setName(faker.address().city());
			cities.save(a);
		}

	}

	private void initPeople() {
		if(persons.count() >= 1) return;
		for (int i = 0; i < 100; i++) {
			String name = faker.name().firstName();
			List<Person> prsList = persons.findByName(name);
			if(prsList.size() > 0)
			//if(persons.countByName(name) > 0)
				continue;
			Person p = new Person();
			p.setName(name);
			p.setLastName(faker.name().lastName());

			persons.save(p);
		}

	}

}


