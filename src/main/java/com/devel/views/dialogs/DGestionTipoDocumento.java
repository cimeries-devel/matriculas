package com.devel.views.dialogs;

import com.devel.utilities.JButoonEditors.JButtonEditorTipoDocumento;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.TipoDocumentoAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DGestionTipoDocumento extends JDialog{
    private JTable tablaTipoDocumentos;
    private JButton hechoButton;
    private JButton nuevoButton;
    private JPanel panelPrincipal;
    private TipoDocumentoAbstractModel tipoDocumentoAbstractModel;
    public DGestionTipoDocumento() {
        iniciarComponentes();
        hechoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
        nuevoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarNuevoTipoDeDocumento();
            }
        });
    }

    private void iniciarComponentes(){
        setTitle("Tipo de documentos");
        setContentPane(panelPrincipal);
        cargarTabla();
        pack();
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(null);
    }
    private void cargarTabla(){
        tipoDocumentoAbstractModel =new TipoDocumentoAbstractModel(VPrincipal.tipoDocumentos);
        tablaTipoDocumentos.setModel(tipoDocumentoAbstractModel);
        tablaTipoDocumentos.getColumnModel().getColumn(tipoDocumentoAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorTipoDocumento(tablaTipoDocumentos));
        TableCellRenderer renderer1 = tablaTipoDocumentos.getDefaultRenderer(JButton.class);
        tablaTipoDocumentos.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.definirTamaño(tablaTipoDocumentos.getColumn("Código"),80);
        Utilidades.headerNegrita(tablaTipoDocumentos);
        Utilidades.cellsRendered(tablaTipoDocumentos);
    }
    private void cargarNuevoTipoDeDocumento(){
        DAñadirTipoDocumento documento=new DAñadirTipoDocumento();
        documento.setVisible(true);
        tablaTipoDocumentos.updateUI();
        Utilidades.headerNegrita(tablaTipoDocumentos);
        Utilidades.cellsRendered(tablaTipoDocumentos);
    }
}
