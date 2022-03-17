package com.devel.views.dialogs;

import com.devel.models.Nivel;
import com.devel.utilities.JButoonEditors.JButtonEditroNiveles;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.NivelesAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.*;
import java.util.Vector;

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
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void iniciarComponentes(){
        setTitle("Niveles");
        setContentPane(panelPrincipal);
        cargarTabla();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);

    }private void cargarCrearNivel(){
        DCrearNivel dCrearNivel=new DCrearNivel();
        dCrearNivel.setVisible(true);
        Utilidades.actualizarTabla(tablaNiveles);
    }
    private void cargarTabla(){
        nivelesAbstractModel=new NivelesAbstractModel(VPrincipal.niveles);
        tablaNiveles.setModel(nivelesAbstractModel);
        tablaNiveles.getColumnModel().getColumn(nivelesAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditroNiveles(tablaNiveles));
        TableCellRenderer renderer1 = tablaNiveles.getDefaultRenderer(JButton.class);
        tablaNiveles.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.headerNegrita(tablaNiveles);
        Utilidades.cellsRendered(tablaNiveles);
    }
    private void cerrar(){
        dispose();
    }
}
