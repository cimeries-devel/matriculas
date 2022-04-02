package com.devel.utilities;

import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.util.Map;

public class UtilidadesTabla {

    public static JTextField buscarTexto(Map<Integer, String> listaFiltros, Object value, int column, DefaultTableCellRenderer defaultTableCellRenderer) {
        JTextField componente=new JTextField();
        componente.setBorder(defaultTableCellRenderer.getBorder());
        componente.setBackground(defaultTableCellRenderer.getBackground());
        componente.setForeground(defaultTableCellRenderer.getForeground());
        componente.setText(String.valueOf(value));
        if(listaFiltros!=null){
            Highlighter hilit = new DefaultHighlighter();
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(componente.getSelectionColor());
            componente.setHighlighter(hilit);
            if(!listaFiltros.isEmpty()){
                if(listaFiltros.get(column)!=null){
                    String s = listaFiltros.get(column);
                    if (s.length() > 0) {
                        String contenido = componente.getText();
                        int index = contenido.indexOf(s);
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
        }
        return componente;
    }

    public static Component seleccionada(boolean isSelected, String icono,JTable table){
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
}
