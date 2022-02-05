package com.devel.views.dialogs;

import com.devel.models.Persona;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DAñadirCelular extends JDialog{
    private JTextField textField2;
    private JButton añadirButton;
    private JButton hechoButton;
    private JPanel panelPrincipal;
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

    }
    private void iniciarComponentes(){
        setTitle("Añadir número de celular");
        setContentPane(panelPrincipal);
        setModal(true);
    }
}
