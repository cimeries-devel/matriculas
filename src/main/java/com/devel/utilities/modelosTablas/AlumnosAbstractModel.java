package com.devel.utilities.modelosTablas;

import com.devel.models.Persona;
import com.devel.models.Registro;
import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class AlumnosAbstractModel extends AbstractTableModel {
    private String[] columnNames = {"Código","Nombres y apellidos","Apoderado","Edad","Nivel","Grado","Sección","Última matrícula","Más"};
    public Class[] m_colTypes = {String.class,String.class,String.class,Integer.class,String.class,String.class,String.class,String.class,JButton.class};
    private DateFormat año=new SimpleDateFormat("yyyy");
    private Vector<Persona> vector;
    public AlumnosAbstractModel(Vector<Persona> vector){
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
                return persona.getCodigo();
            case 1:
                return persona.getNombres()+" "+persona.getApellidos();
            case 2:
                return persona.getApoderado().getNombres()+" "+persona.getApoderado().getApellidos();
            case 3:
                return persona.getEdad();
            case 4:
                return persona.getRegistros().isEmpty()?"Sin registros":persona.ultimaMatricula().getSalon().getNivel().getDescripcion();
            case 5:
                return persona.getRegistros().isEmpty()?"Sin registros":persona.ultimaMatricula().getSalon().getGrado().getGrado();
            case 6:
                return persona.getRegistros().isEmpty()?"Sin registros":persona.ultimaMatricula().getSalon().getSeccion().getSeccion();
            case 7:
                return persona.getRegistros().isEmpty()?"Sin registros":año.format(persona.ultimaMatricula().getCreacion());
            default:
                return new JButtonAction("x16/mostrarContraseña.png");
        }
    }
    public Persona traer(int row){
        return vector.get(row);
    }
}
