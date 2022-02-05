package com.devel.models;

import com.devel.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "TBL_DOCUMENT")
public class Document extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer ID;
    @Column
    private String NUMBER;
    @ManyToOne
    @JoinColumn(name = "FK_PERSON")
    private Person person;
    @ManyToOne
    @JoinColumn(name = "FK_TYPE_DOCUMENT")
    private TypeDocument typeDocument;

    public Integer getID() {
        return ID;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public TypeDocument getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
    }

    @Override
    public void save(){
        super.save();
    }
}
