package com.devel.utilities.JButoonEditors;

import com.devel.models.Celular;
import com.devel.models.Relacion;
import com.devel.utilities.modelosTablas.FamiliaresAbstractModel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class JButtonEditorCelulares extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private Vector<Celular> celulares;

    public JButtonEditorCelulares(Vector<Celular> celulares) {
        this.celulares=celulares;
        button = new JButtonAction("x16/editar.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
