package com.devel.views.dialogs;

import com.devel.models.Seccion;
import com.devel.models.Seguro;
import com.devel.utilities.Utilities;
import com.devel.validators.SeccionValidator;
import com.devel.validators.SeguroValidator;
import com.devel.views.VPrincipal;
import com.sun.istack.Nullable;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

public class DAñadirSecciones extends JDialog{
    private JTextField txtSeccion;
    private JButton btnRegistrar;
    private JPanel panelPrincipal;
    private JButton btnHecho;
    private Seccion seccion;

    public DAñadirSecciones(){
        this(null);
    }

    public DAñadirSecciones(Seccion seccion1) {
        crearoActualizar(seccion1);
        iniciarComponentes();

        btnRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                registrar(seccion1);
            }
        });

        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onCancel();
            }
        });
    }

    private void onCancel(){
        seccion.setSeccion(txtSeccion.getName());
        dispose();
    }

    private void registrar(Seccion seccion1){
        String desripcion=txtSeccion.getText().trim();
        seccion.setSeccion(desripcion);
        SeccionValidator validator = new SeccionValidator();
        Set<ConstraintViolation<Seccion>> errors = validator.loadViolations(seccion);
        if(errors.isEmpty()){
            seccion.guardar();
            if(seccion1==null){
                VPrincipal.secciones.add(seccion);
                seccion=new Seccion();
                Utilities.sendNotification("Éxito","Sección registrado", TrayIcon.MessageType.INFO);
                txtSeccion.setText(null);
            }else {
                Utilities.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
                dispose();
            }
        }else {
            Object[] errores=errors.toArray();
            ConstraintViolation<Seccion> error1= (ConstraintViolation<Seccion>) errores[0];
            String error = "Verfique el campo: "+error1.getPropertyPath();
            Utilities.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
        }
    }

    private void iniciarComponentes(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                onCancel();
            }
        });
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void crearoActualizar(Seccion seccion1){
        if(seccion1==null){
            setTitle("Registrar Sección");
            seccion=new Seccion();
        }else{
            seccion=seccion1;
            setTitle("Editar Sección");
            btnRegistrar.setText("Guardar");
            btnHecho.setText("Cancelar");
            guardarCopia();
        }
    }

    private void guardarCopia(){
        txtSeccion.setText(seccion.getSeccion());
        txtSeccion.setName(seccion.getSeccion());
    }
}
