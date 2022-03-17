package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.Seguro;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.*;
import java.util.Vector;

public class Seguros extends Hibernate {
    private static Root<Seguro> root;
    private static CriteriaQuery<Seguro> criteria;
    private static Vector<Seguro> todos;

    public static Seguro get(Integer id) {
        Seguro seguro = session.find(Seguro.class, id, LockModeType.NONE);
        return seguro;
    }
    public static Vector<Seguro> todos(){
        criteria = builder.createQuery(Seguro.class);
        criteria.select(criteria.from(Seguro.class));
        todos= new Vector<>(session.createQuery(criteria).getResultList());
        verificarSeguros();
        return todos;
    }
    public static Vector<Seguro> todosconTodos(){
        criteria = builder.createQuery(Seguro.class);
        criteria.select(criteria.from(Seguro.class));
        todos= new Vector<>(session.createQuery(criteria).getResultList());
        verificarSeguros();
        System.out.println(todos.size());
        Seguro seguro=new Seguro();
        seguro.setDescripcion("Todos");
        todos.add(0,seguro);
        return todos;
    }

    private static void verificarSeguros(){
        if (todos.isEmpty()){
            Seguro sis=new Seguro();
            sis.setCodigo("SIS");
            sis.setDescripcion("Seguro Integral de Salud");
            sis.guardar();
            Seguro essalud=new Seguro();
            essalud.setCodigo("EsSalud");
            essalud.setDescripcion("Seguro Social de Salud del Perú");
            essalud.guardar();
            todos.add(sis);
            todos.add(essalud);
        }
    }
}
