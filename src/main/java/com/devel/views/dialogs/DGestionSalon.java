package com.devel.views.dialogs;

import com.devel.utilities.Colors;
import com.devel.utilities.JButoonEditors.JButtonEditorSalones;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.SalonesAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class DGestionSalon extends JDialog{
    private JTable tablaSalones;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private SalonesAbstractModel salonesAbstractModel;
    public DGestionSalon(){
        iniciarComponentes();
        btnAñadir.addActionListener(e -> {
            cargarNuevoSalon();
        });
        btnHecho.addActionListener(e -> {
            cerrar();
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void iniciarComponentes(){
        setTitle("Salones");
        setContentPane(panelPrincipal);
        cargarTabla();
        pack();
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(null);
        cargarConfiguracion();
    }
    private void cargarNuevoSalon(){
        DAñadirSalon dAñadirSalon=new DAñadirSalon();
        dAñadirSalon.setVisible(true);
        Utilidades.actualizarTabla(tablaSalones);
    }
    private void cargarTabla(){
        salonesAbstractModel =new SalonesAbstractModel(VPrincipal.salones);
        tablaSalones.setModel(salonesAbstractModel);
        tablaSalones.getColumnModel().getColumn(salonesAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorSalones(tablaSalones));
        TableCellRenderer renderer1 = tablaSalones.getDefaultRenderer(JButton.class);
        tablaSalones.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.cellsRendered(tablaSalones);
        Utilidades.headerNegrita(tablaSalones);
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
