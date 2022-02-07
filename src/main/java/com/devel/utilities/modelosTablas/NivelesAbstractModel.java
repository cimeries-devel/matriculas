package com.devel.utilities.modelosTablas;

import com.devel.models.Nivel;
import com.devel.models.Relacion;
import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class NivelesAbstractModel extends AbstractTableModel {
    private String[] columnNames = {"Descripción","Hora inicio","Hora fin",""};
    public Class[] m_colTypes = {String.class,String.class,String.class, JButton.class};
    private Vector<Nivel> vector;
    private DateFormat formatoHora=new SimpleDateFormat("HH:mm");

    public NivelesAbstractModel(Vector<Nivel> vector){
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
        Nivel nivel=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return nivel.getDescripcion();
            case 1:
                return formatoHora.format(nivel.getHoraInicio());
            case 2:
                return formatoHora.format(nivel.getHoraFin());
            default:
                return new JButtonAction("x16/default.png");
        }
    }
    public Nivel traer(int row){
        Nivel ta = vector.get(row);
        return ta;
    }
}
