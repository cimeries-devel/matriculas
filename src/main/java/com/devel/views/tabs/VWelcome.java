package com.devel.views.tabs;

import com.devel.custom.FondoPanel;

import javax.swing.*;

public class VWelcome extends JFrame{
    private JPanel panelPrincipal;
    public VWelcome() {
        setTitle("Inicio");
    }
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        panelPrincipal=new FondoPanel("Images/fondo2.jpg");
    }
}
