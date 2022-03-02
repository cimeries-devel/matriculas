package com.devel.app;

import com.devel.controllers.Registros;
import com.devel.hibernate.Hibernate;
import com.devel.utilities.Utilities;
import com.devel.views.VLogin;
import com.devel.views.VPrincipal;

public class Principal
{
    public static void main( String[] args ){
        Hibernate.initialize();
        Utilities.cambiarThemaIntellij();

//        VPrincipal vPrincipal=new VPrincipal();
//        vPrincipal.setVisible(true);
        VLogin vLogin=new VLogin();
        vLogin.setVisible(true);
    }
}
