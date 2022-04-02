package com.devel.utilities.JButoonEditors;

import com.devel.models.Relacion;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.FamiliaresAbstractModel;
import com.devel.views.dialogs.DAñadirFamiliar;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class JButtonEditorFamiliares extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private List<Relacion> relaciones;
    private JTable table;
    private String opcion;

    public JButtonEditorFamiliares(List<Relacion> relaciones,JTable table,String opcion) {
        this.relaciones=relaciones;
        this.table=table;
        this.opcion=opcion;
        switch (opcion){
            case "apoderado":
                button = new JButtonAction("x16/default.png");
                break;
            case "editar":
                button = new JButtonAction("x16/editar.png");
                break;
        }
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Relacion relacion=((FamiliaresAbstractModel) table.getModel()).traer(table.getSelectedRow());
        switch (opcion){
            case "editar":
                DAñadirFamiliar dAñadirFamiliar=new DAñadirFamiliar(relacion.getPersona(),relacion.getPersona1());
                dAñadirFamiliar.pack();
                dAñadirFamiliar.setVisible(true);
                break;
            case "apoderado":
                for (Relacion relacion1:relaciones){
                    if(relacion1.isApoderado()){
                        relacion1.setApoderado(false);
                        if(relacion1.getId()!=null){
                            relacion1.guardar();
                        }
                    }
                }
                relacion.setApoderado(true);
                if(relacion.getId()!=null){
                    relacion.guardar();
                }
                break;
        }
        Utilidades.actualizarTabla(table);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
