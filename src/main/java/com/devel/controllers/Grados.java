package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Grado;
import com.devel.models.Nivel;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Grados extends Hibernate {
    private static Root<Grado> root;
    private static CriteriaQuery<Grado> criteria;
    private static Vector<Grado> todos;

    public static Grado get(Integer id) {
        Grado nivel = session.find(Grado.class, id, LockModeType.NONE);
        return nivel;
    }
    public static Vector<Grado> getTodos(){
        criteria = builder.createQuery(Grado.class);
        criteria.select(criteria.from(Grado.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }
}
