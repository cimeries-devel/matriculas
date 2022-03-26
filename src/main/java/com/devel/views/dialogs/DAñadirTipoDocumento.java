package com.devel.views.dialogs;

import com.devel.models.TipoDocumento;
import com.devel.utilities.Colors;
import com.devel.utilities.Utilidades;
import com.devel.validators.TipoDocumentoValidator;
import com.devel.views.VPrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DAñadirTipoDocumento extends JDialog{
    private JTextField txtDescripcion;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private TipoDocumento tipoDocumento;

    public DAñadirTipoDocumento(){
        iniciarComponentes();
        tipoDocumento=new TipoDocumento();
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
    public DAñadirTipoDocumento(TipoDocumento tipoDocumento1) {
        iniciarComponentes();
        this.tipoDocumento=tipoDocumento1;
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
        tipoDocumento.setDescripcion(desripcion);
        tipoDocumento.setCodigo(codigo);

        Set<ConstraintViolation<TipoDocumento>> errors = TipoDocumentoValidator.loadViolations(tipoDocumento);
        if(errors.isEmpty()){
            tipoDocumento.guardar();
            VPrincipal.tipoDocumentos.add(tipoDocumento);
            tipoDocumento=new TipoDocumento();
            Utilidades.sendNotification("Éxito","Tipo de documento registrado", TrayIcon.MessageType.INFO);
            limpiarControles();
        }else {
            TipoDocumentoValidator.mostrarErrores(errors);
        }
    }

    private void actualizar(){
        String desripcion=txtDescripcion.getText().trim();
        String codigo=txtCodigo.getText().trim();
        tipoDocumento.setDescripcion(desripcion);
        tipoDocumento.setCodigo(codigo);

        Set<ConstraintViolation<TipoDocumento>> errors = TipoDocumentoValidator.loadViolations(tipoDocumento);
        if(errors.isEmpty()){
            tipoDocumento.guardar();
            Utilidades.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            cerrar();
        }else {
            TipoDocumentoValidator.mostrarErrores(errors);
        }
    }
    private void iniciarComponentes(){
        setTitle("Editar Tipo de documento");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        getRootPane().setDefaultButton(btnAñadir);
    }
    private void paraActualizar(){
        setTitle("Editar Tipo de documento");
        btnAñadir.setText("Guardar");
        btnHecho.setText("Cancelar");
        cargarTipoDeDocumento();
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
        txtDescripcion.setName(tipoDocumento.getDescripcion());
        txtCodigo.setName(tipoDocumento.getCodigo());
    }
    private void onCancel(){
        tipoDocumento.setDescripcion(txtDescripcion.getName());
        tipoDocumento.setCodigo(txtCodigo.getName());
        cerrar();
    }
    private void cerrar(){
        dispose();
    }

    private void cargarTipoDeDocumento(){
        txtDescripcion.setText(tipoDocumento.getDescripcion());
        txtCodigo.setText(tipoDocumento.getCodigo());
    }

    private void limpiarControles(){
        txtDescripcion.setText(null);
        txtCodigo.setText(null);
    }

}
