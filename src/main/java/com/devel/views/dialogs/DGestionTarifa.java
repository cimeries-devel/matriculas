package com.devel.views.dialogs;

import com.devel.models.Tarifa;
import com.devel.utilities.JButoonEditors.JButtonEditroTarifas;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.TarifasAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class DGestionTarifa extends JDialog{
    private JTable tablaTarifas;
    private JButton hechoButton;
    private JButton nuevoButton;
    private JPanel panelPrincipal;
    private TarifasAbstractModel tarifasAbstractModel;
    public DGestionTarifa() {
        iniciarComponentes();
        nuevoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        nuevoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarNueva();
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
        setTitle("Tarifas");
        setContentPane(panelPrincipal);
        cargarTabla();
        pack();
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(null);
    }
    private void cargarNueva(){
        DAñadirTarifa añadirTarifa=new DAñadirTarifa();
        añadirTarifa.setVisible(true);
        tablaTarifas.updateUI();
        Utilidades.headerNegrita(tablaTarifas);
        Utilidades.cellsRendered(tablaTarifas,VPrincipal.tarifas);
    }
    private void cargarTabla(){
        tarifasAbstractModel =new TarifasAbstractModel(VPrincipal.tarifas);
        tablaTarifas.setModel(tarifasAbstractModel);
        tablaTarifas.getColumnModel().getColumn(tarifasAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditroTarifas(tablaTarifas,"editar"));
        tablaTarifas.getColumnModel().getColumn(tarifasAbstractModel.getColumnCount()-2).setCellEditor(new JButtonEditroTarifas(tablaTarifas,"defecto"));
        TableCellRenderer renderer1 = tablaTarifas.getDefaultRenderer(JButton.class);
        tablaTarifas.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
//        Utilidades.definirTamaño(tablaTarifas.getColumn("Tarifa"),80);
//        Utilidades.definirTamaño(tablaTarifas.getColumn("Activa"),40);
//        Utilidades.definirTamaño(tablaTarifas.getColumn("Fecha creación"),110);
        Utilidades.cellsRendered(tablaTarifas,VPrincipal.tarifas);
        Utilidades.headerNegrita(tablaTarifas);
    }
}
