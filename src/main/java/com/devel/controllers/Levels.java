package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;
import com.devel.models.Level;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Levels extends Hibernate {
    private static Root<Level> root;
    private static CriteriaQuery<Level> criteria;
    private static Vector<Level> todos;

    public static Level get(Integer id) {
        Level level = SESSION.find(Level.class, id, LockModeType.NONE);
        return level;
    }
}
