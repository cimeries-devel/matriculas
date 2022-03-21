package com.devel.views.tabs;

import com.devel.Principal;
import com.devel.custom.FondoPanel;
import com.devel.custom.TabPanel;

import javax.swing.*;

public class VWelcome extends JFrame{
    private JPanel panelContenido;
    private TabPanel panelPrincipal;

    public VWelcome() {
        setTitle("Inicio");
        panelPrincipal.setIcon(new ImageIcon(Principal.class.getResource("Icons/x24/inicio.png")));
    }
    public TabPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        panelContenido=new FondoPanel("Images/fondo2.jpg");
    }
}
