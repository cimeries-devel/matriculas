package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Seccion;
import com.devel.models.Seguro;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Secciones extends Hibernate {
    private static Root<Seccion> root;
    private static CriteriaQuery<Seccion> criteria;
    private static Vector<Seccion> todos;

    public static Seccion get(Integer id) {
        Seccion seccion = session.find(Seccion.class, id, LockModeType.NONE);
        return seccion;
    }
    public static Vector<Seccion> todos(){
        criteria = builder.createQuery(Seccion.class);
        criteria.select(criteria.from(Seccion.class));
        todos= new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }
    public static Vector<Seccion> todosConTodos(){
        criteria = builder.createQuery(Seccion.class);
        criteria.select(criteria.from(Seccion.class));
        todos= new Vector<>(session.createQuery(criteria).getResultList());
        Seccion seccion=new Seccion();
        seccion.setSeccion("Todos");
        todos.add(0,seccion);
        return todos;
    }
}
