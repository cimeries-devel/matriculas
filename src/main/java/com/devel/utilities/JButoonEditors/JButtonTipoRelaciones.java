package com.devel.utilities.JButoonEditors;

import com.devel.models.Seccion;
import com.devel.models.TipoRelacion;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.SeccionAbstractModel;
import com.devel.utilities.modelosTablas.TipoRelacionAbstractModel;
import com.devel.views.dialogs.DAñadirSecciones;
import com.devel.views.dialogs.DAñadirTipoRelacion;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonTipoRelaciones extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private JTable table;

    public JButtonTipoRelaciones(JTable table) {
        this.table=table;
        button = new JButtonAction("x16/editar.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        TipoRelacion tipoRelacion=((TipoRelacionAbstractModel) table.getModel()).traer(table.convertRowIndexToModel(table.getSelectedRow()));
        DAñadirTipoRelacion dAñadirSecciones=new DAñadirTipoRelacion(tipoRelacion);
        dAñadirSecciones.setVisible(true);
        Utilidades.actualizarTabla(table);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
