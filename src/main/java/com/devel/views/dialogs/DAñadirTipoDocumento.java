package com.devel.views.dialogs;

import com.devel.models.Tarifa;
import com.devel.models.TipoDocumento;
import com.devel.utilities.Utilidades;
import com.devel.validators.TarifaValidator;
import com.devel.validators.TipoDocumentoValidator;
import com.devel.views.VPrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Set;

public class DAñadirTipoDocumento extends JDialog{
    private JTextField txtDescripcion;
    private JButton btnRegistrar;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private TipoDocumento tipoDocumento;

    public DAñadirTipoDocumento(){
        iniciarComponentes();
        tipoDocumento=new TipoDocumento();
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
    public DAñadirTipoDocumento(TipoDocumento tipoDocumento1) {
        iniciarComponentes();
        this.tipoDocumento=tipoDocumento1;
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
        tipoDocumento.setDescripcion(desripcion);
        tipoDocumento.setCodigo(codigo);

        TipoDocumentoValidator validator = new TipoDocumentoValidator();
        Set<ConstraintViolation<TipoDocumento>> errors = validator.loadViolations(tipoDocumento);
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

        TipoDocumentoValidator validator = new TipoDocumentoValidator();
        Set<ConstraintViolation<TipoDocumento>> errors = validator.loadViolations(tipoDocumento);
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
    }
    private void paraActualizar(){
        setTitle("Editar Tipo de documento");
        btnRegistrar.setText("Guardar");
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
