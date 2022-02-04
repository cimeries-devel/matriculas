package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;
import com.devel.models.Person;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Persons extends Hibernate {
    private static Root<Person> root;
    private static CriteriaQuery<Person> criteria;
    private static Vector<Person> todos;

    public static Person get(Integer id) {
        Person person = SESSION.find(Person.class, id, LockModeType.NONE);
        return person;
    }
}
