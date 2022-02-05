package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Persona;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Persons extends Hibernate {
    private static Root<Persona> root;
    private static CriteriaQuery<Persona> criteria;
    private static Vector<Persona> todos;

    public static Persona get(Integer id) {
        Persona persona = SESSION.find(Persona.class, id, LockModeType.NONE);
        return persona;
    }
}
