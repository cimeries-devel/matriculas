package com.devel.views;

import com.devel.custom.FondoPanel;
import com.devel.utilities.PlaceHolder;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class VLogin extends JFrame{
    private JPanel panelLogin;
    private JPanel panelDatos;
    private JLabel logoLogin;
    private JPasswordField psfContraseña;
    private JButton ingresarButton;
    private JTextField txtUsuario;
    private JCheckBox recordarUsuarioCheckBox;
    private JCheckBox checkBox1=new JCheckBox();
    public static PlaceHolder placeholder;
    public VLogin(){
        iniciarComponentes();
        checkBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox1.isSelected()) {
                    psfContraseña.setEchoChar((char) 0);
                } else {
                    psfContraseña.setEchoChar('•');
                }
            }
        });
    }
    private void iniciarComponentes(){
        setContentPane(panelLogin);
        setTitle("Login");
        placeholder= new PlaceHolder("Usuario", txtUsuario);
        placeholder= new PlaceHolder("Contraseña", psfContraseña);
        pack();
        setLocationRelativeTo(null);
        cargarCursores();
        psfContraseña.add(checkBox1,1);
    }
    private void cargarCursores(){
        psfContraseña.setEchoChar('•');
        checkBox1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ingresarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        panelLogin=new FondoPanel("Images/fondo2.jpg");
        psfContraseña=new JPasswordField();
        psfContraseña.setSize(new JTextField().getSize());
    }
}
