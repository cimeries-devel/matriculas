package com.devel.utilities.JButoonEditors;

import com.devel.models.Relacion;
import com.devel.utilities.modelosTablas.FamiliaresAbstractModel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class JButtonEditorFamiliares extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private Vector<Relacion> relaciones;
    public JButtonEditorFamiliares(Vector<Relacion> relaciones) {
        this.relaciones=relaciones;
        button = new JButtonAction("x16/default.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        JTable table = (JTable)((JButton)getCellEditorValue()).getParent();
        Relacion relacion=((FamiliaresAbstractModel) table.getModel()).traer(table.getSelectedRow());
        if(!relacion.isApoderado()){
            for (Relacion relacion1:relaciones){
                relacion1.setApoderado(false);
            }
            relacion.setApoderado(true);
            table.setVisible(false);
            table.setVisible(true);
        }
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
