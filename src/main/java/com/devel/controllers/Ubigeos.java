package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Ubigeo;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Ubigeos extends Hibernate {
    private static Root<Ubigeo> root;
    private static CriteriaQuery<Ubigeo> criteria;
    private static Vector<Ubigeo> todos;

    public static Ubigeo get(Integer id) {
        Ubigeo ubigeo = SESSION.find(Ubigeo.class, id, LockModeType.NONE);
        return ubigeo;
    }
}
