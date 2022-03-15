package com.devel.utilities.modelosTablas;

import com.devel.models.Celular;
import com.devel.models.Documento;
import com.devel.models.Persona;
import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;

public class DocumentoAbstractModel extends AbstractTableModel {
    private String[] columnNames = {"Tipo de documento","Número","Editar","Quitar"};
    public Class[] m_colTypes = {String.class,String.class, JButton.class, JButton.class};
    private List<Documento> vector;

    public DocumentoAbstractModel(List<Documento> vector){
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
        Documento documento=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return documento.getTypeDocument().getCodigo();
            case 1:
                return documento.getNumero();
            case 2:
                return new JButtonAction("x16/editar.png");
            default:
                return new JButtonAction("x16/cancelar.png");
        }
    }
    public Documento traer(int row){
        return vector.get(row);
    }
}
