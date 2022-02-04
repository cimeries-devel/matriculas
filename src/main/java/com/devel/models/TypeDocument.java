package com.devel.models;

import com.devel.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "TBL_TYPE_DOCUMENT")
public class TypeDocument extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer ID;

    @Column
    private String CODE;

    @Column
    private String DESCRIPTION;

    public Integer getID() {
        return ID;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
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
