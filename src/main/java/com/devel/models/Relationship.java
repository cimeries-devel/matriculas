package com.devel.models;

import com.devel.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "TBL_RELATIONSHIP")
public class Relationship extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer ID;

    @Column
    private String TYPERELATION;

    @ManyToOne
    @JoinColumn(name = "FK_RELATIONSHIP")
    private Relationship relationship;

    @ManyToOne
    @JoinColumn(name = "FK_PERSON")
    private Person person;

    public Integer getID() {
        return ID;
    }

    public String getTYPERELATION() {
        return TYPERELATION;
    }

    public void setTYPERELATION(String TYPERELATION) {
        this.TYPERELATION = TYPERELATION;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void guardar() {
        SESSION.beginTransaction();
        if (getID()==null) {
            SESSION.save(this);
        } else {
            SESSION.update(this);
        }
        SESSION.getTransaction().commit();
    }
}
