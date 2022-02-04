package com.devel.models;

import com.devel.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "TBL_PHONE")
public class Phone extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer ID;
    @Column
    private String DESCRIPTION;
    @Column
    private String NUMBER;

    public Integer getID() {
        return ID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
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
