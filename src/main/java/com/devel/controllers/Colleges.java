package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;
import com.devel.models.College;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Colleges extends Hibernate {
    private static Root<College> root;
    private static CriteriaQuery<College> criteria;
    private static Vector<College> todos;

    public static College get(Integer id) {
        College college = SESSION.find(College.class, id, LockModeType.NONE);
        return college;
    }


}
