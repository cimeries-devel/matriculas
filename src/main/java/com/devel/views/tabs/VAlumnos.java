package com.devel.views.tabs;

import com.devel.Principal;
import com.devel.controllers.Personas;
import com.devel.custom.TabPanel;
import com.devel.models.Persona;
import com.devel.models.Registro;
import com.devel.utilities.JButoonEditors.JButtonActionAlumnos;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.AlumnosAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class VAlumnos extends JFrame{
    private TabPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscarPorCodigo;
    private JCheckBox soloMatriculadosCheckBox;
    private JComboBox cbbSeguros;
    private JButton btnExportar;
    private JButton limpiarFiltrosButton;
    private JTable tablaAlumnos;
    private JComboBox cbbNiveles;
    private JComboBox cbbGrados;
    private JComboBox cbbSecciones;
    private JPanel txtNombres;
    private JButton btnBuscarPorNombres;
    private AlumnosAbstractModel matriculasAbstractModel;
    private TableRowSorter<AlumnosAbstractModel> modeloOrdenado;
    private List<RowFilter<AlumnosAbstractModel, String>> filtros = new ArrayList<>();
    private RowFilter filtroand;
    public VAlumnos() {
        iniciarComponentes();
        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                actualizar();
            }
        });
        btnBuscarPorNombres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public TabPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    private void iniciarComponentes(){
        setTitle("Alumnos");
        panelPrincipal.setIcon(new ImageIcon(Principal.class.getResource("Icons/x24/inicio.png")));
        cargarAlumnos(VPrincipal.alumnos);
    }
    private void cargarAlumnos(Vector<Persona> alumnos){
        matriculasAbstractModel=new AlumnosAbstractModel(alumnos);
        tablaAlumnos.setModel(matriculasAbstractModel);
        tablaAlumnos.getColumnModel().getColumn(matriculasAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonActionAlumnos(tablaAlumnos));
        modeloOrdenado=new TableRowSorter<>(matriculasAbstractModel);
        tablaAlumnos.setRowSorter(modeloOrdenado);
        TableCellRenderer renderer1=tablaAlumnos.getDefaultRenderer(JButton.class);
        tablaAlumnos.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.cellsRendered(tablaAlumnos);
        Utilidades.headerNegrita(tablaAlumnos);
    }

    public void actualizar() {
        filtros.clear();
        if (!txtCodigo.getText().trim().isEmpty()) {
            String busqueda = txtCodigo.getText().trim();
            filtros.add(RowFilter.regexFilter(busqueda,0));
        }
        filtroand = RowFilter.andFilter(filtros);
        modeloOrdenado.setRowFilter(filtroand);
    }
}

