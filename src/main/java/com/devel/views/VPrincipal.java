package com.devel.views;

import com.devel.Principal;
import com.devel.controllers.*;
import com.devel.custom.DnDTabbedPane;
import com.devel.custom.TabPanel;
import com.devel.models.*;
import com.devel.utilities.Colors;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilidades;
import com.devel.views.Config.ConfigSistema;
import com.devel.views.menus.MenuGestiones;
import com.devel.views.menus.MenuInicio;
import com.devel.views.menus.MenuReportes;
import com.devel.views.tabs.VWelcome;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;

public class VPrincipal extends JFrame {
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
    public JLabel lblDerecha;
    public JLabel lblCentro;
    public JLabel lblIzquierdo;
    private VWelcome welcome;
    private JButton jButton;
    public static Propiedades propiedades;
    public static String tema;
    private MenuInicio menuInicio;
    private MenuReportes menuReportes;
    private MenuGestiones menuGestiones;
    public static Vector<TipoDocumento> tipoDocumentos = TipoDocumentos.getTodos();
    public static Vector<TipoRelacion> tipoRelaciones = TiposRelaciones.getTodos();
    public static Vector<TipoRelacion> tipoRelacionesConTodos = TiposRelaciones.getTodosConTodos();
    public static Vector<Registro> alumnosMatriculados = Registros.getMatriculasPorAño(Calendar.getInstance());
    public static Vector<Persona> alumnos = Personas.alumnos();
    public static Vector<Persona> familiares = Personas.familares();
    public static Vector<Nivel> niveles = Niveles.getTodos();
    public static Vector<Grado> grados = Grados.getTodos();
    public static Vector<Tarifa> tarifas = Tarifas.getTodas();
    public static Vector<Seguro> seguros = Seguros.todos();
    public static Vector<Seccion> secciones = Secciones.todos();
    public static Vector<Salon> salones = Salones.getTodos();
    public static Vector<Grado> gradosConTodos = Grados.getTodosConTodos();
    public static Vector<Nivel> nivelesConTodos = Niveles.getTodosConTodos();
    public static Vector<Seccion> seccionesConTodos = Secciones.todosConTodos();
    public static Vector<Seguro> segurosConTodos = Seguros.todosconTodos();


