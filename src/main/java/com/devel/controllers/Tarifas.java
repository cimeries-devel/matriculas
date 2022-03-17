package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Grado;
import com.devel.models.Tarifa;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Tarifas extends Hibernate {
    private static Root<Tarifa> root;
    private static CriteriaQuery<Tarifa> criteria;
    private static Vector<Tarifa> todas;

    public static Tarifa get(Integer id) {
        Tarifa tarifa = session.find(Tarifa.class, id, LockModeType.NONE);
        return tarifa;
    }
    public static Vector<Tarifa> getTodas(){
        criteria = builder.createQuery(Tarifa.class);
        root=criteria.from(Tarifa.class);
        criteria.select(root).orderBy(builder.asc(root.get("defecto")));
        todas = new Vector<>(session.createQuery(criteria).getResultList());
        return todas;
    }
    public static Tarifa tarifaActiva(){
        criteria= builder.createQuery(Tarifa.class);
        root=criteria.from(Tarifa.class);
        criteria.select(root)
                .where(builder.isTrue(root.get("defecto"))
                );
        return session.createQuery(criteria).uniqueResult();
    }
}
