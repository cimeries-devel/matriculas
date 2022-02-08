package com.devel.views.dialogs;

import com.devel.models.Seguro;
import com.devel.utilities.Utilities;
import com.devel.views.VPrincipal;
import com.sun.istack.Nullable;
import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DAñadirSeguro extends JDialog{
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton añadirButton;
    private JButton hechoButton;
    private JTextField txtDescripcion;
    private Seguro seguro;
    public DAñadirSeguro(@Nullable Seguro seguro1) {
        if(seguro1==null){
            setTitle("Registrar Seguro");
            seguro=new Seguro();
        }else{
            seguro=seguro1;
            setTitle("Editar seguro");
            cargarSeguro();
            añadirButton.setText("Guardar");
        }
        iniciarComponentes();
        añadirButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                registrar(seguro1);
            }
        });
        hechoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }
    private boolean registrarSeguro(){
        if(txtCodigo.getText().length()>0&&txtDescripcion.getText().length()>0){
            seguro.setNombre(txtDescripcion.getText().trim());
            seguro.setCodigo(txtCodigo.getText().trim());
            seguro.guardar();
            return true;
        }else{
            return false;
        }
    }
    private void registrar(Seguro seguro1){
        if(registrarSeguro()){
            if(seguro1==null){
                VPrincipal.seguros.add(seguro);
                seguro=null;
                seguro=new Seguro();
                Utilities.sendNotification("Éxito","Seguro registrado", TrayIcon.MessageType.INFO);
                txtDescripcion.setText(null);
                txtCodigo.setText(null);
            }else {
                Utilities.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            }
        }else{
            Utilities.sendNotification("Error","Rellene todos los campos", TrayIcon.MessageType.ERROR);
        }
    }
    private void cargarSeguro(){
        txtDescripcion.setText(seguro.getNombre());
        txtCodigo.setText(seguro.getCodigo());
    }
    private void iniciarComponentes(){
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }
}
