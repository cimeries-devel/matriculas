package com.devel.views.dialogs;

import com.devel.utilities.Colors;
import com.devel.utilities.JButoonEditors.JButtonEditroTarifas;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.TarifasAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;

public class DGestionTarifa extends JDialog{
    private JTable tablaTarifas;
    private JButton btnHecho;
    private JButton btnAñadir;
    private JPanel panelPrincipal;
    private TarifasAbstractModel tarifasAbstractModel;
    public DGestionTarifa() {
        iniciarComponentes();
        btnAñadir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        btnAñadir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarNueva();
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
        setTitle("Tarifas");
        setContentPane(panelPrincipal);
        cargarTabla();
        pack();
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(null);
        cargarConfiguracion();
    }

    private void cargarNueva(){
        DAñadirTarifa añadirTarifa=new DAñadirTarifa();
        añadirTarifa.setVisible(true);
        Utilidades.actualizarTabla(tablaTarifas);
    }

    private void cargarTabla(){
        tarifasAbstractModel =new TarifasAbstractModel(VPrincipal.tarifas);
        tablaTarifas.setModel(tarifasAbstractModel);
        tablaTarifas.getColumnModel().getColumn(tarifasAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditroTarifas(tablaTarifas,"editar"));
        tablaTarifas.getColumnModel().getColumn(tarifasAbstractModel.getColumnCount()-2).setCellEditor(new JButtonEditroTarifas(tablaTarifas,"defecto"));
        TableCellRenderer renderer1 = tablaTarifas.getDefaultRenderer(JButton.class);
        tablaTarifas.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.cellsRendered(tablaTarifas,VPrincipal.tarifas);
        Utilidades.headerNegrita(tablaTarifas);
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
