package com.devel.utilities.TablecellRendered;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;

import static com.devel.utilities.UtilidadesTabla.buscarTexto;

public class MatriculasCellRendered extends DefaultTableCellRenderer {
    private Map<Integer, String> listaFiltros;

    public MatriculasCellRendered(Map<Integer, String> listaFiltros) {
        this.listaFiltros=listaFiltros;
    }

    public static void setCellRenderer(Map<Integer, String> listaFiltros, JTable table){
        MatriculasCellRendered cellRendered=new MatriculasCellRendered(listaFiltros);
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(cellRendered);
        }
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        JTextField componente=buscarTexto(listaFiltros,value,column,this);
        switch (table.getColumnName(column)){
            case "Código de alumno":
                table.getColumn(table.getColumnName(column)).setMaxWidth(130);
                table.getColumn(table.getColumnName(column)).setMinWidth(130);
                componente.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case "Tipo":
                table.getColumn(table.getColumnName(column)).setMaxWidth(130);
                table.getColumn(table.getColumnName(column)).setMinWidth(130);
                componente.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            case "Año":
            case "Monto":
            case "Nivel":
            case "Grado":
            case "Sección":
            case "Dni":
            case "Género":
                table.getColumn(table.getColumnName(column)).setMaxWidth(90);
                table.getColumn(table.getColumnName(column)).setMinWidth(90);
                componente.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case "Relación apoderado":
                table.getColumn(table.getColumnName(column)).setMaxWidth(120);
                table.getColumn(table.getColumnName(column)).setMinWidth(120);
                componente.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            default:
                componente.setHorizontalAlignment(SwingConstants.LEFT);
                break;
        }
        return componente;
    }
}
