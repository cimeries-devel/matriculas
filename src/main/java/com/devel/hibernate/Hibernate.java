package com.devel.hibernate;

import com.devel.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.criteria.CriteriaBuilder;

public class Hibernate {
    protected static Session session;
    protected static CriteriaBuilder builder;

    private static void buildSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();
        builder = session.getCriteriaBuilder();

    }
    public static void initialize() {
        buildSessionFactory();
    }
    public static void close(){
        session.close();
    }

    public void guardar(){
        Integer id = null;
        if (this instanceof Persona) {
            id = ((Persona)(this)).getId();
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
        if (this instanceof Celular) {
            id = ((Celular)(this)).getId();
        }
        if (this instanceof Tarifa) {
            id = ((Tarifa)(this)).getId();
        }
        if (this instanceof Registro) {
            id = ((Registro)(this)).getId();
        }
        if (this instanceof Seccion) {
            id = ((Seccion)(this)).getId();
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
        if (this instanceof Documento) {
            id = ((Documento)(this)).getId();
        }
        session.beginTransaction();
        if (id != null) {
            session.update(this);
        } else {
            session.save(this);
        }
        session.getTransaction().commit();

    }
}