package com.enginaar.spring.data.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 *
 * @author kenanerarslan
 */
@Data
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    
    @ManyToOne
    @JoinColumn(name = "origin", referencedColumnName = "id")
    private City origin;
    
    @ManyToOne
    @JoinColumn(name = "destination", referencedColumnName = "id")
    private City destination;
    
    private Date date;

}
