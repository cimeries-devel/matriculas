package com.devel.utilities.JButoonEditors;

import com.devel.models.Seguro;
import com.devel.models.TipoDocumento;
import com.devel.utilities.modelosTablas.SegurosAbstractModel;
import com.devel.utilities.modelosTablas.TipoDocumentoAbstractModel;
import com.devel.views.dialogs.DAñadirSeguro;
import com.devel.views.dialogs.DAñadirTipoDocumento;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonEditorTipoDocumento extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private JTable table;
    public JButtonEditorTipoDocumento(JTable table) {
        this.table=table;
        button = new JButtonAction("x16/editar.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        TipoDocumento tipoDocumento=((TipoDocumentoAbstractModel) table.getModel()).traer(table.convertRowIndexToModel(table.getSelectedRow()));
        DAñadirTipoDocumento dCrearNivel=new DAñadirTipoDocumento(tipoDocumento);
        dCrearNivel.setVisible(true);
        table.setVisible(false);
        table.setVisible(true);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
