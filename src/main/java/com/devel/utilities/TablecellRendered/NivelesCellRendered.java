package com.devel.utilities.TablecellRendered;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import static com.devel.utilities.UtilidadesTabla.seleccionada;

public class NivelesCellRendered extends DefaultTableCellRenderer {

    public static void setCellRenderer( JTable table){
        NivelesCellRendered cellRendered=new NivelesCellRendered();
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(cellRendered);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getColumnClass(column).equals(JButton.class)){
            table.getColumn(table.getColumnName(column)).setMaxWidth(30);
            table.getColumn(table.getColumnName(column)).setMinWidth(30);
            return seleccionada(isSelected,"editar",table);
        }else{
            switch (table.getColumnName(column)){
                case "Hora inicio":
                case "Hora fin":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(110);
                    table.getColumn(table.getColumnName(column)).setMinWidth(110);
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
                default:
                    setHorizontalAlignment(SwingConstants.LEFT);
                    break;
            }
            return this;
        }
    }
}