package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.TipoDocumento;
import com.devel.models.TipoRelacion;
import org.apache.poi.ss.formula.functions.T;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class TiposRelaciones extends Hibernate {
    private static Root<TipoRelacion> root;
    private static CriteriaQuery<TipoRelacion> criteria;
    private static Vector<TipoRelacion> todos;

    public static TipoRelacion get(Integer id) {
        TipoRelacion tipoRelacion = session.find(TipoRelacion.class, id, LockModeType.NONE);
        return tipoRelacion;
    }

    public static Vector<TipoRelacion> getTodos(){
        criteria = builder.createQuery(TipoRelacion.class);
        criteria.select(criteria.from(TipoRelacion.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }

    public static Vector<TipoRelacion> getTodosConTodos(){
        criteria = builder.createQuery(TipoRelacion.class);
        criteria.select(criteria.from(TipoRelacion.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        TipoRelacion tipoRelacion=new TipoRelacion();
        tipoRelacion.setTipo("TODOS");
        todos.add(0,tipoRelacion);
        return todos;
    }
}
