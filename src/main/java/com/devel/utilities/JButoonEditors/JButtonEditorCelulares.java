package com.devel.utilities.JButoonEditors;

import com.devel.models.Celular;
import com.devel.models.Grado;
import com.devel.models.Relacion;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.CelularesAbstractModel;
import com.devel.utilities.modelosTablas.FamiliaresAbstractModel;
import com.devel.utilities.modelosTablas.GradoAbstractModel;
import com.devel.views.dialogs.DAñadirCelular;
import com.devel.views.dialogs.DCrearGrado;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class JButtonEditorCelulares extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private JTable table;

    public JButtonEditorCelulares(JTable table) {
        this.table=table;
        button = new JButtonAction("x16/editar.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Celular celular=((CelularesAbstractModel) table.getModel()).traer(table.convertRowIndexToModel(table.getSelectedRow()));
        DAñadirCelular dAñadirCelular=new DAñadirCelular(celular);
        dAñadirCelular.setVisible(true);
        Utilidades.actualizarTabla(table);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
