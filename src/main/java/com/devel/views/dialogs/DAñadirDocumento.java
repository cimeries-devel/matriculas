package com.devel.views.dialogs;

import com.devel.models.Documento;
import com.devel.utilities.Colors;
import com.devel.utilities.Utilidades;
import com.devel.validators.DocumentoValidator;
import com.devel.views.VPrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DAñadirDocumento extends JDialog {
    private JPanel panelPrincipal;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JTextField txtNumero;
    private Documento documento;

    public DAñadirDocumento(Documento documento) {
        this.documento=documento;
        iniciarComponentes();
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
        setTitle("Editar Documento");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        txtNumero.setName(documento.getNumero());
        txtNumero.setText(documento.getNumero());
        cargarConfiguracion();
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
        Set<ConstraintViolation<Documento>> errors = DocumentoValidator.loadViolations(documento);
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
