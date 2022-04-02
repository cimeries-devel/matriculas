package com.devel.utilities.modelosTablas;

import com.devel.models.Seguro;
import com.devel.models.TipoDocumento;
import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class TipoDocumentoAbstractModel extends AbstractTableModel {
    private String[] columnNames = {"Documento","Código",""};
    public Class[] m_colTypes = {String.class,String.class, JButton.class};
    private Vector<TipoDocumento> vector;

    public TipoDocumentoAbstractModel(Vector<TipoDocumento> vector){
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
        TipoDocumento tipoDocumento=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return tipoDocumento.getDescripcion();
            case 1:
                return tipoDocumento.getCodigo();
            default:
                return new JButtonAction("x16/editar.png");
        }
    }
    public TipoDocumento traer(int row){
        return vector.get(row);
    }
}
