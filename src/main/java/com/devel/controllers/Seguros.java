package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Seguro;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Seguros extends Hibernate {
    private static Root<Seguro> root;
    private static CriteriaQuery<Seguro> criteria;
    private static Vector<Seguro> todos;

    public static Seguro get(Integer id) {
        Seguro seguro = session.find(Seguro.class, id, LockModeType.NONE);
        return seguro;
    }
}
