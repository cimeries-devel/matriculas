package com.devel.hibernate;

import com.devel.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.criteria.CriteriaBuilder;

public class Hibernate {
    protected static Session SESSION;
    protected static CriteriaBuilder BUILDER;
    private static void buildSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        SESSION = sessionFactory.openSession();
        BUILDER = SESSION.getCriteriaBuilder();
    }
    public static void initialize() {
        buildSessionFactory();
    }
    public static void close(){
        SESSION.close();
    }

    public void save(){
        Integer id = null;
        if (this instanceof Person) {
            id = ((Person)(this)).getID();
            System.out.println("ratatata");
        }
        if (this instanceof User) {
            id = ((User)(this)).getID();
        }
        if (this instanceof College) {
            id = ((College)(this)).getID();
        }
        if (this instanceof Level) {
            id = ((Level)(this)).getID();
        }
        if (this instanceof Phone) {
            id = ((Phone)(this)).getID();
        }
        if (this instanceof Rate) {
            id = ((Rate)(this)).getID();
        }
        if (this instanceof Registration) {
            id = ((Registration)(this)).getID();
        }
        if (this instanceof Section) {
            id = ((Section)(this)).getID();
        }
        if (this instanceof Secure) {
            id = ((Secure)(this)).getID();
        }
        if (this instanceof TypeDocument) {
            id = ((TypeDocument)(this)).getID();
        }
        if (this instanceof Ubigeo) {
            id = ((Ubigeo)(this)).getID();
        }
        if (this instanceof ClassRoom) {
            id = ((ClassRoom)(this)).getID();
        }
        SESSION.beginTransaction();
        if (id != null) {
            SESSION.update(this);
        } else {
            SESSION.save(this);
        }
        SESSION.getTransaction().commit();

    }
}