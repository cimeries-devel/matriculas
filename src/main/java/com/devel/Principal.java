package com.devel;

import com.devel.hibernate.Hibernate;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilidades;
import com.devel.views.VLogin;

import javax.swing.*;
import java.awt.*;

public class Principal
{
    public static void main( String[] args ) {
        Hibernate.initialize();
        Propiedades propiedades=new Propiedades();
        Utilidades.tema(propiedades.getTema());
        VLogin vLogin=new VLogin(propiedades);
        vLogin.setVisible(true);
    }
}
