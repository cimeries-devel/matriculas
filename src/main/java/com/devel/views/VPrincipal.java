package com.devel.views;

import com.devel.controllers.Registros;
import com.devel.controllers.Seguros;
import com.devel.controllers.TipoDcoumentos;
import com.devel.custom.DnDTabbedPane;
import com.devel.models.Registro;
import com.devel.models.Seguro;
import com.devel.models.TipoDocumento;
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
    private JPanel contentPane;
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
    private VWelcome welcome;
    private MenuInicio inicioOpciones=new MenuInicio(tabContenido);
    private MenuReportes menuReportes=new MenuReportes(tabContenido);
    private MenuGestiones menuGestiones=new MenuGestiones(tabContenido);
    public static Vector<TipoDocumento> tipoDocumentos=new Vector<>(TipoDcoumentos.getTodos());
    public static Vector<Registro> alumnosMatriculados=new Vector<>(Registros.getMatriculados());
    public static Vector<Seguro> seguros=new Vector<>(Seguros.todos());
    public static Vector<Seguro> segurosConTodos=new Vector<>(Seguros.todosconTodos());
    public VPrincipal(){
        setContentPane(contentPane);
        setTitle("Gestión matrículas");
        setSize(new Dimension(700, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        inicioOpciones.cargarBienvenida();
        splitPane.setRightComponent(null);
        splitPane.setRightComponent(inicioOpciones.traerInicioOpciones());
        contentPane.updateUI();
        inicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splitPane.setRightComponent(null);
                splitPane.setRightComponent(inicioOpciones.traerInicioOpciones());
                contentPane.updateUI();
            }
        });
        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splitPane.setRightComponent(null);
                splitPane.setRightComponent(menuReportes.traerReportesOpciones());
                contentPane.updateUI();
            }
        });
        gestionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
    }
    private void cargarConfiguraciones(){
        ConfigSistema configSistema=new ConfigSistema();
        configSistema.setVisible(true);
    }
    private void createUIComponents(){
        tabContenido=new DnDTabbedPane();
    }
}
