package com.devel.views.dialogs;

import com.devel.models.Seguro;
import com.devel.utilities.Utilidades;
import com.devel.validators.SeguroValidator;
import com.devel.views.VPrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

public class DAñadirSeguro extends JDialog{
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnRegistrar;
    private JButton btnHecho;
    private JTextField txtDescripcion;
    private Seguro seguro;

    public  DAñadirSeguro(){
        iniciarComponentes();
        seguro=new Seguro();
        btnRegistrar.addMouseListener(new MouseAdapter() {
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
    }

    public DAñadirSeguro(Seguro seguro) {
        iniciarComponentes();
        this.seguro=seguro;
        paraActualizar();
        btnRegistrar.addMouseListener(new MouseAdapter() {
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
    }


    private void registrar(){
        String desripcion=txtDescripcion.getText().trim();
        String codigo=txtCodigo.getText().trim();
        seguro.setDescripcion(desripcion);
        seguro.setCodigo(codigo);

        SeguroValidator validator = new SeguroValidator();
        Set<ConstraintViolation<Seguro>> errors = validator.loadViolations(seguro);
        if(errors.isEmpty()){
            seguro.guardar();
            VPrincipal.seguros.add(seguro);
            VPrincipal.segurosConTodos.add(seguro);
            seguro=new Seguro();
            Utilidades.sendNotification("Éxito","Seguro registrado", TrayIcon.MessageType.INFO);
            limpiarControles();
        }else {
            SeguroValidator.mostrarErrores(errors);
        }
    }

    private void actualizar(){
        String desripcion=txtDescripcion.getText().trim();
        String codigo=txtCodigo.getText().trim();
        seguro.setDescripcion(desripcion);
        seguro.setCodigo(codigo);

        SeguroValidator validator = new SeguroValidator();
        Set<ConstraintViolation<Seguro>> errors = validator.loadViolations(seguro);
        if(errors.isEmpty()){
            seguro.guardar();
            Utilidades.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            cerrar();
        }else {
            SeguroValidator.mostrarErrores(errors);
        }
    }

    private void iniciarComponentes(){
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void limpiarControles(){
        txtDescripcion.setText(null);
        txtCodigo.setText(null);
    }

    private void paraActualizar() {
        setTitle("Editar Seguro");
        btnRegistrar.setText("Guardar");
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
        txtCodigo.setName(seguro.getCodigo());
        txtDescripcion.setName(seguro.getDescripcion());
        cargarSeguro();
    }
    private void cargarSeguro(){
        txtDescripcion.setText(seguro.getDescripcion());
        txtCodigo.setText(seguro.getCodigo());
    }
    private void onCancel(){
        seguro.setCodigo(txtCodigo.getName());
        seguro.setDescripcion(txtDescripcion.getName());
        cerrar();
    }

    private void cerrar(){
        dispose();
    }
}
