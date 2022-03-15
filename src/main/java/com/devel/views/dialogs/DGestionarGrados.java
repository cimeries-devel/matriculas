package com.devel.views.dialogs;

import com.devel.models.Grado;
import com.devel.utilities.JButoonEditors.JButtonEditorGrados;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.GradoAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class DGestionarGrados extends JDialog{
    private JTable tablaGrados;
    private JButton nuevoButton;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private Grado grado;
    private GradoAbstractModel gradoAbstractModel;
    public DGestionarGrados() {
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
                cargarNuevoGrado();
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
    }
    private void iniciarComponentes(){
        setTitle("Grados");
        setContentPane(panelPrincipal);
        cargarTabla();
        pack();
        setLocationRelativeTo(null);
        setModal(true);
        setResizable(false);

    }
    private void cargarNuevoGrado(){
        DCrearGrado dCrearGrado=new DCrearGrado();
        dCrearGrado.setVisible(true);
        tablaGrados.updateUI();
        Utilidades.headerNegrita(tablaGrados);
        Utilidades.cellsRendered(tablaGrados);
    }
    private void cargarTabla(){
        gradoAbstractModel=new GradoAbstractModel((Vector<Grado>) VPrincipal.grados);
        tablaGrados.setModel(gradoAbstractModel);
        tablaGrados.getColumnModel().getColumn(gradoAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorGrados(tablaGrados));
        TableCellRenderer renderer1 = tablaGrados.getDefaultRenderer(JButton.class);
        tablaGrados.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.definirTamaño(tablaGrados.getColumn(""),30);
        Utilidades.definirTamaño(tablaGrados.getColumn("Id"),40);
        Utilidades.headerNegrita(tablaGrados);
        Utilidades.cellsRendered(tablaGrados);
    }
}
