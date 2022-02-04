package com.devel.models;

import com.devel.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "TBL_PERSON")
public class Person extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer ID;
    @Column
    private String ADDRESS;
    @Column
    private int AGE;
    @Column
    private Date BIRTHDATE;
    @Column
    private Date CREATED;
    @Column
    private String EMAIL;
    @Column
    private String FIRSTNAME;
    @Column
    private String LASTNAME;
    @Column
    private boolean LIVETOGETHER;
    @Column
    private Date UPDATED;
    @Column
    private String CODE;
    @OneToMany(mappedBy = "person")
    private List<Document> documents = new ArrayList<>();
    @OneToMany(mappedBy = "person")
    private List<Relationship> relationships = new ArrayList<>();
    @OneToMany()
    private List<Phone> phones = new ArrayList<>();
    @OneToMany()
    private List<Secure> secures = new ArrayList<>();

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public int getAGE() {
        return AGE;
    }

    public void setAGE(int AGE) {
        this.AGE = AGE;
    }

    public Date getBIRTHDATE() {
        return BIRTHDATE;
    }

    public void setBIRTHDATE(Date BIRTHDATE) {
        this.BIRTHDATE = BIRTHDATE;
    }

    public Date getCREATED() {
        return CREATED;
    }

    public void setCREATED(Date CREATED) {
        this.CREATED = CREATED;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public void setFIRSTNAME(String FIRSTNAME) {
        this.FIRSTNAME = FIRSTNAME;
    }

    public String getLASTNAME() {
        return LASTNAME;
    }

    public void setLASTNAME(String LASTNAME) {
        this.LASTNAME = LASTNAME;
    }

    public boolean isLIVETOGETHER() {
        return LIVETOGETHER;
    }

    public void setLIVETOGETHER(boolean LIVETOGETHER) {
        this.LIVETOGETHER = LIVETOGETHER;
    }

    public Date getUPDATED() {
        return UPDATED;
    }

    public void setUPDATED(Date UPDATED) {
        this.UPDATED = UPDATED;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    public List<Secure> getSecures() {
        return secures;
    }

    public void setSecures(List<Secure> secures) {
        this.secures = secures;
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
