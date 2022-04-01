package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilidades;
import com.devel.views.tabs.VAlumnos;
import com.devel.views.tabs.VMatricula;
import com.devel.views.tabs.VWelcome;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MenuInicio {
    private JButton inicioButton;
    private JPanel panelPrincipal;
    private JButton matriculaButton;
    private DnDTabbedPane tabContenido;
    private VWelcome welcome;
    private VAlumnos alumnos;
    private VMatricula matricula;
    private Propiedades propiedades;

    public MenuInicio(final DnDTabbedPane tabContenido){
        this.tabContenido=tabContenido;
        inicioButton.addActionListener(e -> {
            cargarBienvenida();
            matriculaButton.setBackground(new JButton().getBackground());
            Utilidades.buttonSelectedOrEntered(inicioButton);
        });
        matriculaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarMatricula();
                inicioButton.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(matriculaButton);
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
