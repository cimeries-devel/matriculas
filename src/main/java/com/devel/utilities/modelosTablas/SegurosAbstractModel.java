package com.devel.utilities.modelosTablas;

import com.devel.models.Celular;
import com.devel.models.Seguro;
import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class SegurosAbstractModel extends AbstractTableModel {
    private String[] columnNames = {"Nombre","Código","Editar"};
    public Class[] m_colTypes = {String.class,String.class, JButton.class};
    private Vector<Seguro> vector;
    public SegurosAbstractModel(Vector<Seguro> vector){
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
        Seguro seguro=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return seguro.getNombre();
            case 1:
                return seguro.getCodigo();
            default:
                return new JButtonAction("x16/editar.png");
        }
    }
    public Seguro traer(int row){
        Seguro ta = vector.get(row);
        return ta;
    }
}
