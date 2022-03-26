package com.devel.views.dialogs;

import com.devel.models.Seccion;
import com.devel.utilities.Colors;
import com.devel.utilities.Utilidades;
import com.devel.validators.SeccionValidator;
import com.devel.views.VPrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DAñadirSecciones extends JDialog{
    private JTextField txtSeccion;
    private JButton btnAñadir;
    private JPanel panelPrincipal;
    private JButton btnHecho;
    private Seccion seccion;

    public DAñadirSecciones(){
        iniciarComponentes();
        seccion=new Seccion();
        btnAñadir.addActionListener(e -> {
            registrar();
        });
        btnHecho.addActionListener(e -> {
            cerrar();
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public DAñadirSecciones(Seccion seccion) {
        iniciarComponentes();
        this.seccion=seccion;
        paraActualizar();
        guardarCopia();
        btnAñadir.addActionListener(e -> {
            actualizar();
        });
        btnHecho.addActionListener(e -> {
            onCancel();
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void iniciarComponentes(){
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void registrar(){
        String desripcion=txtSeccion.getText().trim();
        seccion.setSeccion(desripcion);

        Set<ConstraintViolation<Seccion>> errors = SeccionValidator.loadViolations(seccion);
        if(errors.isEmpty()){
            seccion.guardar();
            VPrincipal.secciones.add(seccion);
            seccion=new Seccion();
            Utilidades.sendNotification("Éxito","Sección registrada", TrayIcon.MessageType.INFO);
            limpiarControles();
        }else {
            SeccionValidator.mostrarErrores(errors);
        }
    }

    private void actualizar(){
        String desripcion=txtSeccion.getText().trim();
        seccion.setSeccion(desripcion);

        Set<ConstraintViolation<Seccion>> errors = SeccionValidator.loadViolations(seccion);
        if(errors.isEmpty()){
            seccion.guardar();
            Utilidades.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            cerrar();
        }else {
            SeccionValidator.mostrarErrores(errors);
        }
    }

    private void paraActualizar(){
        setTitle("Editar Sección");
        btnAñadir.setText("Guardar");
        btnHecho.setText("Cancelar");
        guardarCopia();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                onCancel();
            }
        });
    }

    private void guardarCopia(){
        txtSeccion.setText(seccion.getSeccion());
        txtSeccion.setName(seccion.getSeccion());
    }
    private void onCancel(){
        seccion.setSeccion(txtSeccion.getName());
        cerrar();
    }

    private void cerrar(){
        dispose();
    }

    private void limpiarControles(){
        txtSeccion.setText(null);
    }

}
