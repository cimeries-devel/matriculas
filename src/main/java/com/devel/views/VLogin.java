package com.devel.views;

import com.devel.ForResources;
import com.devel.custom.FondoPanel;
import com.devel.utilities.PlaceHolder;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilities;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXSearchField;

import javax.swing.*;
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
    private JCheckBox checkBox1=new JCheckBox();
    public static PlaceHolder placeholder;
    public Propiedades propiedades;
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
                    checkBox1.setVisible(true);
                }else{
                    checkBox1.setVisible(false);
                }
            }
        });
        ingresarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VPrincipal vPrincipal=new VPrincipal();
                vPrincipal.setVisible(true);
                dispose();
            }
        });
    }
    private void iniciarComponentes(){
        propiedades=new Propiedades();
        setDefaultCloseOperation(3);
        setContentPane(panelLogin);
        setTitle("Login");
        placeholder= new PlaceHolder("Usuario", txtUsuario);
        placeholder= new PlaceHolder("Contraseña", psfContraseña);
        pack();
        setLocationRelativeTo(null);
        cargarCursores();
        psfContraseña.add(checkBox1,1);
        checkBox1.setBorder(BorderFactory.createEmptyBorder(5, 142, 0, 5));
        checkBox1.setVisible(false);
    }
    private void cargarCursores(){
        psfContraseña.setEchoChar('•');
        checkBox1.setOpaque(false);
        checkBox1.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x16/ocultarContraseña.png")));
        checkBox1.setSelectedIcon(new ImageIcon(ForResources.class.getResource("Icons/x16/mostrarContraseña.png")));
        checkBox1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ingresarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBox1.setFocusable(false);
        recordarUsuarioCheckBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        panelLogin=new FondoPanel("Images/fondo2.jpg");
    }
}
