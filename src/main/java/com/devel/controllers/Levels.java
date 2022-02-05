package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Nivel;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Levels extends Hibernate {
    private static Root<Nivel> root;
    private static CriteriaQuery<Nivel> criteria;
    private static Vector<Nivel> todos;

    public static Nivel get(Integer id) {
        Nivel nivel = SESSION.find(Nivel.class, id, LockModeType.NONE);
        return nivel;
    }
}
