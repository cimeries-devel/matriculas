package com.devel.views.dialogs;

import com.devel.utilities.Colors;
import com.devel.utilities.JButoonEditors.JButtonEditorSecciones;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.SeccionAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;

public class DGestionSecciones extends JDialog{
    private JTable tablaSecciones;
    private JButton btnHecho;
    private JButton btnAñadir;
    private JPanel panelPrincipal;
    private SeccionAbstractModel seccionAbstractModel;
    public DGestionSecciones() {
        iniciarComponentes();
        btnAñadir.addActionListener(e -> {
            cargarNuevaSeccion();
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
        setTitle("Secciones");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        cargarTabla();
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void cargarNuevaSeccion(){
        DAñadirSecciones dAñadirSecciones=new DAñadirSecciones();
        dAñadirSecciones.setVisible(true);
        Utilidades.actualizarTabla(tablaSecciones);
    }
    private void cargarTabla(){
        seccionAbstractModel=new SeccionAbstractModel(VPrincipal.secciones);
        tablaSecciones.setModel(seccionAbstractModel);
        tablaSecciones.getColumnModel().getColumn(seccionAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorSecciones(tablaSecciones));
        TableCellRenderer renderer1 = tablaSecciones.getDefaultRenderer(JButton.class);
        tablaSecciones.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.definirTamaño(tablaSecciones.getColumn("Id"),35);
        Utilidades.headerNegrita(tablaSecciones);
        Utilidades.cellsRendered(tablaSecciones);
    }
    private void cerrar(){
        dispose();
    }

}
