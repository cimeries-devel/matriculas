package com.devel.utilities.TablecellRendered;

import com.devel.models.Persona;
import com.devel.models.Relacion;
import com.devel.models.Tarifa;
import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class TablesCellRendered extends DefaultTableCellRenderer {
    private Vector<Tarifa> tarifas;
    private List<Relacion> familiares;
    private JTable table;
    public TablesCellRendered(Vector<Tarifa> tarifas){
        this.tarifas=tarifas;
    }
    public TablesCellRendered(List<Relacion> familiares, boolean a){
        this.familiares=familiares;
    }
    public TablesCellRendered(){

    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.table=table;
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getColumnClass(column).equals(JButton.class)){
            switch (table.getColumnName(column)){
                case "Apoderado":
                case "Activa":
                    if(tarifas!=null){
                        table.getColumn(table.getColumnName(column)).setMaxWidth(50);
                        table.getColumn(table.getColumnName(column)).setMinWidth(50);
                        if(tarifas.get(table.convertRowIndexToModel(row)).isDefecto()){
                            return seleccionada(isSelected,"default");
                        }else{
                            return seleccionada(isSelected,"nodefault");
                        }
                    }else{
                        table.getColumn(table.getColumnName(column)).setMaxWidth(100);
                        table.getColumn(table.getColumnName(column)).setMinWidth(100);
                        if(familiares.get(table.convertRowIndexToModel(row)).isApoderado()){
                            return seleccionada(isSelected,"default");
                        }else{
                            return seleccionada(isSelected,"nodefault");
                        }
                    }
                case "Quitar":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(50);
                    table.getColumn(table.getColumnName(column)).setMinWidth(50);
                    return seleccionada(isSelected,"cancelar");
                case "Editar":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(50);
                    table.getColumn(table.getColumnName(column)).setMinWidth(50);
                    return seleccionada(isSelected,"editar");
                default:
                    table.getColumn(table.getColumnName(column)).setMaxWidth(40);
                    table.getColumn(table.getColumnName(column)).setMinWidth(40);
                    return seleccionada(isSelected,"editar");
            }
        }else{
            switch (table.getColumnName(column)){
                case "Descripción":
                case "Nombre":
                case "Documento":
                    setHorizontalAlignment(SwingConstants.LEFT);
                    break;
                case "Fecha creación":
                case "Fecha Matrícula":
                case "Última matrícula":
                case "Relación":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(110);
                    table.getColumn(table.getColumnName(column)).setMinWidth(110);
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
                case "Código":
                case "Tarifa":
                case "Número":
                case "Viven juntos":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(90);
                    table.getColumn(table.getColumnName(column)).setMinWidth(90);
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
                default:
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
            }
        }
        return this;
    }
    private Component seleccionada(boolean isSelected, String icono){
        switch (icono){
            case "default":
                if(isSelected){
                    return new JButtonAction("x16/default.png",table.getSelectionBackground());
                }else{
                    return new JButtonAction("x16/default.png",table.getBackground());
                }
            case "nodefault":
                if(isSelected){
                        return new JButtonAction("x16/Nodefault.png",table.getSelectionBackground());
                }else{
                    return new JButtonAction("x16/Nodefault.png",table.getBackground());
                }
            case "cancelar":
                if(isSelected){
                    return new JButtonAction("x16/cancelar.png",table.getSelectionBackground());
                }else{
                    return new JButtonAction("x16/cancelar.png",table.getBackground());
                }
            default:
                if(isSelected){
                    return new JButtonAction("x16/editar.png",table.getSelectionBackground());
                }else{
                    return new JButtonAction("x16/editar.png",table.getBackground());
                }

        }

    }
}
