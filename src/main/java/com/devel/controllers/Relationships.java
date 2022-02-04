package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;
import com.devel.models.Relationship;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Relationships extends Hibernate {
    private static Root<Relationship> root;
    private static CriteriaQuery<Relationship> criteria;
    private static Vector<Relationship> todos;

    public static Relationship get(Integer id) {
        Relationship relationship = SESSION.find(Relationship.class, id, LockModeType.NONE);
        return relationship;
    }
}
