package com.devel.app;

import com.devel.hibernate.Hibernate;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilities;
import com.devel.views.VLogin;

public class Principal
{
    public static void main( String[] args ){
        Hibernate.initialize();
        Propiedades propiedades=new Propiedades();
        Utilities.tema(propiedades.getTema());
        VLogin vLogin=new VLogin();
        vLogin.setVisible(true);
    }
}
