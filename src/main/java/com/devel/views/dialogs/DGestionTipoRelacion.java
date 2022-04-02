package com.devel.views.dialogs;

import com.devel.utilities.JButoonEditors.JButtonEditorSecciones;
import com.devel.utilities.JButoonEditors.JButtonTipoRelaciones;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.SeccionAbstractModel;
import com.devel.utilities.modelosTablas.TipoRelacionAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class DGestionTipoRelacion extends JDialog{
    private JTable tablaTipoRelaciones;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private TipoRelacionAbstractModel tipoRelacionAbstractModel;

    public DGestionTipoRelacion() {
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
        setTitle("Tipo Relaciones");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        cargarTabla();
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void cargarNuevaSeccion(){
        DAñadirTipoRelacion dAñadirTipoRelacion=new DAñadirTipoRelacion();
        dAñadirTipoRelacion.setVisible(true);
        Utilidades.actualizarTabla(tablaTipoRelaciones);
    }

    private void cargarTabla(){
        tipoRelacionAbstractModel=new TipoRelacionAbstractModel(VPrincipal.tipoRelaciones);
        tablaTipoRelaciones.setModel(tipoRelacionAbstractModel);
        tablaTipoRelaciones.getColumnModel().getColumn(tipoRelacionAbstractModel.getColumnCount()-1).setCellEditor(new JButtonTipoRelaciones(tablaTipoRelaciones));
        TableCellRenderer renderer1 = tablaTipoRelaciones.getDefaultRenderer(JButton.class);
        tablaTipoRelaciones.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.definirTamaño(tablaTipoRelaciones.getColumn("Id"),35);
        Utilidades.headerNegrita(tablaTipoRelaciones);
        Utilidades.cellsRendered(tablaTipoRelaciones);
    }
    private void cerrar(){
        dispose();
    }

}
