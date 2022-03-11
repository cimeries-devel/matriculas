package com.devel.views;

import com.devel.ForResources;
import com.devel.custom.FondoPanel;
import com.devel.utilities.PlaceHolder;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilities;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.intellij.uiDesigner.lw.LwHSpacer;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXSearchField;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;
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
    private JLabel label=new JLabel("");
    public static PlaceHolder placeholder;
    public Propiedades propiedades;
    public VLogin(){
        iniciarComponentes();
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (psfContraseña.getEchoChar()=='•') {
                    psfContraseña.setEchoChar((char) 0);
                    label.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x16/mostrarContraseña.png")));
                } else {
                    psfContraseña.setEchoChar('•');
                    label.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x16/ocultarContraseña.png")));
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
        ingresarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarVPrincipal();
            }
        });
    }
    public void cargarVPrincipal(){
        VPrincipal vPrincipal=new VPrincipal(propiedades);
        vPrincipal.setVisible(true);
        dispose();
    }
    private void iniciarComponentes()  {
        propiedades=new Propiedades();
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
    private void cargarMostrarContraseña(){
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
        label.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x16/ocultarContraseña.png")));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ingresarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setFocusable(false);
        recordarUsuarioCheckBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    private void cargarConfiguracion(){
        switch (propiedades.getTema()){
            case "claro":
                txtUsuario.setForeground(new Color(0x000000));
                psfContraseña.setForeground(new Color(0x000000));
                break;
            case "oscuro":
                txtUsuario.setForeground(new Color(0xFFFFFF));
                psfContraseña.setForeground(new Color(0xFFFFFF));
                break;
        }
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
//        panelLogin=new FondoPanel("Images/fondo.jpg");
    }
}
