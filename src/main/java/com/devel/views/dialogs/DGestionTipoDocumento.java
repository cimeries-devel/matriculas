package com.devel.views.dialogs;

import com.devel.utilities.Colors;
import com.devel.utilities.JButoonEditors.JButtonEditorTipoDocumento;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.TipoDocumentoAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;

public class DGestionTipoDocumento extends JDialog{
    private JTable tablaTipoDocumentos;
    private JButton btnHecho;
    private JButton btnAñadir;
    private JPanel panelPrincipal;
    private TipoDocumentoAbstractModel tipoDocumentoAbstractModel;
    public DGestionTipoDocumento() {
        iniciarComponentes();
        btnAñadir.addActionListener(e -> {
            cargarNuevoTipoDeDocumento();
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
        setTitle("Tipo de documentos");
        setContentPane(panelPrincipal);
        cargarTabla();
        pack();
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnAñadir);
    }
    private void cargarTabla(){
        tipoDocumentoAbstractModel =new TipoDocumentoAbstractModel(VPrincipal.tipoDocumentos);
        tablaTipoDocumentos.setModel(tipoDocumentoAbstractModel);
        tablaTipoDocumentos.getColumnModel().getColumn(tipoDocumentoAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorTipoDocumento(tablaTipoDocumentos));
        TableCellRenderer renderer1 = tablaTipoDocumentos.getDefaultRenderer(JButton.class);
        tablaTipoDocumentos.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.headerNegrita(tablaTipoDocumentos);
        Utilidades.cellsRendered(tablaTipoDocumentos);
    }
    private void cargarNuevoTipoDeDocumento(){
        DAñadirTipoDocumento documento=new DAñadirTipoDocumento();
        documento.setVisible(true);
        Utilidades.actualizarTabla(tablaTipoDocumentos);
    }
    private void cerrar(){
        dispose();
    }

}
