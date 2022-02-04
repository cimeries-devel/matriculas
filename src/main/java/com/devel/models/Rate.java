package com.devel.models;

import com.devel.app.Principal;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import com.devel.hibernate.Hibernate;

@Entity(name = "TBL_RATE")
public class Rate extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer ID;
    @Column
    private boolean DEFAULTRATE;
    @Column
    private String DESCRIPTION;
    @Column
    private Date CREATED;
    @Column
    private Double PRICE;

    public Integer getID() {
        return ID;
    }

    public boolean isDEFAULTRATE() {
        return DEFAULTRATE;
    }

    public void setDEFAULTRATE(boolean DEFAULTRATE) {
        this.DEFAULTRATE = DEFAULTRATE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public Date getCREATED() {
        return CREATED;
    }

    public void setCREATED(Date CREATED) {
        this.CREATED = CREATED;
    }

    public Double getPRICE() {
        return PRICE;
    }

    public void setPRICE(Double PRICE) {
        this.PRICE = PRICE;
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
