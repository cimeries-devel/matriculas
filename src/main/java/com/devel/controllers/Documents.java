package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;
import com.devel.models.Document;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class Documents extends Hibernate {
    private static Root<Document> root;
    private static CriteriaQuery<Document> criteria;
    private static Vector<Document> todos;

    public static Document get(Integer id) {
        Document document = SESSION.find(Document.class, id, LockModeType.NONE);
        return document;
    }
}
