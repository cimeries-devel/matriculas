package com.devel.views.dialogs;

import com.devel.models.Seguro;
import com.devel.utilities.Colors;
import com.devel.utilities.Utilidades;
import com.devel.validators.SeguroValidator;
import com.devel.views.VPrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DAñadirSeguro extends JDialog{
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JTextField txtDescripcion;
    private Seguro seguro;

    public  DAñadirSeguro(){
        iniciarComponentes();
        seguro=new Seguro();
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

    public DAñadirSeguro(Seguro seguro) {
        iniciarComponentes();
        this.seguro=seguro;
        paraActualizar();
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


    private void registrar(){
        String desripcion=txtDescripcion.getText().trim();
        String codigo=txtCodigo.getText().trim();
        seguro.setDescripcion(desripcion);
        seguro.setCodigo(codigo);

        Set<ConstraintViolation<Seguro>> errors = SeguroValidator.loadViolations(seguro);
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

        Set<ConstraintViolation<Seguro>> errors = SeguroValidator.loadViolations(seguro);
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
        cargarConfiguracion();
    }

    private void limpiarControles(){
        txtDescripcion.setText(null);
        txtCodigo.setText(null);
    }

    private void paraActualizar() {
        setTitle("Editar Seguro");
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

    private void cargarConfiguracion(){
        switch (VPrincipal.tema){
            case "oscuro":
                btnHecho.setForeground(new Color(0xFFFFFF));
                btnAñadir.setBackground(Colors.buttonDefect2);
                break;
            default:
                btnHecho.setForeground(new Color(0x000000));
                btnAñadir.setForeground(Color.white);
                btnAñadir.setBackground(Colors.buttonDefect1);
                break;
        }
    }
}
