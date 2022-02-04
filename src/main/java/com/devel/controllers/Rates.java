package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;
import com.devel.models.Rate;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Rates extends Hibernate {
    private static Root<Rate> root;
    private static CriteriaQuery<Rate> criteria;
    private static Vector<Rate> todos;

    public static Rate get(Integer id) {
        Rate rate = SESSION.find(Rate.class, id, LockModeType.NONE);
        return rate;
    }
}
