package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Documento;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.print.Doc;
import java.util.Vector;

public class Documentos extends Hibernate {
    private static Root<Documento> root;
    private static CriteriaQuery<Documento> criteria;
    private static Vector<Documento> todos;

    public static Documento get(Integer id) {
        Documento documento = session.find(Documento.class, id, LockModeType.NONE);
        return documento;
    }

    public static Documento getByDni(String dni) {
        criteria = builder.createQuery(Documento.class);
        root = criteria.from(Documento.class);
        criteria.select(root)
                .where(builder.equal(root.get("numero"), dni));
        Documento documento = session.createQuery(criteria).uniqueResult();
        return documento;
    }
}
