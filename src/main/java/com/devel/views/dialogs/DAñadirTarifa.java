package com.devel.views.dialogs;

import com.devel.models.Tarifa;
import com.devel.utilities.Utilities;
import com.devel.validators.TarifaValidator;
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

public class DAñadirTarifa extends JDialog {
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JButton btnRegistrar;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private Tarifa tarifa;

    public DAñadirTarifa(){
        iniciarComponentes();
        tarifa = new Tarifa();

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
                dispose();
            }
        });
    }
    public DAñadirTarifa(Tarifa tarifa) {
        iniciarComponentes();
        this.tarifa=tarifa;
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
        Double precio= Double.valueOf(String.valueOf(txtPrecio.getText().trim().isEmpty()?0:txtPrecio.getText().trim()));
        tarifa.setDescripcion(desripcion);
        tarifa.setPrecio(precio);
        tarifa.setCreacion(new Date());
        tarifa.setDefecto(false);

        TarifaValidator validator = new TarifaValidator();
        Set<ConstraintViolation<Tarifa>> errors = validator.loadViolations(tarifa);
        if(errors.isEmpty()){
            tarifa.guardar();
            VPrincipal.tarifas.add(tarifa);
            tarifa=new Tarifa();
            Utilities.sendNotification("Éxito","Tarifa registrado", TrayIcon.MessageType.INFO);
            limpiarControles();
        }else {
            TarifaValidator.mostrarErrores(errors);
        }
    }

    private void actualizar(){
        String desripcion=txtDescripcion.getText().trim();
        Double precio= Double.valueOf(txtPrecio.getText().trim());
        tarifa.setDescripcion(desripcion);
        tarifa.setPrecio(precio);

        TarifaValidator validator = new TarifaValidator();
        Set<ConstraintViolation<Tarifa>> errors = validator.loadViolations(tarifa);
        if(errors.isEmpty()){
            tarifa.guardar();
            Utilities.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            dispose();
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
    }

    private void paraActualizar(){
        setTitle("Editar Tarifa");
        btnRegistrar.setText("Guardar");
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
