package com.devel.views.dialogs;

import com.devel.utilities.Colors;
import com.devel.utilities.JButoonEditors.JButtonEditorSeguros;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.SegurosAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;

public class DGestionarSeguros extends JDialog{
    private JTable tablaSeguros;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private SegurosAbstractModel segurosAbstractModel;
    public DGestionarSeguros() {
        iniciarComponentes();
        btnAñadir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarCrearSeguro();
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cerrar();
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void iniciarComponentes(){
        setTitle("Seguros");
        setContentPane(panelPrincipal);
        cargarTabla();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        cargarConfiguracion();
    }
    private void cargarCrearSeguro(){
        DAñadirSeguro dAñadirSeguro=new DAñadirSeguro();
        dAñadirSeguro.setVisible(true);
        Utilidades.actualizarTabla(tablaSeguros);
    }
    private void cargarTabla(){
        segurosAbstractModel=new SegurosAbstractModel( VPrincipal.seguros);
        tablaSeguros.setModel(segurosAbstractModel);
        tablaSeguros.getColumnModel().getColumn(segurosAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorSeguros(tablaSeguros));
        TableCellRenderer renderer1 = tablaSeguros.getDefaultRenderer(JButton.class);
        tablaSeguros.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.definirTamaño(tablaSeguros.getColumn(""),30);
        Utilidades.headerNegrita(tablaSeguros);
        Utilidades.cellsRendered(tablaSeguros);
    }
    private void cerrar(){
        dispose();
    }

    private void cargarConfiguracion(){
        switch (VPrincipal.tema){
            case "oscuro":
                btnHecho.setForeground(new Color(0xFFFFFF));
                btnAñadir.setBackground(Colors.buttonDefect2);
                break;
            default:
                btnHecho.setForeground(new Color(0x000000));
                btnAñadir.setForeground(Color.white);
                btnAñadir.setBackground(Colors.buttonDefect1);
                break;
        }
    }
}
