package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Usuario;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Usuarios extends Hibernate {
    private static Root<Usuario> root;
    private static CriteriaQuery<Usuario> criteria;
    private static Vector<Usuario> todos;

    public static Usuario get(Integer id) {
        Usuario usuario = session.find(Usuario.class, id, LockModeType.NONE);
        return usuario;
    }

}
