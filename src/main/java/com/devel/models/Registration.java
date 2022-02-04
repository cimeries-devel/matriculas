package com.devel.models;

import com.devel.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "TBL_REGISTRATION")
public class Registration extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer ID;
    @Column
    private Date CREATED;
    @Column
    private Date UPDATED;

    @ManyToOne
    @JoinColumn(name = "FK_CLASSROOM")
    private ClassRoom classRoom;

    @ManyToOne
    @JoinColumn(name = "FK_STUDENT")
    private Person student;

    @ManyToOne
    @JoinColumn(name = "FK_RATE")
    private Rate rate;

    public Integer getID() {
        return ID;
    }

    public Date getCREATED() {
        return CREATED;
    }

    public void setCREATED(Date CREATED) {
        this.CREATED = CREATED;
    }

    public Date getUPDATED() {
        return UPDATED;
    }

    public void setUPDATED(Date UPDATED) {
        this.UPDATED = UPDATED;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person person) {
        this.student = person;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
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
