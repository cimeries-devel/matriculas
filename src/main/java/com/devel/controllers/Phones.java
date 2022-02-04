package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;
import com.devel.models.Phone;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Phones extends Hibernate {
    private static Root<Phone> root;
    private static CriteriaQuery<Phone> criteria;
    private static Vector<Phone> todos;

    public static Phone get(Integer id) {
        Phone phone = SESSION.find(Phone.class, id, LockModeType.NONE);
        return phone;
    }
}
