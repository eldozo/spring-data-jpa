package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.enginaar.spring.data.DataApplication;
import com.enginaar.spring.data.domain.Person;
import com.enginaar.spring.data.repository.impl.PersonRepositoryImpl;
import com.github.javafaker.Faker;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DataApplication.class)
class DataApplicationTests {

    private @Autowired
    @Qualifier("personRepositoryImpl")
    PersonRepositoryImpl persons;

    @Test
    void saveNew() {
        var saved = saveOne();
        assertThat(saved).isNotNull();
    }

    @Test
    void saveList() {
        var l = new LinkedList<Person>();
        for (int i = 0; i < 10; i++) {
            l.add(generatePerson());
        }

        Iterable<Person> all = persons.saveAll(l);
        assertThat(all).size().isEqualTo(l.size());
    }

    @Test
    void findAll() {
        var l = new LinkedList<Person>();
        for (int i = 0; i < 10; i++) {
            l.add(generatePerson());
        }

        Iterable<Person> saved = persons.saveAll(l);
        var ids = new LinkedList<Long>();
        
        for(var p : saved)
            ids.add(p.getId());
        Iterable<Person> found = persons.findAllById(ids);
        assertThat(found).size().isEqualTo(ids.size());
    }

    @Test
    void listAll() {
        System.out.println("Test startedß");
        Iterable<Person> all = persons.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    public void findById() {
        var saved = saveOne();
        Optional<Person> found = persons.findById(saved.getId());
        assertThat(found).isPresent();
    }

    @Test
    public void delete() {
        var person = saveOne();
        persons.delete(person);
        Optional<Person> found = persons.findById(person.getId());
        assertThat(found).isEmpty();
    }

    @Test
    public void deleteById() {
        var person = saveOne();
        persons.deleteById(person.getId());
        Optional<Person> found = persons.findById(person.getId());
        assertThat(found).isEmpty();
    }

    @Test
    public void update() {
        Person saved = saveOne();
        saved.setName("Updated " + saved.getName());
        Person updated = persons.save(saved);
        assertThat(updated.getName()).startsWith("Updated");

    }

    @Test
    public void findByName() {
        var person = saveOne();
        List<Person> list = persons.findByName(person.getName());
        assertThat(list).size().isGreaterThanOrEqualTo(1);
    }
    
    @Test
    void count() {
        long count = persons.count();
        assertThat(count).isGreaterThan(0);
    }

    private Person saveOne() {
        Person newOne = generatePerson();
        return persons.save(newOne);
    }

    private Person generatePerson() {
        var newOne = new Person();
        newOne.setName(Faker.instance().name().firstName());
        newOne.setLastName(Faker.instance().name().lastName());
        return newOne;
    }

}
