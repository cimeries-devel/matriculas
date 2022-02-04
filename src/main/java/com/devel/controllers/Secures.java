package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;
import com.devel.models.Secure;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Secures extends Hibernate {
    private static Root<Secure> root;
    private static CriteriaQuery<Secure> criteria;
    private static Vector<Secure> todos;

    public static Secure get(Integer id) {
        Secure secure = SESSION.find(Secure.class, id, LockModeType.NONE);
        return secure;
    }
}
