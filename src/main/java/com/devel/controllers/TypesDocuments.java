package com.devel.controllers;

import com.devel.hibernate.Hibernate;
import com.devel.models.ClassRoom;
import com.devel.models.TypeDocument;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Vector;

public class TypesDocuments extends Hibernate {
    private static Root<TypeDocument> root;
    private static CriteriaQuery<TypeDocument> criteria;
    private static Vector<TypeDocument> todos;

    public static TypeDocument get(Integer id) {
        TypeDocument typeDocument = SESSION.find(TypeDocument.class, id, LockModeType.NONE);
        return typeDocument;
    }
}
