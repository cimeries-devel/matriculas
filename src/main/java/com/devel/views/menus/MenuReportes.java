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
                Utilities.buttonSelectedOrEntered(alumnosButton);
            }
        });
        padresButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                alumnosButton.setBackground(new JButton().getBackground());
                matriculasButton.setBackground(new JButton().getBackground());
                alumnosPersonalizadoButton.setBackground(new JButton().getBackground());
                Utilities.buttonSelectedOrEntered(padresButton);
            }
        });
        matriculasButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                alumnosButton.setBackground(new JButton().getBackground());
                padresButton.setBackground(new JButton().getBackground());
                alumnosPersonalizadoButton.setBackground(new JButton().getBackground());
                Utilities.buttonSelectedOrEntered(matriculasButton);
            }
        });
        alumnosPersonalizadoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                alumnosButton.setBackground(new JButton().getBackground());
                padresButton.setBackground(new JButton().getBackground());
                matriculasButton.setBackground(new JButton().getBackground());
                Utilities.buttonSelectedOrEntered(alumnosPersonalizadoButton);
            }
        });
    }

    public JPanel traerReportesOpciones() {
        return panelPrincipal;
    }
}
