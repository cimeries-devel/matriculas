package com.devel.views.dialogs;

import com.devel.utilities.JButoonEditors.JButtonEditorSecciones;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.SeccionAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DGestionSecciones extends JDialog{
    private JTable tablaSecciones;
    private JButton btnHecho;
    private JButton nuevoButton;
    private JPanel panelPrincipal;
    private SeccionAbstractModel seccionAbstractModel;
    public DGestionSecciones() {
        iniciarComponentes();
        nuevoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarNuevaSeccion();
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }
    private void iniciarComponentes(){
        setTitle("Secciones");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        cargarTabla();
    }

    private void cargarNuevaSeccion(){
        DAñadirSecciones dAñadirSecciones=new DAñadirSecciones();
        dAñadirSecciones.setVisible(true);
        tablaSecciones.updateUI();
        Utilidades.headerNegrita(tablaSecciones);
        Utilidades.cellsRendered(tablaSecciones);
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
}
