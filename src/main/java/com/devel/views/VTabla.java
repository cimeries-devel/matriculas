package com.devel.views;

import com.devel.utilities.Utilidades;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

public class VTabla extends JDialog  {
    private JPanel panelPrincipal;
    private JTable tablaDatos;
    private JButton btnHecho;
    private List<String> columnas;
    private List<Object[]> datos;
    private DefaultTableModel modelo;

    public VTabla(List<String> columnas,List<Object[]> datos) {
        this.columnas=columnas;
        this.datos=datos;
        iniciarComponentes();
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        });
        panelPrincipal.registerKeyboardAction(e -> cerrar(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void iniciarComponentes(){
        setContentPane(panelPrincipal);
        setTitle("Vista previa");
        cargarDatos();
        getRootPane().setDefaultButton(btnHecho);
        setModal(true);
        pack();
        setLocationRelativeTo(null);
    }
    private void cargarDatos(){
        tablaDatos.setModel(new DefaultTableModel(null, columnas.toArray()));
        modelo=(DefaultTableModel) tablaDatos.getModel();
        for (Object[] object:datos){
            modelo.addRow(object);
        }
        Utilidades.headerNegrita(tablaDatos);
        Utilidades.cellsRendered(tablaDatos);
    }
    private void cerrar(){
        dispose();
    }
}
