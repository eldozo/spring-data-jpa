package com.enginaar.spring.data.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.enginaar.spring.data.domain.Person;
import com.enginaar.spring.data.repository.PersonRepository;

public class PersonRepositoryImpl implements PersonRepository {
	@Autowired
	private JdbcTemplate db;

	@Override
	public <S extends Person> S save(S entity) { 
		if(entity.getId() == 0) {
			KeyHolder key = new GeneratedKeyHolder();
			db.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement stmt = con.prepareStatement("insert into Person (name, last_name) values (?, ?)");
					stmt.setString(1, entity.getName());
					stmt.setString(2, entity.getLastName());
					return stmt;
				}
				
			}, key); 
			return (S) findById(key.getKey().longValue()).get();
		} else {
			db.update("update person set name = ?, last_name = ? where id = ?", entity.getName(), entity.getLastName(), entity.getId());
			return (S) findById(entity.getId()).get();
		} 
		db.update("insert into Person (name, last_name) values (?, ?)", entity.getName(), entity.getLastName())
	}

	@Override
	public <S extends Person> Iterable<S> saveAll(Iterable<S> entities) {
		List<S> list = new LinkedList<>();
		for(S entity : entities)
			list.add(save(entity));
		return list;
	}

	@Override
	public Optional<Person> findById(Long id) {
		 Person p = db.query("select  * from person p where p.id = ?", new ResultSetExtractor<Person>() {

			@Override
			public Person extractData(ResultSet rs) throws SQLException, DataAccessException {
				Person person = new Person();
				person.setId(rs.getLong("id"));
				person.setName(rs.getString("name"));
				person.setLastName(rs.getString("last_name"));
				
				return person;
			}
			
		}, id); 
		return Optional.ofNullable(p);
	}

	@Override
	public boolean existsById(Long id) {
		Optional<Person> findById = findById(id);
		return findById.isPresent();
	}

	@Override
	public Iterable<Person> findAll() {
		return db.queryForList("select * from person", Person.class);
	}

	@Override
	public Iterable<Person> findAllById(Iterable<Long> ids) {
		return null;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public void delete(Person entity) {
		
	}

	@Override
	public void deleteAll(Iterable<? extends Person> entities) {
		
	}

	@Override
	public void deleteAll() {
		
	}

	@Override
	public List<Person> findByName(String name) {
		return null;
	}

	@Override
	public List<Person> findByLastName(String lastname) {
		return null;
	}

	@Override
	public int countByName(String name) {
		return 0;
	} 
}
