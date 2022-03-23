package com.devel.utilities.TablecellRendered;

import com.devel.models.Relacion;
import com.devel.models.Tarifa;
import com.devel.utilities.JButoonEditors.JButtonAction;
import com.devel.utilities.modelosTablas.AlumnosAbstractModel;
import com.formdev.flatlaf.ui.FlatTableCellBorder;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class TablesCellRendered extends DefaultTableCellRenderer {
    private Vector<Tarifa> tarifas;
    private List<Relacion> familiares;
    private JTable table;
    private Map<Integer, String> listaFiltros;

    public TablesCellRendered(Map<Integer, String> listaFiltros,Vector<Tarifa> tarifas){
        this.listaFiltros=listaFiltros;
        this.tarifas=tarifas;
    }
    public TablesCellRendered(Map<Integer, String> listaFiltros,List<Relacion> familiares, boolean a){
        this.listaFiltros=listaFiltros;
        this.familiares=familiares;
    }
    public TablesCellRendered(List<Relacion> familiares, boolean a){
        this.familiares=familiares;
    }
    public TablesCellRendered(Map<Integer, String> listaFiltros){
        this.listaFiltros=listaFiltros;
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
                case "Más":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(40);
                    table.getColumn(table.getColumnName(column)).setMinWidth(40);
                    return seleccionada(isSelected,"mostrar");
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
                case "Tipo":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(130);
                    table.getColumn(table.getColumnName(column)).setMinWidth(130);
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
                case "Monto":
                case "Número":
                case "Edad":
                case "Nivel":
                case "Grado":
                case "Seguro":
                case "Sección":
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
        return buscarTexto(value,column);
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
            case "mostrar":
                if(isSelected){
                    return new JButtonAction("x16/mostrarContraseña.png",table.getSelectionBackground());
                }else{
                    return new JButtonAction("x16/mostrarContraseña.png",table.getBackground());
                }
            default:
                if(isSelected){
                    return new JButtonAction("x16/editar.png",table.getSelectionBackground());
                }else{
                    return new JButtonAction("x16/editar.png",table.getBackground());
                }

        }
    }

    public Component buscarTexto(Object value,int column) {
        if(listaFiltros!=null){
            JTextField componente=new JTextField();
            componente.setBorder(this.getBorder());
            componente.setBackground(this.getBackground());
            componente.setForeground(this.getForeground());
            componente.setText(String.valueOf(value));
            Highlighter hilit = new DefaultHighlighter();
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(new Color(0xEE1633));
            componente.setHighlighter(hilit);
            if(!listaFiltros.isEmpty()){
                if(listaFiltros.get(column)!=null){
                    String s = listaFiltros.get(column);
                    if (s.length() > 0) {
                        String contenido = componente.getText();
                        int index = contenido.indexOf(s, 0);
                        if (index >= 0) {
                            try {
                                int end = index + s.length();
                                hilit.addHighlight(index, end, painter);
                                componente.setCaretPosition(end);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            return componente;
        }
        return this;
    }

}