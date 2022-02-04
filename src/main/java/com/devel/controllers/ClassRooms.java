package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class ClassRooms extends Hibernate {
    private static Root<ClassRoom> root;
    private static CriteriaQuery<ClassRoom> criteria;
    private static Vector<ClassRoom> todos;

    public static ClassRoom get(Integer id) {
        ClassRoom classRoom = SESSION.find(ClassRoom.class, id, LockModeType.NONE);
        return classRoom;
    }
}
