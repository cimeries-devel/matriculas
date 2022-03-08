package com.devel.views.dialogs;

import com.devel.models.Celular;
import com.devel.models.Seguro;
import com.devel.utilities.Utilities;
import com.devel.validators.CelularValidator;
import com.devel.validators.SeguroValidator;
import com.devel.views.VPrincipal;
import com.sun.istack.Nullable;
import jakarta.validation.ConstraintViolation;
import jdk.jshell.SourceCodeAnalysis;
import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

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
            hechoButton.setText("Cancelar");
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
    private void registrar(Seguro seguro1){
        String desripcion=txtDescripcion.getText().trim();
        String codigo=txtCodigo.getText().trim();
        seguro.setDescripcion(desripcion);
        seguro.setCodigo(codigo);

        SeguroValidator validator = new SeguroValidator();
        Set<ConstraintViolation<Seguro>> errors = validator.loadViolations(seguro);
        if(errors.isEmpty()){
            seguro.guardar();
            if(seguro1==null){
                VPrincipal.seguros.add(seguro);
                seguro=null;
                seguro=new Seguro();
                Utilities.sendNotification("Éxito","Seguro registrado", TrayIcon.MessageType.INFO);
                txtDescripcion.setText(null);
                txtCodigo.setText(null);
            }else {
                Utilities.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
                dispose();
            }
        }else {
            Object[] errores=errors.toArray();
            ConstraintViolation<Seguro> error1= (ConstraintViolation<Seguro>) errores[0];
            String error = "Verfique el campo: "+error1.getPropertyPath();
            Utilities.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
        }
    }

    private void cargarSeguro(){
        txtDescripcion.setText(seguro.getDescripcion());
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
