package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Nivel;
import com.devel.models.TipoDocumento;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Niveles extends Hibernate {
    private static Root<Nivel> root;
    private static CriteriaQuery<Nivel> criteria;
    private static Vector<Nivel> todos;

    public static Nivel get(Integer id) {
        Nivel nivel = session.find(Nivel.class, id, LockModeType.NONE);
        return nivel;
    }
    public static Vector<Nivel> getTodos(){
        criteria = builder.createQuery(Nivel.class);
        criteria.select(criteria.from(Nivel.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }
}
