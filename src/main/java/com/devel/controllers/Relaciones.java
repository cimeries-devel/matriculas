package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Persona;
import com.devel.models.Relacion;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Relaciones extends Hibernate {
    private static Root<Relacion> root;
    private static CriteriaQuery<Relacion> criteria;
    private static Vector<Relacion> todas;

    public static Relacion get(Integer id) {
        Relacion relacion = session.find(Relacion.class, id, LockModeType.NONE);
        return relacion;
    }
    public static Vector<Relacion> getRelaciones(Persona persona){
        criteria= builder.createQuery(Relacion.class);
        root=criteria.from(Relacion.class);
        criteria.select(root).where(builder.equal(root.get("persona"),persona));
        todas=new Vector<>(session.createQuery(criteria).getResultList());
        return todas;
    }
}
