package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.seccion;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Sections extends Hibernate {
    private static Root<seccion> root;
    private static CriteriaQuery<seccion> criteria;
    private static Vector<seccion> todos;

    public static seccion get(Integer id) {
        seccion seccion = SESSION.find(seccion.class, id, LockModeType.NONE);
        return seccion;
    }
}
