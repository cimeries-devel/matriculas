package com.devel.app;

import com.devel.hibernate.Hibernate;
import com.devel.utilities.Utilities;
import com.devel.views.VPrincipal;

public class Principal
{
    public static void main( String[] args ){
        Hibernate.initialize();
        Utilities.cambiarWindows();
//        Person person1=Persons.get(1);
//        person1.setFIRSTNAME("niño rata");
//        person1.save();

//        Person person=new Person();
//        person.setFIRSTNAME("padre");
//        person.setPerson(person1);
//        person.save();
//
//        Person person2=new Person();
//        person2.setFIRSTNAME("madre");
//        person2.setPerson(person1);
//        person2.save();
        VPrincipal vPrincipal=new VPrincipal();
        vPrincipal.pack();
        vPrincipal.setVisible(true);
    }
}