    public VPrincipal(Propiedades propiedades) {
        this.propiedades = propiedades;
        iniciarComponentes();
        inicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionarButton.setBackground(new JButton().getBackground());
                reportesButton.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(inicioButton);
                reportesButton.setIcon(new ImageIcon(Principal.class.getResource("Icons/x32/reportsDefecto.png")));
                gestionarButton.setIcon(new ImageIcon(Principal.class.getResource("Icons/x32/gestionarDefecto.png")));
                inicioButton.setIcon(new ImageIcon(Principal.class.getResource("Icons/x32/inicio.png")));
                splitPane.setRightComponent(null);
                splitPane.setRightComponent(menuInicio.traerInicioOpciones());
                contentPane.updateUI();
            }
        });
        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inicioButton.setBackground(new JButton().getBackground());
                gestionarButton.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(reportesButton);
                inicioButton.setIcon(new ImageIcon(Principal.class.getResource("Icons/x32/incioDefecto.png")));
                gestionarButton.setIcon(new ImageIcon(Principal.class.getResource("Icons/x32/gestionarDefecto.png")));
                reportesButton.setIcon(new ImageIcon(Principal.class.getResource("Icons/x32/reportes.png")));
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
                Utilidades.buttonSelectedOrEntered(gestionarButton);
                inicioButton.setIcon(new ImageIcon(Principal.class.getResource("Icons/x32/incioDefecto.png")));
                reportesButton.setIcon(new ImageIcon(Principal.class.getResource("Icons/x32/reportsDefecto.png")));
                gestionarButton.setIcon(new ImageIcon(Principal.class.getResource("Icons/x32/gestionar.png")));
                splitPane.setRightComponent(null);
                splitPane.setRightComponent(menuGestiones.traerInicioOpciones());
                contentPane.updateUI();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarLogin();
            }
        });
        btnConfiguraciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarVConfiguraciones();
            }
        });
    }

    private void iniciarComponentes() {
        setContentPane(contentPane);
        setTitle("Gestión matrículas");
        setIconImage((new ImageIcon(Principal.class.getResource("Images/documento.png"))).getImage());
        tema = propiedades.getTema();
        menuInicio = new MenuInicio(tabContenido);
        menuReportes = new MenuReportes(tabContenido);
        menuGestiones = new MenuGestiones(tabContenido);
        splitPane.setRightComponent(null);
        splitPane.setRightComponent(menuInicio.traerInicioOpciones());
        menuInicio.cargarBienvenida();
        contentPane.updateUI();
        tabContenido.setInicio(menuInicio);
        añadirButtonOnJTabedpane();
        cargarConfiguracion();
        pack();
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (salir()) {
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                    super.windowClosing(e);
                }
            }
        });
    }

    private void añadirButtonOnJTabedpane() {
        tabContenido.setAlignmentX(1.0f);
        tabContenido.setAlignmentY(0.0f);
        panelDeTabPane.setLayout(new OverlayLayout(panelDeTabPane));
        jButton = new JButton();
        jButton.setMargin(new Insets(6, 3, 6, 3));
        jButton.setAlignmentX(1.0f);
        jButton.setAlignmentY(0.0f);
        jButton.setFocusPainted(false);
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Utilidades.buttonSelectedOrEntered(jButton);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Utilidades.buttonExited(jButton);
            }
        });
        panelDeTabPane.add(jButton);
        panelDeTabPane.add(tabContenido);
        JPopupMenu pop_up = new JPopupMenu();
        JMenuItem cerrarPestaña = new JMenuItem("Cerrar Pestaña");
        JMenuItem cerrarOtras = new JMenuItem("Cerrar Otras Pestañas");
        JMenuItem cerrarTodas = new JMenuItem("Cerrar Todas Las Pestañas");
        cerrarPestaña.addActionListener(e -> {
            if (tabContenido.getSelectedIndex() != -1) {
                ((TabPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex())).getOption().setBackground(new JButton().getBackground());
                tabContenido.removeTabAt(tabContenido.getSelectedIndex());
                if (tabContenido.getTabCount() == 0) {
                    menuInicio.cargarBienvenida();
                }
                TabPanel tabPanel = (TabPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex());
                Utilidades.buttonSelectedOrEntered(tabPanel.getOption());
            }
        });
        cerrarOtras.addActionListener(e -> {
            if (tabContenido.getSelectedIndex() != -1) {
                TabPanel tab = (TabPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex());
                String titulo = tabContenido.getTitleAt(tabContenido.getSelectedIndex());
                tabContenido.removeAll();
                tabContenido.addTab(titulo, tab.getIcon(), tab);
                TabPanel tabPanel = (TabPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex());
                Utilidades.buttonSelectedOrEntered(tabPanel.getOption());
            }
        });
        cerrarTodas.addActionListener(e -> {
            for (Component component : tabContenido.getComponents()) {
                if (tabContenido.indexOfComponent(component) != -1) {
                    ((TabPanel) component).getOption().setBackground(new JButton().getBackground());
                }
            }
            tabContenido.removeAll();
            menuInicio.cargarBienvenida();
            TabPanel tabPanel = (TabPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex());
            Utilidades.buttonSelectedOrEntered(tabPanel.getOption());
        });
        pop_up.add(cerrarPestaña);
        pop_up.addSeparator();
        pop_up.add(cerrarOtras);
        pop_up.add(cerrarTodas);
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                    pop_up.show(jButton, e.getX(), e.getY());
                }
            }
        });
    }

    private void cargarConfiguracion() {
        switch (tema) {
            case "claro":
                jButton.setIcon(new ImageIcon(Principal.class.getResource("Icons/x16/menu1.png")));
                inicioButton.setBackground(Colors.buttonSelected1);
                ((TabPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex())).getOption().setBackground(Colors.buttonSelected1);
                break;
            case "oscuro":
                jButton.setIcon(new ImageIcon(Principal.class.getResource("Icons/x16/menu2.png")));
                inicioButton.setBackground(Colors.buttonSelected2);
                ((TabPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex())).getOption().setBackground(Colors.buttonSelected2);
                break;
        }
        ((TabPanel) tabContenido.getComponentAt(tabContenido.getSelectedIndex())).getOption().requestFocus();
    }

    private void cargarVConfiguraciones() {
        ConfigSistema configSistema = new ConfigSistema(this, propiedades);
        configSistema.setVisible(true);
    }

    private void cargarLogin() {
        int sioNo = JOptionPane.showOptionDialog(null, "¿Está seguro?", "Cerrar Sesión", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
        if (sioNo == 0) {
            dispose();
            Utilidades.tema(propiedades.getTema());
            VLogin vLogin = new VLogin(propiedades);
            vLogin.setVisible(true);
        }
    }

    private boolean salir() {
        int sioNo = JOptionPane.showOptionDialog(null, "¿Está seguro?", "Salir", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
        return sioNo == 0;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), 1, 1));
        contentPane.setPreferredSize(new Dimension(700, 500));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JMenuBar menuBar1 = new JMenuBar();
        menuBar1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, 0));
        menuBar1.setBackground(new Color(-1));
        menuBar1.setOpaque(false);
        panel1.add(menuBar1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        menuBar1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        btnInicio = new JMenu();
        btnInicio.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        btnInicio.setBackground(new Color(-1049857));
        btnInicio.setBorderPainted(false);
        btnInicio.setForeground(new Color(-16777216));
        btnInicio.setText("Inicio");
        menuBar1.add(btnInicio, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnAyuda = new JMenu();
        btnAyuda.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        btnAyuda.setBackground(new Color(-1049857));
        btnAyuda.setBorderPainted(false);
        btnAyuda.setForeground(new Color(-16777216));
        btnAyuda.setText("ayuda");
        menuBar1.add(btnAyuda, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        menuBar1.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btnConfiguraciones = new JMenu();
        btnConfiguraciones.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        btnConfiguraciones.setBackground(new Color(-1049857));
        btnConfiguraciones.setBorderPainted(false);
        btnConfiguraciones.setForeground(new Color(-16777216));
        btnConfiguraciones.setText("Configuración");
        menuBar1.add(btnConfiguraciones, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 10, 0, 10), -1, -1));
        panel2.setBackground(new Color(-16777216));
        contentPane.add(panel2, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 28), new Dimension(-1, 28), new Dimension(-1, 28), 0, false));
        lblIzquierdo = new JLabel();
        lblIzquierdo.setForeground(new Color(-1));
        lblIzquierdo.setText("");
        panel2.add(lblIzquierdo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblCentro = new JLabel();
        lblCentro.setForeground(new Color(-1));
        lblCentro.setText("");
        panel2.add(lblCentro, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblDerecha = new JLabel();
        lblDerecha.setForeground(new Color(-1));
        lblDerecha.setText("");
        panel2.add(lblDerecha, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelDeTabPane = new JPanel();
        panelDeTabPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panelDeTabPane, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tabContenido = new DnDTabbedPane();
        tabContenido.setTabLayoutPolicy(1);
        tabContenido.setTabPlacement(1);
        panelDeTabPane.add(tabContenido, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), 0, 0));
        contentPane.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(130, -1), null, 0, false));
        splitPane = new JSplitPane();
        splitPane.setDividerSize(0);
        panel3.add(splitPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 200), null, 0, false));
        panelControles = new JPanel();
        panelControles.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, 1));
        splitPane.setLeftComponent(panelControles);
        inicioButton = new JButton();
        inicioButton.setFocusPainted(false);
        Font inicioButtonFont = this.$$$getFont$$$(null, -1, 16, inicioButton.getFont());
        if (inicioButtonFont != null) inicioButton.setFont(inicioButtonFont);
        inicioButton.setHorizontalAlignment(0);
        inicioButton.setHorizontalTextPosition(0);
        inicioButton.setIcon(new ImageIcon(getClass().getResource("/com/devel/Icons/x32/inicio.png")));
        inicioButton.setIconTextGap(-4);
        inicioButton.setMargin(new Insets(5, 2, 5, 2));
        inicioButton.setPressedIcon(new ImageIcon(getClass().getResource("/com/devel/Icons/x32/inicio.png")));
        inicioButton.setText("Inicio");
        inicioButton.setVerticalAlignment(0);
        inicioButton.setVerticalTextPosition(3);
        panelControles.add(inicioButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        reportesButton = new JButton();
        reportesButton.setFocusPainted(false);
        Font reportesButtonFont = this.$$$getFont$$$(null, -1, 16, reportesButton.getFont());
        if (reportesButtonFont != null) reportesButton.setFont(reportesButtonFont);
        reportesButton.setHorizontalAlignment(0);
        reportesButton.setHorizontalTextPosition(0);
        reportesButton.setIcon(new ImageIcon(getClass().getResource("/com/devel/Icons/x32/reportsDefecto.png")));
        reportesButton.setIconTextGap(-4);
        reportesButton.setMargin(new Insets(5, 2, 5, 2));
        reportesButton.setPressedIcon(new ImageIcon(getClass().getResource("/com/devel/Icons/x32/reportes.png")));
        reportesButton.setText("Reportes");
        reportesButton.setVerticalAlignment(0);
        reportesButton.setVerticalTextPosition(3);
        panelControles.add(reportesButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gestionarButton = new JButton();
        gestionarButton.setFocusPainted(false);
        Font gestionarButtonFont = this.$$$getFont$$$(null, -1, 16, gestionarButton.getFont());
        if (gestionarButtonFont != null) gestionarButton.setFont(gestionarButtonFont);
        gestionarButton.setHorizontalAlignment(0);
        gestionarButton.setHorizontalTextPosition(0);
        gestionarButton.setIcon(new ImageIcon(getClass().getResource("/com/devel/Icons/x32/gestionarDefecto.png")));
        gestionarButton.setIconTextGap(-4);
        gestionarButton.setMargin(new Insets(5, 2, 5, 2));
        gestionarButton.setPressedIcon(new ImageIcon(getClass().getResource("/com/devel/Icons/x32/gestionar.png")));
        gestionarButton.setText("Gestionar");
        gestionarButton.setVerticalAlignment(0);
        gestionarButton.setVerticalTextPosition(3);
        panelControles.add(gestionarButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panelControles.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        setingsButton = new JButton();
        setingsButton.setHorizontalTextPosition(0);
        setingsButton.setIcon(new ImageIcon(getClass().getResource("/com/devel/Icons/x16/ajustes.png")));
        setingsButton.setIconTextGap(1);
        setingsButton.setPressedIcon(new ImageIcon(getClass().getResource("/com/devel/Icons/x16/ajustesSeleccionado.png")));
        setingsButton.setText("Setings");
        setingsButton.setVerticalTextPosition(3);
        panelControles.add(setingsButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button1 = new JButton();
        button1.setHorizontalTextPosition(0);
        button1.setIcon(new ImageIcon(getClass().getResource("/com/devel/Icons/x16/ajustes.png")));
        button1.setIconTextGap(1);
        button1.setPressedIcon(new ImageIcon(getClass().getResource("/com/devel/Icons/x16/ajustesSeleccionado.png")));
        button1.setText("Salir");
        button1.setVerticalAlignment(0);
        button1.setVerticalTextPosition(3);
        panelControles.add(button1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelMenus = new JPanel();
        panelMenus.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane.setRightComponent(panelMenus);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 33), new Dimension(-1, 33), new Dimension(-1, 33), 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("USUARIO:JAVIER");
        panel4.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
