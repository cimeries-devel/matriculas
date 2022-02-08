package com.devel.utilities.modelosTablas;

import com.devel.models.Registro;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class MatriculasAbstractModel extends AbstractTableModel {
    private String[] columnNames = {"Código","Alumno","Apoderado","Nivel","Grado","Sección","Fecha Matrícula"};
    public Class[] m_colTypes = {String.class,String.class, String.class,String.class,String.class,String.class,String.class};
    private Vector<Registro> vector;
    private DateFormat formatoFecha=new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public MatriculasAbstractModel(Vector<Registro> vector){
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
        Registro registro=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return registro.getEstudiante().getCodigo();
            case 1:
                return registro.getEstudiante().getNombres()+" "+registro.getEstudiante().getNombres();
            case 2:
                return registro.getEstudiante().getApoderado().getNombres()+" "+registro.getEstudiante().getApoderado().getApellidos()+" - "+registro.getEstudiante().getRelacionDeApoderado();
            case 3:
                return registro.getSalon().getNivel().getDescripcion();
            case 4:
                return registro.getSalon().getGrado().getGrado();
            case 5:
                return registro.getSalon().getSeccion().getSeccion();
            default:
                return formatoFecha.format(registro.getActualizacion());
        }
    }

    public Registro traer(int row){
        return vector.get(row);
    }
}
