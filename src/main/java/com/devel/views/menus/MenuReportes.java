package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilidades;

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
    private JButton alumnosPersonalizadoButton;
    private DnDTabbedPane tabContenido;
    private Propiedades propiedades;
    public MenuReportes(DnDTabbedPane tabContenido){
        this.propiedades=propiedades;
        this.tabContenido=tabContenido;
        alumnosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                padresButton.setBackground(new JButton().getBackground());
                matriculasButton.setBackground(new JButton().getBackground());
                alumnosPersonalizadoButton.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(alumnosButton);
            }
        });
        padresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alumnosButton.setBackground(new JButton().getBackground());
                matriculasButton.setBackground(new JButton().getBackground());
                alumnosPersonalizadoButton.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(padresButton);
            }
        });
        matriculasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alumnosButton.setBackground(new JButton().getBackground());
                padresButton.setBackground(new JButton().getBackground());
                alumnosPersonalizadoButton.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(matriculasButton);
            }
        });
        alumnosPersonalizadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alumnosButton.setBackground(new JButton().getBackground());
                padresButton.setBackground(new JButton().getBackground());
                matriculasButton.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(alumnosPersonalizadoButton);
            }
        });
    }

    public JPanel traerReportesOpciones() {
        return panelPrincipal;
    }
}
