package com.devel.views.dialogs;

import com.devel.models.Tarifa;
import com.devel.utilities.Colors;
import com.devel.utilities.Utilidades;
import com.devel.validators.TarifaValidator;
import com.devel.views.VPrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Set;

public class DAñadirTarifa extends JDialog {
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private Tarifa tarifa;

    public DAñadirTarifa(){
        iniciarComponentes();
        tarifa = new Tarifa();

        btnAñadir.addActionListener(e -> {
            registrar();
        });
        btnHecho.addActionListener(e -> {
            cerrar();
        });
        txtPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Utilidades.precioEsValido(e, txtPrecio.getText().trim())) {
                    e.consume();
                }
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    public DAñadirTarifa(Tarifa tarifa) {
        iniciarComponentes();
        this.tarifa=tarifa;
        paraActualizar();
        btnAñadir.addActionListener(e -> {
            actualizar();
        });
        btnHecho.addActionListener(e -> {
            onCancel();
        });
        txtPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Utilidades.precioEsValido(e, txtPrecio.getText().trim())) {
                    e.consume();
                }
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void registrar(){
        String desripcion=txtDescripcion.getText().trim();
        Double precio= Double.valueOf(String.valueOf(txtPrecio.getText().trim().isEmpty()?0:txtPrecio.getText().trim()));
        tarifa.setDescripcion(desripcion);
        tarifa.setPrecio(precio);
        tarifa.setCreacion(new Date());
        tarifa.setDefecto(false);

        Set<ConstraintViolation<Tarifa>> errors = TarifaValidator.loadViolations(tarifa);
        if(errors.isEmpty()){
            tarifa.guardar();
            VPrincipal.tarifas.add(tarifa);
            tarifa=new Tarifa();
            Utilidades.sendNotification("Éxito","Tarifa registrado", TrayIcon.MessageType.INFO);
            limpiarControles();
        }else {
            TarifaValidator.mostrarErrores(errors);
        }
    }

    private void actualizar(){
        String desripcion=txtDescripcion.getText().trim();
        Double precio= Double.valueOf(String.valueOf(txtPrecio.getText().trim().isEmpty()?0:txtPrecio.getText().trim()));
        tarifa.setDescripcion(desripcion);
        tarifa.setPrecio(precio);

        Set<ConstraintViolation<Tarifa>> errors = TarifaValidator.loadViolations(tarifa);
        if(errors.isEmpty()){
            tarifa.guardar();
            Utilidades.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            cerrar();
        }else {
            TarifaValidator.mostrarErrores(errors);
        }
    }
    private void iniciarComponentes(){
        setTitle("Registrar tarifa");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void paraActualizar(){
        setTitle("Editar Tarifa");
        btnAñadir.setText("Guardar");
        btnHecho.setText("Cancelar");
        cargarTarifa();
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
    private void cargarTarifa(){
        txtDescripcion.setText(tarifa.getDescripcion());
        txtPrecio.setText(String.valueOf(tarifa.getPrecio()));
    }
    private void guardarCopia(){
        txtDescripcion.setName(tarifa.getDescripcion());
        txtPrecio.setName(String.valueOf(tarifa.getPrecio()));
    }
    private void onCancel(){
        tarifa.setDescripcion(txtDescripcion.getName());
        tarifa.setPrecio(Double.valueOf(txtPrecio.getName()));
        cerrar();
    }
    private void cerrar(){
        dispose();
    }

    private void limpiarControles(){
        txtDescripcion.setText(null);
        txtPrecio.setText(null);
    }

}
