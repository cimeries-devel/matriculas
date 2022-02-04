package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;
import com.devel.models.Section;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Sections extends Hibernate {
    private static Root<Section> root;
    private static CriteriaQuery<Section> criteria;
    private static Vector<Section> todos;

    public static Section get(Integer id) {
        Section section = SESSION.find(Section.class, id, LockModeType.NONE);
        return section;
    }
}
