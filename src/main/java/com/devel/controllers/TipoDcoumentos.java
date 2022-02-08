package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.TipoDocumento;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class TipoDcoumentos extends Hibernate {
    private static Root<TipoDocumento> root;
    private static CriteriaQuery<TipoDocumento> criteria;
    private static Vector<TipoDocumento> todos;

    public static TipoDocumento get(Integer id) {
        TipoDocumento tipoDocumento = session.find(TipoDocumento.class, id, LockModeType.NONE);
        return tipoDocumento;
    }
    public static Vector<TipoDocumento> getTodos(){
        criteria = builder.createQuery(TipoDocumento.class);
        criteria.select(criteria.from(TipoDocumento.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }
}
