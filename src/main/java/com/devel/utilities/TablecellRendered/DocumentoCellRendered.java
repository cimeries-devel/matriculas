package com.devel.utilities.TablecellRendered;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import static com.devel.utilities.UtilidadesTabla.seleccionada;

public class DocumentoCellRendered extends DefaultTableCellRenderer {

    public static void setCellRenderer( JTable table){
        DocumentoCellRendered cellRendered=new DocumentoCellRendered();
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(cellRendered);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getColumnClass(column).equals(JButton.class)){
            if(table.getColumnName(column).equals("Editar")){
                table.getColumn(table.getColumnName(column)).setMaxWidth(50);
                table.getColumn(table.getColumnName(column)).setMinWidth(50);
                return seleccionada(isSelected,"editar",table);
            }else{
                table.getColumn(table.getColumnName(column)).setMaxWidth(50);
                table.getColumn(table.getColumnName(column)).setMinWidth(50);
                return seleccionada(isSelected,"cancelar",table);
            }
        }else{
            switch (table.getColumnName(column)){
                case "Número":
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