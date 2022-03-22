package com.devel.utilities.JButoonEditors;

import com.devel.models.Documento;
import com.devel.models.Persona;
import com.devel.models.Relacion;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.DocumentoAbstractModel;
import com.devel.utilities.modelosTablas.FamiliaresAbstractModel;
import com.devel.views.dialogs.DAñadirDocumento;
import com.devel.views.dialogs.DAñadirFamiliar;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class JButtonEditorDocumentos extends AbstractCellEditor implements TableCellEditor, ActionListener {
    JButtonAction button;
    private JTable table;
    private String opcion;
    private Persona persona;
    public JButtonEditorDocumentos(Persona persona,JTable table, String opcion) {
        this.persona=persona;
        this.table=table;
        this.opcion=opcion;
        switch (opcion){
            case "editar":
                button = new JButtonAction("x16/editar.png");
                break;
            case "eliminar":
                button = new JButtonAction("x16/cancelar.png");
                break;
        }
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        button.setActionCommand("edit");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Documento documento=((DocumentoAbstractModel) table.getModel()).traer(table.getSelectedRow());
        switch (opcion){
            case "editar":
                DAñadirDocumento dAñadirDocumento=new DAñadirDocumento(documento);
                dAñadirDocumento.setVisible(true);
                break;
            case "eliminar":
                if(documento.getPerson().getDocumentos().size()>1){
                    int sioNo=JOptionPane.showOptionDialog(null, "¿Está seguro?","Eliminar documento",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,  null,new Object[] { "Si", "No"},"Si");
                    if(sioNo==0){
                        persona.getDocumentos().remove(documento);
                        Utilidades.sendNotification("Éxito","Documento cancelado", TrayIcon.MessageType.INFO);
                    }
                }else{
                    Utilidades.sendNotification("Advertencia","Debe tener al menos un documento", TrayIcon.MessageType.WARNING);
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