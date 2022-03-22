package com.devel.views;

import com.devel.Principal;
import com.devel.utilities.Colors;
import com.devel.utilities.PlaceHolder;
import com.devel.utilities.Propiedades;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VLogin extends JFrame{
    private JPanel panelLogin;
    private JPanel panelDatos;
    private JLabel logoLogin;
    private JPasswordField psfContraseña;
    private JButton btnIngresar;
    private JTextField txtUsuario;
    private JCheckBox recordarUsuarioCheckBox;
    private JLabel label=new JLabel("");
    public static PlaceHolder placeholder;
    public Propiedades propiedades;
    public VLogin(Propiedades propiedades){
        this.propiedades=propiedades;
        iniciarComponentes();
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (psfContraseña.getEchoChar()=='•') {
                    psfContraseña.setEchoChar((char) 0);
                    label.setIcon(new ImageIcon(Principal.class.getResource("Icons/x16/mostrarContraseña.png")));
                } else {
                    psfContraseña.setEchoChar('•');
                    label.setIcon(new ImageIcon(Principal.class.getResource("Icons/x16/ocultarContraseña.png")));
                }
            }
        });
        psfContraseña.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (psfContraseña.getText().length() > 14) {
                    e.consume();
                }
            }
        });
        psfContraseña.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(psfContraseña.getText().length()>0){
                    label.setVisible(true);
                }else{
                    label.setVisible(false);
                }
            }
        });
        btnIngresar.addActionListener(e -> {
            cargarVPrincipal();
        });
    }
    public void cargarVPrincipal(){
        VPrincipal vPrincipal=new VPrincipal(propiedades);
        vPrincipal.setVisible(true);
        dispose();
    }
    private void iniciarComponentes()  {
        setDefaultCloseOperation(3);
        setContentPane(panelLogin);
        setTitle("Login");
        placeholder= new PlaceHolder("Usuario", txtUsuario);
        placeholder= new PlaceHolder("Contraseña", psfContraseña);
        pack();
        setLocationRelativeTo(null);
        cargarMostrarContraseña();
        cargarCursores();
        cargarConfiguracion();
    }
    private void cargarMostrarContraseña() {
        JPanel panel=new JPanel();
        JLabel label1=new JLabel("                                              ");
        label1.setOpaque(false);
        panel.setOpaque(false);
        panel.add(label1);
        panel.add(label);
        label.setSize(new Dimension(20,30));
        label.setMaximumSize(new Dimension(20,30));
        psfContraseña.add(panel);
        label.setVisible(false);
    }
    private void cargarCursores(){
        psfContraseña.setEchoChar('•');
        label.setOpaque(false);
        label.setIcon(new ImageIcon(Principal.class.getResource("Icons/x16/ocultarContraseña.png")));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setFocusable(false);
        recordarUsuarioCheckBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    private void cargarConfiguracion(){
        switch (propiedades.getTema()){
            case "claro":
                txtUsuario.setForeground(new Color(0x000000));
                psfContraseña.setForeground(new Color(0x000000));
                recordarUsuarioCheckBox.setForeground(new Color(0x000000));
                btnIngresar.setForeground(Color.white);
                btnIngresar.setBackground(Colors.buttonDefect1);
                break;
            case "oscuro":
                txtUsuario.setForeground(new Color(0xBABABA));
                psfContraseña.setForeground(new Color(0xBABABA));
                recordarUsuarioCheckBox.setForeground(new Color(0xBABABA));
                btnIngresar.setBackground(Colors.buttonDefect2);
                break;
        }
    }


}
