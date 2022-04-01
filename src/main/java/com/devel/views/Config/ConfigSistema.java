package com.devel.views.Config;

import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilidades;
import com.devel.views.VPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConfigSistema extends JDialog {
    private JButton btnRetroceder;
    private JButton btnAvanzar;
    private JPanel panelContenido;
    private JTabbedPane tabConfiguraciones;
    private JLabel labelLogo;
    private JTextField textField1;
    private JButton editarButton;
    private JTextField textField2;
    private JPanel panelPrincipal;
    private JButton añdirCelularButton;
    private JTable tablaCelulares;
    private JPanel panelDatos;
    private JPanel panelImpreciones;
    private JPanel panelToken;
    private JPanel panelCelulares;
    private JButton btnGuardar;
    private JButton btnHecho;
    private JRadioButton temaOscuro;
    private JRadioButton temaClaro;
    private JPanel panelAplicacion;
    private JRadioButton ImpresionPreguntarSiempreRadioButton;
    private JRadioButton ImpresionSiempreRadioButton;
    private JRadioButton ImpresionNoPreguntarRadioButton;
    private Propiedades propiedades;
    private VPrincipal vPrincipal;

    public ConfigSistema(VPrincipal vPrincipal,Propiedades propiedades){
        this.vPrincipal=vPrincipal;
        this.propiedades=propiedades;
        iniciarComponentes();
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                guardarCambios();
            }
        });
        btnAvanzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambio(true);
            }
        });
        btnRetroceder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambio(false);
            }
        });
    }
    private void cambio(boolean avanzar){
        if(avanzar){
            if(tabConfiguraciones.getSelectedIndex()!=tabConfiguraciones.getTabCount()-1){
                tabConfiguraciones.setSelectedIndex(tabConfiguraciones.getSelectedIndex()+1);
            }
        }else{
            if(tabConfiguraciones.getSelectedIndex()!=0){
                tabConfiguraciones.setSelectedIndex(tabConfiguraciones.getSelectedIndex()-1);
            }
        }
    }
    private void guardarCambios(){
        if(temaOscuro.isSelected()){
            propiedades.setTema("oscuro");
        }else{
            propiedades.setTema("claro");
        }
        propiedades.guardar();
        Utilidades.sendNotification("Éxito","Cambios guardados, algunos cambios se aplciarán despues del reinicio", TrayIcon.MessageType.INFO);
        dispose();
    }
    private void iniciarComponentes(){
        setTitle("Configuraciones");
        setContentPane(panelPrincipal);
        setModal(true);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        cargarCursores();
        cargarConfiguracion();
        getRootPane().setDefaultButton(btnGuardar);
    }
    private void cargarCursores(){
        labelLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRetroceder.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAvanzar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void cargarConfiguracion(){
        switch (propiedades.getTema()){
            case "oscuro":
                temaOscuro.setSelected(true);
                break;
            default:
                temaClaro.setSelected(true);
                break;
        }
    }
}
