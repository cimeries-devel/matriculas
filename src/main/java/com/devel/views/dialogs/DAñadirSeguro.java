package com.devel.views.dialogs;

import com.devel.models.Seguro;
import com.devel.utilities.Utilities;
import com.devel.views.VPrincipal;
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

    public DAñadirSeguro() {
        iniciarComponentes();
        añadirButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                registrarSeguro();
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
    private void registrarSeguro(){
        if(txtCodigo.getText().length()>0&&txtDescripcion.getText().length()>0){
            Seguro seguro=new Seguro();
            seguro.setNombre(txtDescripcion.getText().trim());
            seguro.setCodigo(txtDescripcion.getText().trim());
            seguro.guardar();
            VPrincipal.seguros.add(seguro);
            Utilities.sendNotification("Éxito","Seguro registrado", TrayIcon.MessageType.INFO);
        }else{
            Utilities.sendNotification("Error","Rellene todos los campos", TrayIcon.MessageType.ERROR);
        }
    }
    private void iniciarComponentes(){
        setTitle("Registrar Seguro");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }
}
