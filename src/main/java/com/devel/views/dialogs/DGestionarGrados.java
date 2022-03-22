package com.devel.views.dialogs;

import com.devel.models.Grado;
import com.devel.utilities.Colors;
import com.devel.utilities.JButoonEditors.JButtonEditorGrados;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.GradoAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class DGestionarGrados extends JDialog{
    private JTable tablaGrados;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private Grado grado;
    private GradoAbstractModel gradoAbstractModel;
    public DGestionarGrados() {
        iniciarComponentes();
        btnAñadir.addActionListener(e -> {
            cargarNuevoGrado();
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
        setTitle("Grados");
        setContentPane(panelPrincipal);
        cargarTabla();
        pack();
        setLocationRelativeTo(null);
        setModal(true);
        setResizable(false);
        cargarConfiguracion();
    }
    private void cargarNuevoGrado(){
        DCrearGrado dCrearGrado=new DCrearGrado();
        dCrearGrado.setVisible(true);
        Utilidades.actualizarTabla(tablaGrados);
    }
    private void cargarTabla(){
        gradoAbstractModel=new GradoAbstractModel(VPrincipal.grados);
        tablaGrados.setModel(gradoAbstractModel);
        tablaGrados.getColumnModel().getColumn(gradoAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorGrados(tablaGrados));
        TableCellRenderer renderer1 = tablaGrados.getDefaultRenderer(JButton.class);
        tablaGrados.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.definirTamaño(tablaGrados.getColumn(""),30);
        Utilidades.definirTamaño(tablaGrados.getColumn("Id"),40);
        Utilidades.headerNegrita(tablaGrados);
        Utilidades.cellsRendered(tablaGrados);
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
