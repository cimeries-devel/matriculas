package com.devel.views.dialogs;

import com.devel.models.Celular;
import com.devel.models.Persona;
import com.devel.utilities.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DAñadirCelular extends JDialog{
    private JTextField txtCelular;
    private JButton añadirButton;
    private JButton hechoButton;
    private JPanel panelPrincipal;
    private JTextField txtDescripcion;
    private Persona persona;

    public DAñadirCelular(Persona persona) {
        this.persona=persona;
        iniciarComponentes();
        hechoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
        añadirButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                añadir();
            }
        });
    }
    private void añadir(){
        if(txtDescripcion.getText().length()>0&&txtCelular.getText().length()>5){
            Celular celular=new Celular();
            celular.setDescipcion(txtDescripcion.getText().trim());
            celular.setNumero(txtCelular.getText().trim());
            persona.getCelulars().add(celular);
            limpiarControles();
            Utilities.sendNotification("Éxito","Celular añadido", TrayIcon.MessageType.INFO);
        }else{
            Utilities.sendNotification("Error","Complete todos los campos", TrayIcon.MessageType.ERROR);
        }
    }
    private void limpiarControles(){
        txtCelular.setText(null);
        txtDescripcion.setText(null);
    }
    private void iniciarComponentes(){
        setTitle("Añadir número de celular");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }
}
