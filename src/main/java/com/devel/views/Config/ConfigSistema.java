package com.devel.views.Config;

import com.devel.custom.FondoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConfigSistema extends JDialog {
    private JButton btnRetroceder;
    private JButton btnAvanzar;
    private JPanel panelContenido;
    private JTabbedPane TabConfiguraciones;
    private JLabel labelLogo;
    private JTextField textField1;
    private JButton editarButton;
    private JTextField textField2;
    private JRadioButton ImpresionPreguntarSiempreRadioButton;
    private JRadioButton ImpresionSiempreRadioButton;
    private JRadioButton ImpresionNoPreguntarRadioButton;
    private JPanel panelPrincipal;
    private JButton añdirCelularButton;
    private JTable tablaCelulares;
    private JPanel panelDatos;
    private JPanel panelImpreciones;
    private JPanel panelToken;
    private JPanel panelCelulares;
    private JButton guardarButton;
    private JButton btnHecho;

    public ConfigSistema(){
        iniciarComponentes();
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }
    private void iniciarComponentes(){
        setTitle("Configuraciones");
        setContentPane(panelPrincipal);
        setModal(true);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        cargarCursores();
    }

    private void cargarCursores(){
        labelLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRetroceder.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAvanzar.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        panelPrincipal=new FondoPanel("Images/fondo2.jpg");
        panelDatos =new FondoPanel("Images/fondo2.jpg");
        panelImpreciones=new FondoPanel("Images/fondo2.jpg");
        panelContenido=new FondoPanel("Images/fondo2.jpg");
    }
}
