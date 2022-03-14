package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;
import com.devel.views.dialogs.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuGestiones {
    private JButton usuariosButton;
    private JPanel panelPrincipal;
    private JButton nivelesButton;
    private JButton btnGrados;
    private JButton btnSeguros;
    private JButton btnSecciones;
    private JButton btnEscalasDePago;
    private JButton btnTiposdeDocumentos;
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
        btnSecciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarGestionarSecciones();
            }
        });
        btnEscalasDePago.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarGestionTarifas();
            }
        });
        btnTiposdeDocumentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarGestionTipoDocumentos();
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
    private void cargarGestionTarifas(){
        DGestionTarifa dGestionNiveles=new DGestionTarifa();
        dGestionNiveles.setVisible(true);
    }
    private void cargarGestionarSecciones(){
        DGestionSecciones dGestionSecciones=new DGestionSecciones();
        dGestionSecciones.setVisible(true);
    }
    private void cargarGestionSeguros(){
        DGestionarSeguros dGestionNiveles=new DGestionarSeguros();
        dGestionNiveles.setVisible(true);
    }
    private void cargarGestionarGrados(){
        DGestionarGrados dGestionarGrados=new DGestionarGrados();
        dGestionarGrados.setVisible(true);
    }
    private void cargarGestionTipoDocumentos(){
        DGestionTipoDocumento dGestionarGrados=new DGestionTipoDocumento();
        dGestionarGrados.setVisible(true);
    }

}
