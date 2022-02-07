package com.devel.utilities.JButoonEditors;

import com.devel.models.Nivel;
import com.devel.models.Seguro;
import com.devel.utilities.modelosTablas.NivelesAbstractModel;
import com.devel.utilities.modelosTablas.SegurosAbstractModel;
import com.devel.views.dialogs.DAñadirSeguro;
import com.devel.views.dialogs.DCrearNivel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonEditorSeguros extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private JTable table;
    public JButtonEditorSeguros(JTable table) {
        this.table=table;
        button = new JButtonAction("x16/editar.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Seguro seguro=((SegurosAbstractModel) table.getModel()).traer(table.convertRowIndexToModel(table.getSelectedRow()));
        DAñadirSeguro dCrearNivel=new DAñadirSeguro(seguro);
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
