package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilidades;
import com.devel.views.tabs.VAlumnos;
import com.devel.views.tabs.VFamiliares;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuReportes {
    private JButton btnAlumnos;
    private JPanel panelPrincipal;
    private JButton btnFamiliares;
    private JButton btnMatriculas;
    private DnDTabbedPane tabContenido;
    private Propiedades propiedades;
    private VAlumnos alumnos;
    private VFamiliares vFamiliares;

    public MenuReportes(DnDTabbedPane tabContenido){
        this.propiedades=propiedades;
        this.tabContenido=tabContenido;
        btnAlumnos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnFamiliares.setBackground(new JButton().getBackground());
                btnMatriculas.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(btnAlumnos);
                cargarAlumnos();
            }
        });
        btnFamiliares.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAlumnos.setBackground(new JButton().getBackground());
                btnMatriculas.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(btnFamiliares);
                cargarFamiliares();
            }
        });
        btnMatriculas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAlumnos.setBackground(new JButton().getBackground());
                btnFamiliares.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(btnMatriculas);
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
            alumnos.getPanelPrincipal().setOption(btnAlumnos);
            tabContenido.addTab(alumnos.getTitle(), alumnos.getPanelPrincipal().getIcon(),alumnos.getPanelPrincipal());

        }
        tabContenido.setSelectedComponent(alumnos.getPanelPrincipal());
    }

    public void cargarFamiliares() {
        if (vFamiliares == null) {
            vFamiliares = new VFamiliares();
        }
        if (tabContenido.indexOfComponent(vFamiliares.getPanelPrincipal()) == -1) {
            vFamiliares = new VFamiliares();
            vFamiliares.getPanelPrincipal().setOption(btnAlumnos);
            tabContenido.addTab(vFamiliares.getTitle(), vFamiliares.getPanelPrincipal().getIcon(),vFamiliares.getPanelPrincipal());

        }
        tabContenido.setSelectedComponent(vFamiliares.getPanelPrincipal());
    }
}
