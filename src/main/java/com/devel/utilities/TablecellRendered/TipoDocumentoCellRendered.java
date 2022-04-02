package com.devel.utilities.TablecellRendered;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import static com.devel.utilities.UtilidadesTabla.seleccionada;

public class TipoDocumentoCellRendered extends DefaultTableCellRenderer {

    public static void setCellRenderer( JTable table){
        TipoDocumentoCellRendered cellRendered=new TipoDocumentoCellRendered();
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
                case "Código":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(90);
                    table.getColumn(table.getColumnName(column)).setMinWidth(90);
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