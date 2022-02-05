package com.devel.views;

import com.devel.custom.DnDTabbedPane;
import com.devel.views.tabs.VWelcome;

import javax.swing.*;
import java.awt.*;

public class VPrincipal extends JFrame{
    private JPanel contentPane;
    private DnDTabbedPane tabContenido;
    private JSplitPane splitPane1;
    private VWelcome welcome;

    public VPrincipal(){
        setContentPane(contentPane);
        setTitle("Gestión matrículas");
        setLocationRelativeTo(null);
        setSize(new Dimension(700, 500));
        cargarBienvenida();
    }
    private void cargarBienvenida() {
        if (welcome == null) {
            welcome = new VWelcome();
        }
        if (tabContenido.indexOfComponent(welcome.getPanelBienvenida()) == -1) {
            welcome = new VWelcome();
            tabContenido.add(welcome.getTitle(), welcome.getPanelBienvenida());
        }
        tabContenido.setSelectedComponent(welcome.getPanelBienvenida());
    }
    private void createUIComponents(){
        tabContenido=new DnDTabbedPane();
    }
}
