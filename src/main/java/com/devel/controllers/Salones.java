package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Nivel;
import com.devel.models.Salon;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Salones extends Hibernate {
    private static Root<Salon> root;
    private static CriteriaQuery<Salon> criteria;
    private static Vector<Salon> todos;

    public static Salon get(Integer id) {
        Salon salon = session.find(Salon.class, id, LockModeType.NONE);
        return salon;
    }
    public static Vector<Salon> getTodos(){
        criteria = builder.createQuery(Salon.class);
        criteria.select(criteria.from(Salon.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }
}
