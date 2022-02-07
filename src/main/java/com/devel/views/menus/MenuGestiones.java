package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;
import com.devel.views.dialogs.DGestionNiveles;
import com.devel.views.dialogs.DGestionarGrados;
import com.devel.views.dialogs.DGestionarSeguros;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuGestiones {
    private JButton usuariosButton;
    private JPanel panelPrincipal;
    private JButton nivelesButton;
    private JButton btnGrados;
    private JButton btnSeguros;
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
        btnGrados.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarGestionarGrados();
            }
        });
        btnSeguros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarGestionSeguros();
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
    private void cargarGestionSeguros(){
        DGestionarSeguros dGestionNiveles=new DGestionarSeguros();
        dGestionNiveles.setVisible(true);
    }
    private void cargarGestionarGrados(){
        DGestionarGrados dGestionarGrados=new DGestionarGrados();
        dGestionarGrados.setVisible(true);
    }

}
