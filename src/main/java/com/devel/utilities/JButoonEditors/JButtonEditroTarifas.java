package com.devel.utilities.JButoonEditors;

import com.devel.models.Nivel;
import com.devel.models.Tarifa;
import com.devel.utilities.modelosTablas.NivelesAbstractModel;
import com.devel.utilities.modelosTablas.TarifasAbstractModel;
import com.devel.views.VPrincipal;
import com.devel.views.dialogs.DAñadirTarifa;
import com.devel.views.dialogs.DCrearNivel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class JButtonEditroTarifas extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private JTable table;
    private String opcion;
    public JButtonEditroTarifas(JTable table,String opcion) {
        this.table = table;
        this.opcion=opcion;
        switch (opcion){
            case "defecto":
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
        Tarifa tarifa=((TarifasAbstractModel) table.getModel()).traer(table.convertRowIndexToModel(table.getSelectedRow()));
        switch (opcion){
            case "defecto":
                if(!tarifa.isDefecto()){
                    for (Tarifa tarifa1: VPrincipal.tarifas){
                        if(tarifa1.isDefecto()){
                            tarifa1.setDefecto(false);
                            tarifa1.guardar();
                        }
                    }
                    tarifa.setDefecto(true);
                    tarifa.guardar();
                    table.setVisible(false);
                    table.setVisible(true);
                }
                break;
            case "editar":
                DAñadirTarifa dAñadirTarifa=new DAñadirTarifa(tarifa);
                dAñadirTarifa.setVisible(true);
                table.setVisible(false);
                table.setVisible(true);
                break;
        }
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}