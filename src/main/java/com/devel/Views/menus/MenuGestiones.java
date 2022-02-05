package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;

import javax.swing.*;

public class MenuGestiones {
    private JButton usuariosButton;
    private JPanel panelPrincipal;
    private DnDTabbedPane tabContenido;
    public MenuGestiones(DnDTabbedPane tabContenido){
        this.tabContenido=tabContenido;

    }
    public JPanel traerInicioOpciones() {
        return panelPrincipal;
    }

}
