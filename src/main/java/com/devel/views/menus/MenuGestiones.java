package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;
import com.devel.views.dialogs.DGestionNiveles;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuGestiones {
    private JButton usuariosButton;
    private JPanel panelPrincipal;
    private JButton nivelesButton;
    private DnDTabbedPane tabContenido;
    public MenuGestiones(DnDTabbedPane tabContenido){
        this.tabContenido=tabContenido;

        nivelesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarGestionNiveles();
            }
        });
    }
    public JPanel traerInicioOpciones() {
        return panelPrincipal;
    }

    private void cargarGestionNiveles(){
        DGestionNiveles dGestionNiveles=new DGestionNiveles();
        dGestionNiveles.setVisible(true);
    }

}
