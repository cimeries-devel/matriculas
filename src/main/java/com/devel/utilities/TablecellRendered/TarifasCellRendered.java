package com.devel.utilities.TablecellRendered;

import com.devel.models.Tarifa;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;
import java.util.Vector;

import static com.devel.utilities.UtilidadesTabla.seleccionada;

public class TarifasCellRendered extends DefaultTableCellRenderer {
    private Vector<Tarifa> tarifas;

    public TarifasCellRendered( Vector<Tarifa> tarifas){
        this.tarifas=tarifas;
    }

    public static void setCellRendered( JTable table, Vector<Tarifa> vector){
        TarifasCellRendered tablesCellRendered=new TarifasCellRendered(vector);
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(tablesCellRendered);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getColumnClass(column).equals(JButton.class)){
            if(table.getColumnName(column).equals("Defecto")){
                table.getColumn(table.getColumnName(column)).setMaxWidth(100);
                table.getColumn(table.getColumnName(column)).setMinWidth(100);
                if(tarifas.get(table.convertRowIndexToModel(row)).isDefecto()){
                    return seleccionada(isSelected,"default",table);
                }
                return seleccionada(isSelected,"nodefault",table);
            }else{
                table.getColumn(table.getColumnName(column)).setMaxWidth(40);
                table.getColumn(table.getColumnName(column)).setMinWidth(40);
                return seleccionada(isSelected,"editar",table);
            }
        }else{
            switch (table.getColumnName(column)){
                case "Fecha creación":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(110);
                    table.getColumn(table.getColumnName(column)).setMinWidth(110);
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
                case "Tarifa":
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