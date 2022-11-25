package com.enginaar.spring.data.repository.impl;

import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.enginaar.spring.data.domain.Person;
import com.enginaar.spring.data.repository.PersonRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

@Component
public class PersonRepositoryImpl implements PersonRepository {

    @Autowired
    private JdbcTemplate db;
    private RowMapper<Person> extractor = (ResultSet rs, int rowNum) -> {
        Person person = new Person();
        try {
            person.setId(rs.getLong("id"));
            person.setName(rs.getString("name"));
            person.setLastName(rs.getString("last_name"));
            return person;
        } catch (SQLException ex) {
            return null;
        }
    };

    @Override

    public <S extends Person> S save(S entity) {
        if (entity.getId() == 0) {
            KeyHolder key = new GeneratedKeyHolder();
            db.update((PreparedStatementCreator) con -> {
                PreparedStatement stmt = con.prepareStatement("insert into person (name, last_name) values (?, ?)", new String[]{"id"});
                stmt.setString(1, entity.getName());
                stmt.setString(2, entity.getLastName());
                return stmt;
            }, key);
            return (S) findById(key.getKey().longValue()).get();
        } else {
            db.update("update person set name = ?, last_name = ? where id = ?", entity.getName(), entity.getLastName(), entity.getId());
            return (S) findById(entity.getId()).get();
        }
    }

    @Override
    public <S extends Person> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> list = new LinkedList<>();
        for (S entity : entities) {
            list.add(save(entity));
        }
        return list;
    }

    @Override
    public Optional<Person> findById(Long id) {
        try {
            Person p = db.queryForObject("select  * from person p where p.id = ?", extractor, id);
            return Optional.of(p);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public boolean existsById(Long id) {
        Optional<Person> findById = findById(id);
        return findById.isPresent();
    }

    @Override
    public Iterable<Person> findAll() {
        return db.query("select * from person", extractor);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterable<Person> findAllById(Iterable<Long> ids) {
        NamedParameterJdbcTemplate named = new NamedParameterJdbcTemplate(db.getDataSource());
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        return (Iterable<Person>) named.query("select * from person p where p.id in (:ids)", parameters, extractor);
    }

    @Override
    public long count() {
        return db.queryForObject("select count(*) from person", Integer.class);
    }

    @Override
    public void deleteById(Long id) {
        db.update("delete from person where id = ?", id);
    }

    @Override
    public void delete(Person entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends Person> entities) {
        for (var entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        db.update("delete from person");
    }

    @Override
    public List<Person> findByName(String name) {
        return db.query("select * from person p where p.name = ?", extractor, name);
    }

    @Override
    public List<Person> findByLastName(String lastname) {
        return db.query("select * from person p where p.last_name = ?", extractor, lastname);
    }

    @Override
    public int countByName(String name) {
        return db.queryForObject("select count(*) from person p where p.name = ?", Integer.class, name);
    }

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
	 
		
	}
}
