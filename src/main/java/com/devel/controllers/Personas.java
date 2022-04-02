package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Documento;
import com.devel.models.Nivel;
import com.devel.models.Persona;
import com.devel.models.Relacion;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Personas extends Hibernate {
    private static Root<Persona> root;
    private static CriteriaQuery<Persona> criteria;
    private static Vector<Persona> todos;

    public static Persona get(Integer id) {
        Persona persona = session.find(Persona.class, id, LockModeType.NONE);
        return persona;
    }

    public static Persona alumnoPorCodigo(String codigo){
        criteria= builder.createQuery(Persona.class);
        root=criteria.from(Persona.class);
        criteria.select(root).where(builder.equal(root.get("codigo"),codigo));
        return session.createQuery(criteria).uniqueResult();
    }

    public static Vector<Persona> alumnos(){
        criteria= builder.createQuery(Persona.class);
        root=criteria.from(Persona.class);
        criteria.select(root).where(builder.isNotNull(root.get("codigo")));
        todos=new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }

    public static Vector<Persona> familares(){
        criteria= builder.createQuery(Persona.class);
        root=criteria.from(Persona.class);
        criteria.select(root).where(builder.isNull(root.get("codigo")));
        todos=new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }
    public static boolean codigoRegistrado(String codigo){
        criteria= builder.createQuery(Persona.class);
        root=criteria.from(Persona.class);
        criteria.select(root).where(builder.equal(root.get("codigo"),codigo));
        Persona persona=session.createQuery(criteria).uniqueResult();
        return persona != null;
    }
}
