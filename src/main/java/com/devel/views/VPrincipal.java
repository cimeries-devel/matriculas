package com.devel.views;

import com.devel.ForResources;
import com.devel.controllers.*;
import com.devel.custom.DnDTabbedPane;
import com.devel.models.*;
import com.devel.utilities.Propiedades;
import com.devel.views.Config.ConfigSistema;
import com.devel.views.menus.MenuGestiones;
import com.devel.views.menus.MenuInicio;
import com.devel.views.menus.MenuReportes;
import com.devel.views.tabs.VWelcome;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

public class VPrincipal extends JFrame{
    public JPanel contentPane;
    private DnDTabbedPane tabContenido;
    private JMenu btnInicio;
    private JMenu btnAyuda;
    private JMenu btnConfiguraciones;
    private JPanel paneControls;
    private JPanel paneButtons;
    private JButton inicioButton;
    private JButton reportesButton;
    private JButton gestionarButton;
    private JSplitPane splitPane;
    private JButton button1;
    private VWelcome welcome;
    private MenuInicio inicioOpciones=new MenuInicio(tabContenido);
    private MenuReportes menuReportes=new MenuReportes(tabContenido);
    private MenuGestiones menuGestiones=new MenuGestiones(tabContenido);
    public static Vector<TipoDocumento> tipoDocumentos=TipoDcoumentos.getTodos();
    public static Vector<Registro> alumnosMatriculados=Registros.getMatriculados();
    public static Vector<Nivel> niveles= Niveles.getTodos();
    public static Vector<Grado> grados= Grados.getTodos();
    public static Vector<Tarifa> tarifas= Tarifas.getTodas();
    public static Vector<Seguro> seguros= Seguros.todos();
    public static Vector<Seccion> secciones= Secciones.todos();
    public static Vector<Seguro> segurosConTodos=new Vector<>(Seguros.todosconTodos());
    public VPrincipal(){
        iniciarComponentes();
        inicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionarButton.setBackground(new JButton().getBackground());
                reportesButton.setBackground(new JButton().getBackground());
                inicioButton.setBackground(new Color(230,230,230,255));
                reportesButton.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x32/reportsDefecto.png")));
                gestionarButton.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x32/gestionarDefecto.png")));
                inicioButton.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x32/inicio.png")));
                splitPane.setRightComponent(null);
                splitPane.setRightComponent(inicioOpciones.traerInicioOpciones());
                contentPane.updateUI();
            }
        });
        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inicioButton.setBackground(new JButton().getBackground());
                gestionarButton.setBackground(new JButton().getBackground());
                reportesButton.setBackground(new Color(230,230,230,255));
                inicioButton.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x32/incioDefecto.png")));
                gestionarButton.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x32/gestionarDefecto.png")));
                reportesButton.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x32/reportes.png")));
                splitPane.setRightComponent(null);
                splitPane.setRightComponent(menuReportes.traerReportesOpciones());
                contentPane.updateUI();
            }
        });
        gestionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inicioButton.setBackground(new JButton().getBackground());
                reportesButton.setBackground(new JButton().getBackground());
                gestionarButton.setBackground(new Color(230,230,230,255));
                inicioButton.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x32/incioDefecto.png")));
                reportesButton.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x32/reportsDefecto.png")));
                gestionarButton.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x32/gestionar.png")));
                splitPane.setRightComponent(null);
                splitPane.setRightComponent(menuGestiones.traerInicioOpciones());
                contentPane.updateUI();
            }
        });

        btnConfiguraciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarConfiguraciones();
            }
        });

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                VLogin vLogin=new VLogin();
                vLogin.setVisible(true);
            }
        });
    }

    private void iniciarComponentes(){
        setContentPane(contentPane);
        setTitle("Gestión matrículas");
        setDefaultCloseOperation(3);
        inicioOpciones.cargarBienvenida();
        splitPane.setRightComponent(null);
        splitPane.setRightComponent(inicioOpciones.traerInicioOpciones());
        contentPane.updateUI();
        pack();
        setLocationRelativeTo(null);
    }
    private void cargarConfiguraciones(){
        ConfigSistema configSistema=new ConfigSistema(this,new Propiedades());
        configSistema.setVisible(true);
    }
    private void createUIComponents(){
        tabContenido=new DnDTabbedPane();
    }
}
