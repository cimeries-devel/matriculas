package com.devel.utilities.JButoonEditors;

import com.devel.models.Salon;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.SalonesAbstractModel;
import com.devel.views.dialogs.DAñadirSalon;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonEditorSalones extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private JTable table;

    public JButtonEditorSalones(JTable table) {
        this.table=table;
        button = new JButtonAction("x16/editar.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Salon salon=((SalonesAbstractModel) table.getModel()).traer(table.convertRowIndexToModel(table.getSelectedRow()));
        DAñadirSalon dAñadirSalon=new DAñadirSalon(salon);
        dAñadirSalon.setVisible(true);
        Utilidades.actualizarTabla(table);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}

