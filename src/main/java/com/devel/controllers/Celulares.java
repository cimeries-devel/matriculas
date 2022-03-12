package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Celular;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Celulares extends Hibernate {
    private static Root<Celular> root;
    private static CriteriaQuery<Celular> criteria;
    private static Vector<Celular> todos;

    public static Celular get(Integer id) {
        Celular celular = session.find(Celular.class, id, LockModeType.NONE);
        return celular;
    }
}
