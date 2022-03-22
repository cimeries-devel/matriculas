package com.devel.utilities.JButoonEditors;

import com.devel.models.Persona;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.AlumnosAbstractModel;
import com.devel.views.dialogs.DAñadirEstudiante;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonActionAlumnos extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private JTable table;

    public JButtonActionAlumnos(JTable table) {
        this.table=table;
        button = new JButtonAction("x16/mostrarContraseña.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Persona persona=((AlumnosAbstractModel) table.getModel()).traer(table.convertRowIndexToModel(table.getSelectedRow()));
        DAñadirEstudiante dAñadirEstudiante =new DAñadirEstudiante(persona);
        dAñadirEstudiante.setVisible(true);
        Utilidades.actualizarTabla(table);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
