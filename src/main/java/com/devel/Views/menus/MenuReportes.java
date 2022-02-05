package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;

import javax.swing.*;

public class MenuReportes {
    private JButton alumnosButton;
    private JPanel panelPrincipal;
    private DnDTabbedPane tabContenido;
    public MenuReportes(DnDTabbedPane tabContenido){
        this.tabContenido=tabContenido;
    }

    public JPanel traerReportesOpciones() {
        return panelPrincipal;
    }
}
