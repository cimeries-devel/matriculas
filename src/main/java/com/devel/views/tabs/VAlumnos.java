package com.devel.views.tabs;

import javax.swing.*;

public class VAlumnos extends JFrame{
    private JPanel panelPrincipal;
    private JTable tablaAlumnos;
    private JTextField textField1;
    private JButton buscarButton;
    private JCheckBox soloMatriculadosCheckBox;
    private JButton nuevoButton;
    private JComboBox comboBox1;
    private JButton exportarButton;
    private JButton limpiarFiltrosButton;

    public VAlumnos() {
        setTitle("Alumnos");
    }
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
