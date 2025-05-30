package com.devel.utilities.modelosTablas;

import com.devel.models.TipoDocumento;
import com.devel.models.TipoRelacion;
import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class TipoRelacionAbstractModel extends AbstractTableModel {
    private String[] columnNames = {"ID","Tipo",""};
    public Class[] m_colTypes = {String.class,String.class, JButton.class};
    private Vector<TipoRelacion> vector;

    public TipoRelacionAbstractModel(Vector<TipoRelacion> vector){
        this.vector=vector;
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public int getRowCount() {
        return vector.size();
    }
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    @Override
    public Class getColumnClass(int col) {
        return m_colTypes[col];
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (m_colTypes[columnIndex].equals(JButton.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TipoRelacion tipoRelacion=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return tipoRelacion.getId();
            case 1:
                return tipoRelacion.getTipo();
            default:
                return new JButtonAction("x16/editar.png");
        }
    }
    public TipoRelacion traer(int row){
        return vector.get(row);
    }
}
