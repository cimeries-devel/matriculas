package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Registro;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Registrations extends Hibernate {
    private static Root<Registro> root;
    private static CriteriaQuery<Registro> criteria;
    private static Vector<Registro> todos;

    public static Registro get(Integer id) {
        Registro registro = SESSION.find(Registro.class, id, LockModeType.NONE);
        return registro;
    }
}
