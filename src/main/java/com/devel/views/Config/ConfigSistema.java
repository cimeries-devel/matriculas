package com.devel.views.Config;

import com.devel.custom.FondoPanel;

import javax.swing.*;

public class ConfigSistema extends JDialog {
    private JButton button1;
    private JButton button2;
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

    public ConfigSistema(){
        setTitle("Configuraciones");
        setContentPane(panelPrincipal);
        setModal(true);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        panelPrincipal=new FondoPanel("Images/fondo2.jpg");
        panelDatos =new FondoPanel("Images/fondo2.jpg");
        panelImpreciones=new FondoPanel("Images/fondo2.jpg");
        panelContenido=new FondoPanel("Images/fondo2.jpg");
    }
}
