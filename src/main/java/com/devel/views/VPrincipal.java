package com.devel.views;

import com.devel.ForResources;
import com.devel.controllers.*;
import com.devel.custom.Cross;
import com.devel.custom.DnDTabbedPane;
import com.devel.models.*;
import com.devel.utilities.Propiedades;
import com.devel.views.Config.ConfigSistema;
import com.devel.views.menus.MenuGestiones;
import com.devel.views.menus.MenuInicio;
import com.devel.views.menus.MenuReportes;
import com.devel.views.tabs.VWelcome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JButton setingsButton;
    private JPanel panelDeTabPane;
    private VWelcome welcome;
    private Propiedades propiedades;
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

    public VPrincipal(Propiedades propiedades){
        this.propiedades=propiedades;
        iniciarComponentes();
        inicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionarButton.setBackground(new JButton().getBackground());
                reportesButton.setBackground(new JButton().getBackground());
                verificarTema(inicioButton);
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
                verificarTema(reportesButton);
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
                verificarTema(gestionarButton);
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
        añadirButtonOnJTabedpane();
            }
    private void añadirButtonOnJTabedpane(){
        tabContenido.setAlignmentX(1.0f);
        tabContenido.setAlignmentY(0.0f);
        panelDeTabPane.setLayout( new OverlayLayout(panelDeTabPane) );
        JButton jButton=new JButton(new ImageIcon(ForResources.class.getResource("Icons/x32/menu.png")));
        jButton.setAlignmentX(1.0f);
        jButton.setAlignmentY(0.0f);
        panelDeTabPane.add( jButton );
        panelDeTabPane.add(tabContenido);
        JPopupMenu pop_up = new JPopupMenu();
        JMenuItem cerrarPestaña = new JMenuItem("Cerrar Pestaña");
        JMenuItem cerrarOtras = new JMenuItem("Cerrar Otras Pestañas");
        JMenuItem cerrarTodas = new JMenuItem("Cerrar Todas Las Pestañas");
        cerrarPestaña.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(tabContenido.getSelectedIndex()!=-1){
                    tabContenido.removeTabAt(tabContenido.getSelectedIndex());
                }
            }
        });
        cerrarOtras.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(tabContenido.getSelectedIndex()!=-1){
                    JPanel tab= (JPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex());
                    String titulo= tabContenido.getTitleAt(tabContenido.getSelectedIndex());
                    tabContenido.removeAll();
                    tabContenido.add(tab,titulo);
                    tabContenido.setTabComponentAt(tabContenido.indexOfTab(titulo), new Cross(tabContenido,titulo));
                }
            }
        });
        cerrarTodas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tabContenido.removeAll();
            }
        });
        pop_up.add(cerrarPestaña);
        pop_up.addSeparator();
        pop_up.add(cerrarOtras);
        pop_up.add(cerrarTodas);
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==1){
                    pop_up.show(jButton,e.getX(),e.getY());
                }
            }
        });
    }
    private void cargarConfiguraciones(){
        ConfigSistema configSistema=new ConfigSistema(this,new Propiedades());
        configSistema.setVisible(true);
    }

    private void verificarTema(JButton boton){
        if(propiedades.getTema().equals("oscuro")){
            boton.setBackground(new Color(93,95,98,255));
        }else{
            boton.setBackground(new Color(230,230,230,255));
        }
    }
}
