package com.devel.views.dialogs;

import com.devel.models.Grado;
import com.devel.utilities.JButoonEditors.JButtonEditorGrados;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilities;
import com.devel.utilities.modelosTablas.GradoAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        setTitle("Gestión Grados");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setModal(true);
        cargarTabla();
    }
    private void cargarNuevoGrado(){
        DCrearGrado dCrearGrado=new DCrearGrado(null);
        dCrearGrado.setVisible(true);
        tablaGrados.updateUI();
        Utilities.headerNegrita(tablaGrados);
    }
    private void cargarTabla(){
        gradoAbstractModel=new GradoAbstractModel(VPrincipal.grados);
        tablaGrados.setModel(gradoAbstractModel);
        tablaGrados.getColumnModel().getColumn(gradoAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorGrados(tablaGrados));
        TableCellRenderer renderer1 = tablaGrados.getDefaultRenderer(JButton.class);
        tablaGrados.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilities.definirTamaño(tablaGrados.getColumn("Editar"),35);
        Utilities.alinearCentro(tablaGrados.getColumn("Grado"));
        Utilities.alinearCentro(tablaGrados.getColumn("Id"));
        Utilities.definirTamaño(tablaGrados.getColumn("Id"),40);
        Utilities.headerNegrita(tablaGrados);
    }
}
