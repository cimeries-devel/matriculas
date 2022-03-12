package com.devel.views;

import com.devel.ForResources;
import com.devel.controllers.*;
import com.devel.custom.DnDTabbedPane;
import com.devel.custom.TabPanel;
import com.devel.models.*;
import com.devel.utilities.Colors;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilities;
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
    private JButton inicioButton;
    private JButton reportesButton;
    private JButton gestionarButton;
    private JSplitPane splitPane;
    private JButton button1;
    private JPanel panelDeTabPane;
    private JButton setingsButton;
    private JPanel panelMenus;
    private JPanel panelControles;
    private VWelcome welcome;
    private JButton jButton;
    public static Propiedades propiedades;
    public static String tema;
    private MenuInicio inicioOpciones;
    private MenuReportes menuReportes;
    private MenuGestiones menuGestiones;
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
                Utilities.buttonSelectedOrEntered(inicioButton);
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
                Utilities.buttonSelectedOrEntered(reportesButton);
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
                Utilities.buttonSelectedOrEntered(gestionarButton);
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
                cargarVConfiguraciones();
            }
        });

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                Utilities.tema(propiedades.getTema());
                VLogin vLogin=new VLogin(propiedades);
                vLogin.setVisible(true);
            }
        });
    }

    private void iniciarComponentes(){
        setContentPane(contentPane);
        setTitle("Gestión matrículas");
        tema=propiedades.getTema();
        inicioOpciones=new MenuInicio(tabContenido);
        menuReportes=new MenuReportes(tabContenido);
        menuGestiones=new MenuGestiones(tabContenido);
        setDefaultCloseOperation(3);
        splitPane.setRightComponent(null);
        splitPane.setRightComponent(inicioOpciones.traerInicioOpciones());
        inicioOpciones.cargarBienvenida();
        contentPane.updateUI();
        añadirButtonOnJTabedpane();
        cargarConfiguracion();
        pack();
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
    }
    private void añadirButtonOnJTabedpane(){
        tabContenido.setAlignmentX(1.0f);
        tabContenido.setAlignmentY(0.0f);
        panelDeTabPane.setLayout( new OverlayLayout(panelDeTabPane) );
        jButton=new JButton();
        jButton.setMargin(new Insets(6,3,6,3));
        jButton.setAlignmentX(1.0f);
        jButton.setAlignmentY(0.0f);
        jButton.setFocusPainted(false);

        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Utilities.buttonSelectedOrEntered(jButton);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                Utilities.buttonExited(jButton);
            }
        });
        panelDeTabPane.add(jButton);
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
                    TabPanel tab= (TabPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex());
                    String titulo= tabContenido.getTitleAt(tabContenido.getSelectedIndex());
                    tabContenido.removeAll();
                    tabContenido.addTab(titulo,tab.getIcon(),tab);
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
    private void cargarConfiguracion(){
        switch (tema){
            case "claro":
                jButton.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x16/menu1.png")));
                inicioButton.setBackground(Colors.buttonSelected1);
                ((TabPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex())).getOption().setBackground(Colors.buttonSelected1);
                break;
            case "oscuro":
                jButton.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x16/menu2.png")));
                inicioButton.setBackground(Colors.buttonSelected2);
                ((TabPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex())).getOption().setBackground(Colors.buttonSelected2);
                break;
        }
        ((TabPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex())).getOption().requestFocus();
    }
    private void cargarVConfiguraciones(){
        ConfigSistema configSistema=new ConfigSistema(this,propiedades);
        configSistema.setVisible(true);
    }
}
