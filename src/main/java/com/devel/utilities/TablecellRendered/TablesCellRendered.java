package com.devel.utilities.TablecellRendered;

import com.devel.models.Tarifa;
import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Vector;

public class TablesCellRendered extends DefaultTableCellRenderer {
    private Vector<Tarifa> vector;

    public TablesCellRendered(Vector<Tarifa> vector){
        this.vector=vector;
    }
    public TablesCellRendered(){

    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        int columnCount=table.getColumnCount()-1;
        if(table.getColumnClass(column).equals(JButton.class)){
            if(columnCount==column){
                table.getColumn(table.getModel().getColumnName(column)).setMaxWidth(30);
                table.getColumn(table.getModel().getColumnName(column)).setMinWidth(30);
                return new JButtonAction("x16/editar.png");
            }else{
                table.getColumn(table.getModel().getColumnName(column)).setMaxWidth(80);
                table.getColumn(table.getModel().getColumnName(column)).setMinWidth(80);
                return vector.get(table.convertRowIndexToModel(row)).isDefecto()?new JButtonAction("x16/default.png"):new JButtonAction("x16/Nodefault.png");
            }
        }else{

            switch (table.getColumnName(column)){
                case "Descripción":
                case "Nombre":
                case "Documento":
                    setHorizontalAlignment(SwingConstants.LEFT);
                    break;
                default:
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
            }
        }
        return this;
    }

    private void verificarEstado(boolean isSelected){
            if(isSelected){

            }else{

            }
    }
}
