package com.devel.app;

import com.devel.Views.Dashboard;
import com.devel.hibernate.Hibernate;
import com.devel.utilities.Utilities;

public class Principal
{
    public static void main( String[] args ){
        Hibernate.initialize();
        Utilities.cambiarWindows();
        Dashboard dashboard = new Dashboard();
        dashboard.pack();
        dashboard.setVisible(true);
    }
}
