package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Documento;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Documents extends Hibernate {
    private static Root<Documento> root;
    private static CriteriaQuery<Documento> criteria;
    private static Vector<Documento> todos;

    public static Documento get(Integer id) {
        Documento documento = SESSION.find(Documento.class, id, LockModeType.NONE);
        return documento;
    }
}
