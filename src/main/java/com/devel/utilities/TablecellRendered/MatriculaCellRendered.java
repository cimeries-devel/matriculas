package com.devel.utilities.TablecellRendered;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class MatriculaCellRendered extends DefaultTableCellRenderer {

    public static void setCellRenderer( JTable table){
        MatriculaCellRendered cellRendered=new MatriculaCellRendered();
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(cellRendered);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        switch (table.getColumnName(column)){
            case "Fecha Matrícula":
                table.getColumn(table.getColumnName(column)).setMaxWidth(110);
                table.getColumn(table.getColumnName(column)).setMinWidth(110);
                setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case "Tipo":
                table.getColumn(table.getColumnName(column)).setMaxWidth(130);
                table.getColumn(table.getColumnName(column)).setMinWidth(130);
                setHorizontalAlignment(SwingConstants.LEFT);
                break;
            case "Monto":
            case "Código":
            case "Nivel":
            case "Grado":
            case "Sección":
                table.getColumn(table.getColumnName(column)).setMaxWidth(90);
                table.getColumn(table.getColumnName(column)).setMinWidth(90);
                setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case "Relación apoderado":
                table.getColumn(table.getColumnName(column)).setMaxWidth(120);
                table.getColumn(table.getColumnName(column)).setMinWidth(120);
                setHorizontalAlignment(SwingConstants.CENTER);
                break;
            default:
                setHorizontalAlignment(SwingConstants.LEFT);
                break;
        }
        return this;
    }
}
