package com.devel.utilities.JButoonEditors;

import com.devel.models.Nivel;
import com.devel.models.Persona;
import com.devel.models.Seguro;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.NivelesAbstractModel;
import com.devel.utilities.modelosTablas.SeguroPersonaAbstractModel;
import com.devel.utilities.modelosTablas.SegurosAbstractModel;
import com.devel.views.dialogs.DAñadirSeguro;
import com.devel.views.dialogs.DCrearNivel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonEditorSeguros extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private JTable table;
    private Persona persona;
    public JButtonEditorSeguros(JTable table, Persona persona) {
        this.table=table;
        this.persona=persona;
        button = new JButtonAction("x16/cancelar.png");
        iniciarComponentes();
    }
    public JButtonEditorSeguros(JTable table) {
        this.table=table;
        button = new JButtonAction("x16/editar.png");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (persona == null) {
            Seguro seguro=((SegurosAbstractModel) table.getModel()).traer(table.convertRowIndexToModel(table.getSelectedRow()));
            DAñadirSeguro dCrearNivel=new DAñadirSeguro(seguro);
            dCrearNivel.setVisible(true);
        }else{
            Seguro seguro=((SeguroPersonaAbstractModel) table.getModel()).traer(table.convertRowIndexToModel(table.getSelectedRow()));
            int sioNo=JOptionPane.showOptionDialog(null, "¿Está seguro?","Quitar seguro",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,  null,new Object[] { "Si", "No"},"Si");
            if(sioNo==0){
                persona.getSeguros().remove(seguro);
                Utilidades.sendNotification("Éxito","Seguro quitado", TrayIcon.MessageType.INFO);
            }
        }
        table.setVisible(false);
        table.setVisible(true);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    public Object getCellEditorValue() {
        return button;
    }
}
