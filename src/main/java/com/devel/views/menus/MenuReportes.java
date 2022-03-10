package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;
import com.devel.utilities.Utilities;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuReportes {
    private JButton alumnosButton;
    private JPanel panelPrincipal;
    private JButton padresButton;
    private JButton matriculasButton;
    private JButton alumnosPersonalizadoButton;
    private DnDTabbedPane tabContenido;
    public MenuReportes(DnDTabbedPane tabContenido){
        this.tabContenido=tabContenido;
        alumnosButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                padresButton.setBackground(new JButton().getBackground());
                matriculasButton.setBackground(new JButton().getBackground());
                alumnosPersonalizadoButton.setBackground(new JButton().getBackground());
                Utilities.verificarTema(alumnosButton);
            }
        });
        padresButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                alumnosButton.setBackground(new JButton().getBackground());
                matriculasButton.setBackground(new JButton().getBackground());
                alumnosPersonalizadoButton.setBackground(new JButton().getBackground());
                Utilities.verificarTema(padresButton);
            }
        });
        matriculasButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                alumnosButton.setBackground(new JButton().getBackground());
                padresButton.setBackground(new JButton().getBackground());
                alumnosPersonalizadoButton.setBackground(new JButton().getBackground());
                Utilities.verificarTema(matriculasButton);
            }
        });
        alumnosPersonalizadoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                alumnosButton.setBackground(new JButton().getBackground());
                padresButton.setBackground(new JButton().getBackground());
                matriculasButton.setBackground(new JButton().getBackground());
                Utilities.verificarTema(alumnosPersonalizadoButton);
            }
        });
    }

    public JPanel traerReportesOpciones() {
        return panelPrincipal;
    }
}
