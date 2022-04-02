package com.devel.utilities.TablecellRendered;

import com.devel.utilities.modelosTablas.TodosLosFamiliaresAbstractModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;

import static com.devel.utilities.UtilidadesTabla.buscarTexto;
import static com.devel.utilities.UtilidadesTabla.seleccionada;

public class TodosLosFamiliaresCellRendered extends DefaultTableCellRenderer {
    private Map<Integer, String> listaFiltros;

    public TodosLosFamiliaresCellRendered(Map<Integer, String> listaFiltros){
        this.listaFiltros=listaFiltros;

    }
    public static void setCellRenderer(Map<Integer, String> listaFiltros, JTable table){
        TodosLosFamiliaresCellRendered tablesCellRendered=new TodosLosFamiliaresCellRendered(listaFiltros);
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(tablesCellRendered);
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
                case "Documento":
                case "Celular":
                case "Alumnos":
                case "Apoderado":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(90);
                    table.getColumn(table.getColumnName(column)).setMinWidth(90);
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    break;
                case "ID":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(50);
                    table.getColumn(table.getColumnName(column)).setMinWidth(50);
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
