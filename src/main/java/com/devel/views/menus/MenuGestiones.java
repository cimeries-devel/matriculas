package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;
import com.devel.views.dialogs.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuGestiones {
    private JPanel panelPrincipal;
    private JButton nivelesButton;
    private JButton btnGrados;
    private JButton btnSeguros;
    private JButton btnSecciones;
    private JButton btnEscalasDePago;
    private JButton btnTiposdeDocumentos;
    private JButton usuariosButton;
    private DnDTabbedPane tabContenido;

    public MenuGestiones(DnDTabbedPane tabContenido){
        this.tabContenido=tabContenido;
        nivelesButton.addActionListener(e -> {
            cargarGestionNiveles();
        });
        btnGrados.addActionListener(e -> {
            cargarGestionarGrados();
        });
        btnSeguros.addActionListener(e -> {
            cargarGestionSeguros();
        });
        btnSecciones.addActionListener(e -> {
            cargarGestionarSecciones();
        });
        btnEscalasDePago.addActionListener(e -> {
            cargarGestionTarifas();
        });
        btnTiposdeDocumentos.addActionListener(e -> {
            cargarGestionTipoDocumentos();
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
