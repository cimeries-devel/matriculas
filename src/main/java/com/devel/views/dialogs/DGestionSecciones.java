package com.devel.views.dialogs;

import com.devel.utilities.JButoonEditors.JButtonEditorSecciones;
import com.devel.utilities.JButoonEditors.JButtonEditroNiveles;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilities;
import com.devel.utilities.modelosTablas.NivelesAbstractModel;
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
        setTitle("Gestión Seccion");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setModal(true);
        cargarTabla();
    }

    private void cargarNuevaSeccion(){
        DAñadirSecciones dAñadirSecciones=new DAñadirSecciones(null);
        dAñadirSecciones.setVisible(true);
        tablaSecciones.updateUI();
        Utilities.headerNegrita(tablaSecciones);
    }
    private void cargarTabla(){
        seccionAbstractModel=new SeccionAbstractModel(VPrincipal.secciones);
        tablaSecciones.setModel(seccionAbstractModel);
        tablaSecciones.getColumnModel().getColumn(seccionAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorSecciones(tablaSecciones));
        TableCellRenderer renderer1 = tablaSecciones.getDefaultRenderer(JButton.class);
        tablaSecciones.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilities.definirTamaño(tablaSecciones.getColumn("Editar"),35);
        Utilities.alinearCentro(tablaSecciones.getColumn("Sección"));
        Utilities.definirTamaño(tablaSecciones.getColumn("Id"),35);
        Utilities.alinearCentro(tablaSecciones.getColumn("Id"));
        Utilities.headerNegrita(tablaSecciones);
    }
}
