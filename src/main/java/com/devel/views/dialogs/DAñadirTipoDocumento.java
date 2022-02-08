package com.devel.views.dialogs;

import com.devel.models.Tarifa;
import com.devel.models.TipoDocumento;
import com.devel.utilities.Utilities;
import com.devel.views.VPrincipal;
import com.sun.istack.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class DAñadirTipoDocumento extends JDialog{
    private JTextField txtDocumento;
    private JButton registrarButton;
    private JButton hechoButton;
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private TipoDocumento tipoDocumento;

    public DAñadirTipoDocumento(@Nullable TipoDocumento tipoDocumento1) {
        if (tipoDocumento1 == null) {
            tipoDocumento = new TipoDocumento();
            setTitle("Crear Tipo de documento");
        } else {
            tipoDocumento = tipoDocumento1;
            setTitle("Editar Tipo de documento");
            registrarButton.setText("Guardar");
            cargarTipoDeDocumento();
        }
        iniciarComponentes();
        hechoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
        registrarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                registrar(tipoDocumento1);
            }
        });
    }
    private boolean registrarTarifa(){
        if(txtDocumento.getText().length()>0&&txtCodigo.getText().length()>0){
            tipoDocumento.setCodigo(txtCodigo.getText().trim());
            tipoDocumento.setDescripcion(txtDocumento.getText().trim());
            tipoDocumento.guardar();
            return true;
        }else{
            return false;
        }
    }
    private void registrar(TipoDocumento tipoDocumento1){
        if(registrarTarifa()){
            if(tipoDocumento1==null){
                VPrincipal.tipoDocumentos.add(tipoDocumento);
                tipoDocumento=null;
                tipoDocumento=new TipoDocumento();
                Utilities.sendNotification("Éxito","Tipo de documento registrada", TrayIcon.MessageType.INFO);
                txtDocumento.setText(null);
                txtCodigo.setText(null);
            }else {
                Utilities.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            }
        }else{
            Utilities.sendNotification("Error","Rellene todos los campos", TrayIcon.MessageType.ERROR);
        }
    }
    private void iniciarComponentes(){
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setModal(true);
        setResizable(false);
    }
    private void cargarTipoDeDocumento(){
        txtDocumento.setText(tipoDocumento.getDescripcion());
        txtCodigo.setText(tipoDocumento.getCodigo());
    }
}
