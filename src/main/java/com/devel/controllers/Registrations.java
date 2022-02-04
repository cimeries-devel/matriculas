package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;
import com.devel.models.Registration;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Registrations extends Hibernate {
    private static Root<Registration> root;
    private static CriteriaQuery<Registration> criteria;
    private static Vector<Registration> todos;

    public static Registration get(Integer id) {
        Registration registration = SESSION.find(Registration.class, id, LockModeType.NONE);
        return registration;
    }
}
