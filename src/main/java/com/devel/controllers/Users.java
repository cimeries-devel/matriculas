package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;
import com.devel.models.User;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Users extends Hibernate {
    private static Root<User> root;
    private static CriteriaQuery<User> criteria;
    private static Vector<User> todos;

    public static User get(Integer id) {
        User user = SESSION.find(User.class, id, LockModeType.NONE);
        return user;
    }

}
