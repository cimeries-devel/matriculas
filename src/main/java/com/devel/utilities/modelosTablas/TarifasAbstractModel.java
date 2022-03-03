package com.devel.utilities.modelosTablas;

import com.devel.models.Seguro;
import com.devel.models.Tarifa;
import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class TarifasAbstractModel extends AbstractTableModel {
    private String[] columnNames = {"Fecha creación","Descripción","Tarifa","Activa",""};
    public Class[] m_colTypes = {String.class,String.class,String.class, JButton.class, JButton.class};
    private Vector<Tarifa> vector;
    private DateFormat formatoFecha=new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private NumberFormat sol = NumberFormat.getCurrencyInstance();

    public TarifasAbstractModel(Vector<Tarifa> vector){
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
        Tarifa tarifa=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return formatoFecha.format(tarifa.getCreacion());
            case 1:
                return tarifa.getDescripcion();
            case 2:
                return sol.format(tarifa.getPrecio());
            case 3:
                return tarifa.isDefecto()?new JButtonAction("x16/default.png"):new JButtonAction("x16/Nodefault.png");
            default:
                return new JButtonAction("x16/editar.png");
        }
    }
    public Tarifa traer(int row){
        return vector.get(row);
    }
}
