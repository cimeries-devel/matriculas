package com.devel.views.dialogs;

import com.devel.models.Seguro;
import com.devel.utilities.JButoonEditors.JButtonEditorSeguros;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.SegurosAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class DGestionarSeguros extends JDialog{
    private JTable tablaSeguros;
    private JButton nuevoButton;
    private JButton hechoButton;
    private JPanel panelPrincipal;
    private SegurosAbstractModel segurosAbstractModel;
    public DGestionarSeguros() {
        iniciarComponentes();
        nuevoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarCrearSeguro();
            }
        });
        hechoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }
    private void iniciarComponentes(){
        setTitle("Seguros");
        setContentPane(panelPrincipal);
        cargarTabla();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }
    private void cargarCrearSeguro(){
        DAñadirSeguro dAñadirSeguro=new DAñadirSeguro();
        dAñadirSeguro.setVisible(true);
        tablaSeguros.updateUI();
        Utilidades.headerNegrita(tablaSeguros);
        Utilidades.cellsRendered(tablaSeguros);
    }
    private void cargarTabla(){
        segurosAbstractModel=new SegurosAbstractModel((Vector<Seguro>) VPrincipal.seguros);
        tablaSeguros.setModel(segurosAbstractModel);
        tablaSeguros.getColumnModel().getColumn(segurosAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorSeguros(tablaSeguros));
        TableCellRenderer renderer1 = tablaSeguros.getDefaultRenderer(JButton.class);
        tablaSeguros.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.definirTamaño(tablaSeguros.getColumn(""),30);
        Utilidades.headerNegrita(tablaSeguros);
        Utilidades.cellsRendered(tablaSeguros);
    }
}
