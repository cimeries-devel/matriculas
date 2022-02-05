package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Tarifa;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Tarifas extends Hibernate {
    private static Root<Tarifa> root;
    private static CriteriaQuery<Tarifa> criteria;
    private static Vector<Tarifa> todos;

    public static Tarifa get(Integer id) {
        Tarifa tarifa = session.find(Tarifa.class, id, LockModeType.NONE);
        return tarifa;
    }
}
