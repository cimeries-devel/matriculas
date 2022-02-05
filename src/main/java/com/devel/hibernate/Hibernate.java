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
        if (this instanceof Persona) {
            id = ((Persona)(this)).getId();
            System.out.println("ratatata");
        }
        if (this instanceof Usuario) {
            id = ((Usuario)(this)).getId();
        }
        if (this instanceof Colegio) {
            id = ((Colegio)(this)).getId();
        }
        if (this instanceof Nivel) {
            id = ((Nivel)(this)).getId();
        }
        if (this instanceof Phone) {
            id = ((Phone)(this)).getId();
        }
        if (this instanceof Tarifa) {
            id = ((Tarifa)(this)).getId();
        }
        if (this instanceof Registro) {
            id = ((Registro)(this)).getId();
        }
        if (this instanceof seccion) {
            id = ((seccion)(this)).getId();
        }
        if (this instanceof Seguro) {
            id = ((Seguro)(this)).getId();
        }
        if (this instanceof TipoDocumento) {
            id = ((TipoDocumento)(this)).getId();
        }
        if (this instanceof Ubigeo) {
            id = ((Ubigeo)(this)).getId();
        }
        if (this instanceof Salon) {
            id = ((Salon)(this)).getId();
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