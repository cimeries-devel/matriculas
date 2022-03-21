package com.devel.views.Config;

import com.devel.utilities.Colors;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilidades;
import com.devel.views.VPrincipal;

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
                btnHecho.setForeground(new Color(0xFFFFFF));
                btnGuardar.setBackground(Colors.buttonDefect2);
                break;
            default:
                temaClaro.setSelected(true);
                btnHecho.setForeground(new Color(0x000000));
                btnGuardar.setForeground(Color.white);
                btnGuardar.setBackground(Colors.buttonDefect1);
                break;
        }
    }
}
