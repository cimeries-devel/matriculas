package com.devel.utilities.TablecellRendered;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;

import static com.devel.utilities.UtilidadesTabla.buscarTexto;
import static com.devel.utilities.UtilidadesTabla.seleccionada;

public class AlumnosCellRendered extends DefaultTableCellRenderer {
    private Map<Integer, String> listaFiltros;

    public AlumnosCellRendered(Map<Integer, String> listaFiltros) {
        this.listaFiltros=listaFiltros;

    }

    public static void setCellRenderer(Map<Integer, String> listaFiltros, JTable table){
        AlumnosCellRendered cellRendered=new AlumnosCellRendered(listaFiltros);
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(cellRendered);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getColumnClass(column).equals(JButton.class)){
            table.getColumn(table.getColumnName(column)).setMaxWidth(40);
            table.getColumn(table.getColumnName(column)).setMinWidth(40);
            return seleccionada(isSelected,"mostrar",table);
        }else{
            JTextField componente=buscarTexto(listaFiltros,value,column,this);
            switch (table.getColumnName(column)){
                case "Última matrícula":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(110);
                    table.getColumn(table.getColumnName(column)).setMinWidth(110);
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    break;
                case "Código":
                case "Edad":
                case "Seguro":
                case "Nivel":
                case "Grado":
                case "Sección":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(90);
                    table.getColumn(table.getColumnName(column)).setMinWidth(90);
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    break;
                default:
                    componente.setHorizontalAlignment(SwingConstants.LEFT);
                    break;
            }
            return componente;
        }
    }
}

