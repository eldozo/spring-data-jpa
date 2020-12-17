package com.enginaar.spring.data.listener;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.enginaar.spring.data.domain.City;
import com.enginaar.spring.data.domain.Flight;
import com.enginaar.spring.data.domain.Person;
import com.enginaar.spring.data.repository.CityRepository;
import com.enginaar.spring.data.repository.FlightRepository;
import com.enginaar.spring.data.repository.PersonRepository;

@Component
public class ApplicationListener {
	
	  @Autowired
	  private PersonRepository persons;
	  @Autowired
	  private CityRepository cities;
	  @Autowired
	  private FlightRepository flights;
	  
	  @EventListener(ApplicationReadyEvent.class)
	  public void doSomethingAfterStartup() {
	    Person p = new Person();
	    p.setName("Kenan");
	    p.setLastName("Erarslan");
	    
	    persons.save(p);
	    
	    City a = new City();
	    a.setName("Ankara");
	    City i = new City();
	    i.setName("İstanbul");
	    cities.save(a);
	    cities.save(i);
	    
	    Flight f = new Flight();
	    f.setDate(new Date());
	    f.setPerson(p);
	    f.setOrigin(a);
	    f.setDestination(i);
	    
	    flights.save(f);
	  }

}
