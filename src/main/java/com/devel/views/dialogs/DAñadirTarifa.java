package com.devel.views.dialogs;

import com.devel.models.Grado;
import com.devel.models.Seguro;
import com.devel.models.Tarifa;
import com.devel.utilities.Utilities;
import com.devel.views.VPrincipal;
import com.sun.istack.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class DAñadirTarifa extends JDialog {
    private JTextField txtDescripción;
    private JTextField txtPrecio;
    private JButton registrarButton;
    private JButton hechoButton;
    private JPanel panelPrincipal;
    private Tarifa tarifa;

    public DAñadirTarifa(@Nullable Tarifa tarifa1) {
        if (tarifa1 == null) {
            tarifa = new Tarifa();
            setTitle("Crear Tarifa");
        } else {
            tarifa = tarifa1;
            setTitle("Editar Tarifa");
            registrarButton.setText("Guardar");
            hechoButton.setText("Cancelar");
            cargarTarifa();
        }
        iniciarComponentes();
        registrarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                registrar(tarifa1);
            }
        });
        hechoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }
    private boolean registrarTarifa(){
        if(txtDescripción.getText().length()>0&&txtPrecio.getText().length()>0){
            tarifa.setDescripcion(txtDescripción.getText().trim());
            tarifa.setPrecio(Double.valueOf(txtPrecio.getText().trim()));
            if(tarifa.getCreacion()==null){
                tarifa.setCreacion(new Date());
            }
            tarifa.guardar();
            return true;
        }else{
            return false;
        }
    }
    private void registrar(Tarifa tarifa1){
        if(registrarTarifa()){
            if(tarifa1==null){
                VPrincipal.tarifas.add(tarifa);
                tarifa=null;
                tarifa=new Tarifa();
                Utilities.sendNotification("Éxito","Tarifa registrada", TrayIcon.MessageType.INFO);
                txtDescripción.setText(null);
                txtPrecio.setText(null);
            }else {
                Utilities.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
                dispose();
            }
        }else{
            Utilities.sendNotification("Error","Rellene todos los campos", TrayIcon.MessageType.ERROR);
        }
    }
    private void iniciarComponentes(){
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }
    private void cargarTarifa(){
        txtDescripción.setText(tarifa.getDescripcion());
        txtPrecio.setText(String.valueOf(tarifa.getPrecio()));
    }
}
