package com.devel.hibernate;

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
}