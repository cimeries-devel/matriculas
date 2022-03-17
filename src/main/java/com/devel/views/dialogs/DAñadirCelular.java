package com.devel.views.dialogs;

import com.devel.models.Celular;
import com.devel.models.Persona;
import com.devel.utilities.Utilidades;
import com.devel.validators.CelularValidator;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DAñadirCelular extends JDialog{
    private JTextField txtNumero;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private JTextField txtDescripcion;
    private Persona persona;
    private Celular celular;

    public DAñadirCelular(Persona persona) {
        this.persona=persona;
        iniciarComponentes();
        btnAñadir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                registrar();
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cerrar();
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public DAñadirCelular(Celular celular) {
        this.celular=celular;
        iniciarComponentes();
        paraActualizar();
        btnAñadir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                actualizar();
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onCancel();
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void registrar(){
        celular=new Celular();
        String desripcion=txtDescripcion.getText().trim();
        String numero= txtNumero.getText().trim();
        celular.setDescipcion(desripcion);
        celular.setNumero(numero);
        CelularValidator validator = new CelularValidator();
        Set<ConstraintViolation<Celular>> errors = validator.loadViolations(celular);
        if(errors.isEmpty()){
            if(persona.getId()!=null){
                celular.guardar();
                persona.getCelulares().add(celular);
                persona.guardar();
            }else{
                persona.getCelulares().add(celular);
            }
            Utilidades.sendNotification("Éxito","Número de celular registrado", TrayIcon.MessageType.INFO);
            limpiarControles();
        }else {
            CelularValidator.mostrarErrores(errors);
        }
    }

    private void actualizar(){
        String desripcion=txtDescripcion.getText().trim();
        String numero= txtNumero.getText().trim();
        celular.setDescipcion(desripcion);
        celular.setNumero(numero);
        CelularValidator validator = new CelularValidator();
        Set<ConstraintViolation<Celular>> errors = validator.loadViolations(celular);
        if(errors.isEmpty()){
            celular.guardar();
            Utilidades.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            cerrar();
        }else {
            CelularValidator.mostrarErrores(errors);
        }
    }

    private void iniciarComponentes(){
        setTitle("Añadir número de celular");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void limpiarControles(){
        txtNumero.setText(null);
        txtDescripcion.setText(null);
    }

    private void paraActualizar(){
        setTitle("Editar Celular");
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
        txtNumero.setName(celular.getNumero());
        txtDescripcion.setName(celular.getDescipcion());
        txtDescripcion.setText(celular.getDescipcion());
        txtNumero.setText(celular.getNumero());
    }
    private void onCancel(){
        celular.setNumero(txtNumero.getName());
        celular.setDescipcion(txtDescripcion.getName());
        cerrar();
    }
    private void cerrar(){
        dispose();
    }
}
