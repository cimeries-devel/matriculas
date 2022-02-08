package com.devel.utilities.modelosTablas;

import com.devel.models.Persona;
import com.devel.models.Relacion;
import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class FamiliaresAbstractModel extends AbstractTableModel {
    private String[] columnNames = {"Nombres y apellidos","Relación","Viven juntos","Dirección","Apoderado"};
    public Class[] m_colTypes = {String.class,String.class,String.class,String.class, JButton.class};
    private Vector<Relacion> vector;
    public FamiliaresAbstractModel(Vector<Relacion> vector){
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
        Relacion relacion=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return relacion.getPersona1().getNombres()+" "+relacion.getPersona1().getApellidos();
            case 1:
                return relacion.getTipoRelacion();
            case 2:
                return relacion.isVivenJuntos()?"si":"no";
            case 3:
                return relacion.getPersona1().getDireccion();
            default:
                if(relacion.isApoderado()){
                    return new JButtonAction("x16/default.png");
                }else {
                    return new JButtonAction("x16/Nodefault.png");
                }
        }
    }
    public Relacion traer(int row){
        return vector.get(row);
    }
}

