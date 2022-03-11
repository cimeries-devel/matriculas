package com.devel.views.menus;

import com.devel.ForResources;
import com.devel.custom.DnDTabbedPane;
import com.devel.custom.TabPanel;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilities;
import com.devel.views.tabs.VAlumnos;
import com.devel.views.tabs.VMatricula;
import com.devel.views.tabs.VWelcome;
import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MenuInicio {
    private JButton inicioButton;
    private JButton alumnosButton;
    private JPanel panelPrincipal;
    private JButton matriculaButton;
    private DnDTabbedPane tabContenido;
    private VWelcome welcome;
    private VAlumnos alumnos;
    private VMatricula matricula;

    public MenuInicio(final DnDTabbedPane tabContenido){
        this.tabContenido=tabContenido;
        welcome=new VWelcome();
        alumnos=new VAlumnos();
        matricula=new VMatricula();
        inicioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarBienvenida();
                alumnosButton.setBackground(new JButton().getBackground());
                matriculaButton.setBackground(new JButton().getBackground());
                Utilities.buttonSelectedOrEntered(inicioButton);
            }
        });
        alumnosButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarAlumnos();
                inicioButton.setBackground(new JButton().getBackground());
                matriculaButton.setBackground(new JButton().getBackground());
                Utilities.buttonSelectedOrEntered(alumnosButton);
            }
        });
        matriculaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarMatricula();
                alumnosButton.setBackground(new JButton().getBackground());
                inicioButton.setBackground(new JButton().getBackground());
                Utilities.buttonSelectedOrEntered(matriculaButton);
            }
        });
    }
    public JPanel traerInicioOpciones() {
        return panelPrincipal;
    }

    public void cargarBienvenida() {
        if (welcome == null) {
            welcome = new VWelcome();
        }
        if (tabContenido.indexOfComponent(welcome.getPanelPrincipal()) == -1) {
            welcome = new VWelcome();
            welcome.getPanelPrincipal().setOption(inicioButton);
            tabContenido.addTab(welcome.getTitle(), welcome.getPanelPrincipal().getIcon(),welcome.getPanelPrincipal());
        }
        tabContenido.setSelectedComponent(welcome.getPanelPrincipal());
        inicioButton.requestFocus();
    }
    public void cargarAlumnos() {
        if (alumnos == null) {
            alumnos = new VAlumnos();
        }
        if (tabContenido.indexOfComponent(alumnos.getPanelPrincipal()) == -1) {
            alumnos = new VAlumnos();
            alumnos.getPanelPrincipal().setOption(alumnosButton);
            tabContenido.addTab(alumnos.getTitle(), alumnos.getPanelPrincipal().getIcon(),alumnos.getPanelPrincipal());

        }
        tabContenido.setSelectedComponent(alumnos.getPanelPrincipal());
    }
    public void cargarMatricula() {
        if (matricula == null) {
            matricula = new VMatricula();
        }
        if (tabContenido.indexOfComponent(matricula.getPanelPrincipal()) == -1) {
            matricula = new VMatricula();
            matricula.getPanelPrincipal().setOption(matriculaButton);
            tabContenido.addTab(matricula.getTitle(),matricula.getPanelPrincipal().getIcon(), matricula.getPanelPrincipal());

        }
        tabContenido.setSelectedComponent(matricula.getPanelPrincipal());
    }

}
