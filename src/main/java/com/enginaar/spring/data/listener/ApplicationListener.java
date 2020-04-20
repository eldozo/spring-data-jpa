package com.enginaar.spring.data.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.enginaar.spring.data.domain.Person;

@Component
public class ApplicationListener {

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		Person p = new Person();
		p.setName("Kenan");
		p.setLastName("Erarslan");
		
	}

}
