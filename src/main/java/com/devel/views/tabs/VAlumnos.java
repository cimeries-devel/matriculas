package com.devel.views.tabs;

import com.devel.custom.TabPanel;
import com.devel.utilities.modelosTablas.AlumnosAbstractModel;
import com.devel.utilities.modelosTablas.MatriculasAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;

public class VAlumnos extends JFrame{
    private TabPanel panelPrincipal;
    private JTextField textField1;
    private JButton buscarButton;
    private JCheckBox soloMatriculadosCheckBox;
    private JComboBox comboBox1;
    private JButton exportarButton;
    private JButton limpiarFiltrosButton;
    private JTable tablaAlumnos;
    private AlumnosAbstractModel matriculasAbstractModel;
    public VAlumnos() {
        setTitle("Alumnos");
    }

    public TabPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    private void iniciarComponentes(){

    }

    private void cargarAlumnos(){
//        matriculasAbstractModel=new AlumnosAbstractModel(VPrincipal)
    }
}

