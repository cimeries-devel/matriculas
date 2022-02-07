package com.devel.views.dialogs;

import com.devel.utilities.JButoonEditors.JButtonEditorCelulares;
import com.devel.utilities.JButoonEditors.JButtonEditorFamiliares;
import com.devel.utilities.JButoonEditors.JButtonEditroNiveles;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilities;
import com.devel.utilities.modelosTablas.NivelesAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DGestionNiveles extends JDialog{
    private JPanel panelPrincipal;
    private JButton nuevoButton;
    private JTable tablaNiveles;
    private JButton btnHecho;
    private NivelesAbstractModel nivelesAbstractModel;
    public DGestionNiveles() {
        iniciarComponentes();
        nuevoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarCrearNivel();
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
        setTitle("Gestión niveles");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setModal(true);
        cargarTabla();
    }private void cargarCrearNivel(){
        DCrearNivel dCrearNivel=new DCrearNivel(null);
        dCrearNivel.setVisible(true);
        tablaNiveles.updateUI();
        Utilities.headerNegrita(tablaNiveles);
    }
    private void cargarTabla(){
        nivelesAbstractModel=new NivelesAbstractModel(VPrincipal.niveles);
        tablaNiveles.setModel(nivelesAbstractModel);
        tablaNiveles.getColumnModel().getColumn(nivelesAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditroNiveles(tablaNiveles));
        TableCellRenderer renderer1 = tablaNiveles.getDefaultRenderer(JButton.class);
        tablaNiveles.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilities.definirTamaño(tablaNiveles.getColumn(""),35);
        Utilities.alinearCentro(tablaNiveles.getColumn("Descripción"));
        Utilities.alinearCentro(tablaNiveles.getColumn("Hora inicio"));
        Utilities.alinearCentro(tablaNiveles.getColumn("Hora fin"));
        Utilities.headerNegrita(tablaNiveles);
    }

}
