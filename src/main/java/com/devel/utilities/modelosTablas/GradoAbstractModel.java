package com.devel.utilities.modelosTablas;

import com.devel.models.Grado;
import com.devel.models.Relacion;
import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class GradoAbstractModel extends AbstractTableModel {
    private String[] columnNames = {"Id","Grado","Editar"};
    public Class[] m_colTypes = {Integer.class,String.class, JButton.class};
    private Vector<Grado> vector;
    public GradoAbstractModel(Vector<Grado> vector){
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
        Grado grado=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return grado.getId();
            case 1:
                return grado.getGrado();
            default:
                return new JButtonAction("x16/editar.png");
        }
    }
    public Grado traer(int row){
        Grado ta = vector.get(row);
        return ta;
    }
}
