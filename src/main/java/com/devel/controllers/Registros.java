package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Registro;
import com.devel.utilities.Utilidades;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class Registros extends Hibernate {
    private static Root<Registro> root;
    private static CriteriaQuery<Registro> criteria;
    private static Vector<Registro> todos;

    public static Registro get(Integer id) {
        Registro registro = session.find(Registro.class, id, LockModeType.NONE);
        return registro;
    }

    public static Vector<Registro> getMatriculados(){
        Calendar primerDia=Calendar.getInstance();
        primerDia.set(Calendar.MONTH,primerDia.getActualMinimum(Calendar.MONTH));
        primerDia.set(Calendar.DATE,primerDia.getActualMinimum(Calendar.DATE));
        primerDia.add(primerDia.DATE,-1);
        primerDia.set(Calendar.HOUR_OF_DAY,23);
        primerDia.set(Calendar.MINUTE,59);
        criteria=builder.createQuery(Registro.class);
        root = criteria.from(Registro.class);
        criteria.select(root).where(
                builder.between(root.get("creacion"),primerDia.getTime(),new Date())
        );
        todos=new Vector<>(session.createQuery(criteria).getResultList());
        return Utilidades.invertirVector(todos);
    }
}
