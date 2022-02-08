package com.devel.utilities.JButoonEditors;

import com.devel.models.Seccion;
import com.devel.models.Seguro;
import com.devel.utilities.modelosTablas.SeccionAbstractModel;
import com.devel.utilities.modelosTablas.SegurosAbstractModel;
import com.devel.views.dialogs.DAñadirSecciones;
import com.devel.views.dialogs.DAñadirSeguro;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonEditorSecciones extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private JTable table;

    public JButtonEditorSecciones(JTable table) {
        this.table=table;
        button = new JButtonAction("x16/editar.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Seccion seccion=((SeccionAbstractModel) table.getModel()).traer(table.convertRowIndexToModel(table.getSelectedRow()));
        DAñadirSecciones dAñadirSecciones=new DAñadirSecciones(seccion);
        dAñadirSecciones.setVisible(true);
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
