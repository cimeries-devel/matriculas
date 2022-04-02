package com.devel.utilities.modelosTablas;

import com.devel.models.Persona;
import com.devel.models.Seguro;
import com.devel.utilities.JButoonEditors.JButtonAction;
import jdk.jfr.Percentage;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class TodosLosFamiliaresAbstractModel extends AbstractTableModel {

    private String[] columnNames = {"Documento","Nombres y apellidos","Celular","Dirección","Alumnos","Apoderado",""};
    public Class[] m_colTypes = {String.class,String.class, String.class,String.class,Integer.class,String.class,JButton.class};
    private Vector<Persona> vector;

    public TodosLosFamiliaresAbstractModel(Vector<Persona> vector){
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
        Persona persona=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return persona.getDocumentos().get(0).getNumero();
            case 1:
                return persona.getNombres()+" "+persona.getApellidos();
            case 2:
                return persona.getCelulares().get(0).getNumero();
            case 3:
                return persona.getDireccion();
            case 4:
                return persona.getRelaciones().size();
            case 5:
                return persona.isApoderado();
            default:
                return new JButtonAction("x16/editar.png");
        }
    }
    public Persona traer(int row){
        return vector.get(row);
    }
}
