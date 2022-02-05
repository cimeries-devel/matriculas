package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Colegio;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Colleges extends Hibernate {
    private static Root<Colegio> root;
    private static CriteriaQuery<Colegio> criteria;
    private static Vector<Colegio> todos;

    public static Colegio get(Integer id) {
        Colegio colegio = SESSION.find(Colegio.class, id, LockModeType.NONE);
        return colegio;
    }


}
