package com.devel.utilities.TablecellRendered;

import com.devel.models.Persona;
import com.devel.models.Relacion;
import com.devel.models.Tarifa;
import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Vector;

public class TablesCellRendered extends DefaultTableCellRenderer {
    private Vector<Tarifa> tarifas;
    private Vector<Relacion> familiares;
    public TablesCellRendered(Vector<Tarifa> tarifas){
        this.tarifas=tarifas;
    }
    public TablesCellRendered(Vector<Relacion> familiares,boolean a){
        this.familiares=familiares;
    }
    public TablesCellRendered(){

    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getColumnClass(column).equals(JButton.class)){
            table.getColumn(table.getColumnName(column)).setMaxWidth(40);
            table.getColumn(table.getColumnName(column)).setMaxWidth(40);
            switch (table.getColumnName(column)){
                case "Apoderado":
                case "Activa":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(80);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(80);
                    if(tarifas!=null){
                        if(tarifas.get(table.convertRowIndexToModel(row)).isDefecto()){
                            return seleccionada(isSelected,"default");
                        }else{
                            return seleccionada(isSelected,"nodefault");
                        }
                    }else{
                        if(familiares.get(table.convertRowIndexToModel(row)).isApoderado()){
                            return seleccionada(isSelected,"default");
                        }else{
                            return seleccionada(isSelected,"nodefault");
                        }
                    }

                case "Quitar":
                    return seleccionada(isSelected,"cancelar");
                default:
                    return seleccionada(isSelected,"editar");
            }
        }else{
            switch (table.getColumnName(column)){
                case "Descripción":
                case "Nombre":
                case "Documento":
                    setHorizontalAlignment(SwingConstants.LEFT);
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
                    return new JButtonAction("x16/default.png",new Color(38,117,191));
                }else{
                    return new JButtonAction("x16/default.png");
                }
            case "nodefault":
                if(isSelected){
                    return new JButtonAction("x16/Nodefault.png",new Color(38,117,191));
                }else{
                    return new JButtonAction("x16/Nodefault.png");
                }
            case "cancelar":
                if(isSelected){
                    return new JButtonAction("x16/cancelar.png",new Color(38,117,191));
                }else{
                    return new JButtonAction("x16/cancelar.png");
                }
            default:
                if(isSelected){
                    return new JButtonAction("x16/editar.png",new Color(38,117,191));
                }else{
                    return new JButtonAction("x16/editar.png");
                }

        }

    }
}
