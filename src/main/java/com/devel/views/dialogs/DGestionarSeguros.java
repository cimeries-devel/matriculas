package com.devel.views.dialogs;

import com.devel.utilities.JButoonEditors.JButtonEditorGrados;
import com.devel.utilities.JButoonEditors.JButtonEditorSeguros;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilities;
import com.devel.utilities.modelosTablas.SegurosAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        cargarTabla();
    }
    private void cargarCrearSeguro(){
        DAñadirSeguro dAñadirSeguro=new DAñadirSeguro();
        dAñadirSeguro.setVisible(true);
        tablaSeguros.updateUI();
        Utilities.headerNegrita(tablaSeguros);
        Utilities.cellsRendered(tablaSeguros);
    }
    private void cargarTabla(){
        segurosAbstractModel=new SegurosAbstractModel(VPrincipal.seguros);
        tablaSeguros.setModel(segurosAbstractModel);
        tablaSeguros.getColumnModel().getColumn(segurosAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorSeguros(tablaSeguros));
        TableCellRenderer renderer1 = tablaSeguros.getDefaultRenderer(JButton.class);
        tablaSeguros.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilities.definirTamaño(tablaSeguros.getColumn(""),30);
        Utilities.headerNegrita(tablaSeguros);
        Utilities.cellsRendered(tablaSeguros);
    }
}
