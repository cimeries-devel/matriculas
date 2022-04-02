package com.devel.utilities.TablecellRendered;

import com.devel.models.Relacion;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.Map;

import static com.devel.utilities.UtilidadesTabla.seleccionada;


public class FamiliaresCellRendered extends DefaultTableCellRenderer {
    private List<Relacion> familiares;

    public FamiliaresCellRendered( List<Relacion> familiares){
        this.familiares=familiares;
    }

    public static void setCellRenderer(JTable table, List<Relacion> vector){
        FamiliaresCellRendered cellRendered=new FamiliaresCellRendered(vector);
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(cellRendered);
        }
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getColumnClass(column).equals(JButton.class)){
            if(table.getColumnName(column).equals("Apoderado")){
                table.getColumn(table.getColumnName(column)).setMaxWidth(100);
                table.getColumn(table.getColumnName(column)).setMinWidth(100);
                if(familiares.get(table.convertRowIndexToModel(row)).isApoderado()){
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
                case "Relación":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(90);
                    table.getColumn(table.getColumnName(column)).setMinWidth(90);
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
                case "Viven juntos":
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
