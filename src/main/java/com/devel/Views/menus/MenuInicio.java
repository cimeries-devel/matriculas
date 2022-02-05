package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;
import com.devel.views.tabs.VAlumnos;
import com.devel.views.tabs.VMatricula;
import com.devel.views.tabs.VWelcome;

import javax.swing.*;
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
            }
        });
        alumnosButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarAlumnos();
            }
        });
        matriculaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarMatricula();
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
            tabContenido.add(welcome.getTitle(), welcome.getPanelPrincipal());
        }
        tabContenido.setSelectedComponent(welcome.getPanelPrincipal());
    }
    public void cargarAlumnos() {
        if (alumnos == null) {
            alumnos = new VAlumnos();
        }
        if (tabContenido.indexOfComponent(alumnos.getPanelPrincipal()) == -1) {
            alumnos = new VAlumnos();
            tabContenido.add(alumnos.getTitle(), alumnos.getPanelPrincipal());
        }
        tabContenido.setSelectedComponent(alumnos.getPanelPrincipal());
    }
    public void cargarMatricula() {
        if (matricula == null) {
            matricula = new VMatricula();
        }
        if (tabContenido.indexOfComponent(matricula.getPanelPrincipal()) == -1) {
            matricula = new VMatricula();
            tabContenido.add(matricula.getTitle(), matricula.getPanelPrincipal());
        }
        tabContenido.setSelectedComponent(matricula.getPanelPrincipal());
    }

}
