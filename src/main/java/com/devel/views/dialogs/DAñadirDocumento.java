package com.devel.views.dialogs;

import com.devel.models.Documento;
import com.devel.models.TipoDocumento;
import com.devel.utilities.Utilidades;
import com.devel.validators.DocumentoValidator;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DAñadirDocumento extends JDialog {
    private JPanel contentPane;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JTextField txtNumero;
    private Documento documento;

    public DAñadirDocumento(Documento documento) {
        this.documento=documento;
        iniciarComponentes();
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizar();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

    }
    private void iniciarComponentes(){
        setTitle("Editar Documento");
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        txtNumero.setName(documento.getNumero());
        txtNumero.setText(documento.getNumero());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                onCancel();
            }
        });
    }
    private void actualizar(){
        documento.setNumero(txtNumero.getText().trim());
        DocumentoValidator validator=new DocumentoValidator();
        Set<ConstraintViolation<Documento>> errors = validator.loadViolations(documento);
        if(errors.isEmpty()){
            Utilidades.sendNotification("Éxito","Documento registrado", TrayIcon.MessageType.INFO);
            cerrar();
        }else {
            DocumentoValidator.mostrarErrores(errors);
        }
    }
    private void onCancel(){
        documento.setNumero(txtNumero.getName());
        cerrar();
    }
    private void cerrar(){
        dispose();
    }

}
