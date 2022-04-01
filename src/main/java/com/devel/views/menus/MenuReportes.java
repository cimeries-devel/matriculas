package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilidades;
import com.devel.views.tabs.VAlumnos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuReportes {
    private JButton alumnosButton;
    private JPanel panelPrincipal;
    private JButton padresButton;
    private JButton matriculasButton;
    private DnDTabbedPane tabContenido;
    private Propiedades propiedades;
    private VAlumnos alumnos;

    public MenuReportes(DnDTabbedPane tabContenido){
        this.propiedades=propiedades;
        this.tabContenido=tabContenido;
        alumnosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                padresButton.setBackground(new JButton().getBackground());
                matriculasButton.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(alumnosButton);
                cargarAlumnos();
            }
        });
        padresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alumnosButton.setBackground(new JButton().getBackground());
                matriculasButton.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(padresButton);
            }
        });
        matriculasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alumnosButton.setBackground(new JButton().getBackground());
                padresButton.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(matriculasButton);
            }
        });
    }

    public JPanel traerReportesOpciones() {
        return panelPrincipal;
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
}
