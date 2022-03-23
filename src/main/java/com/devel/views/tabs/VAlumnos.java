package com.devel.views.tabs;

import com.devel.Principal;
import com.devel.custom.TabPanel;
import com.devel.models.*;
import com.devel.utilities.JButoonEditors.JButtonActionAlumnos;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.AlumnosAbstractModel;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class VAlumnos extends JFrame{
    private TabPanel panelPrincipal;
    private JTextField txtCodigo;
    private JCheckBox checkSoloMatriculados;
    private JComboBox cbbSeguros;
    private JButton btnExportar;
    private JButton limpiarFiltrosButton;
    private JTable tablaAlumnos;
    private JComboBox cbbNiveles;
    private JComboBox cbbGrados;
    private JComboBox cbbSecciones;
    private JTextField txtNombres;
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
        txtNombres.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                actualizar();
            }
        });
        checkSoloMatriculados.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                actualizar();
            }
        });
        cbbSeguros.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                actualizar();
            }
        });
        cbbNiveles.addItemListener(new ItemListener()  {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cargarGradosPorNivel();
                actualizar();
            }
        });
        cbbGrados.addItemListener(new ItemListener()  {
            @Override
            public void itemStateChanged(ItemEvent e) {
                actualizar();
            }
        });
        cbbSecciones.addItemListener(new ItemListener()  {
            @Override
            public void itemStateChanged(ItemEvent e) {
                actualizar();
            }
        });
        limpiarFiltrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFiltros();
                actualizar();
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
        cargarComboBox();
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
    private void cargarGradosPorNivel(){
        if(((Nivel)cbbNiveles.getSelectedItem()).getId()!=null){
            cbbGrados.setModel(new DefaultComboBoxModel(new Vector(((Nivel)cbbNiveles.getSelectedItem()).getGradosConTodos())));
            cbbGrados.setRenderer(new Grado.ListCellRenderer());
        }else {
            cbbGrados.setModel(new DefaultComboBoxModel( VPrincipal.gradosConTodos));
            cbbGrados.setRenderer(new Grado.ListCellRenderer());
        }
    }
    private void cargarComboBox(){
        cbbNiveles.setModel(new DefaultComboBoxModel(VPrincipal.nivelesConTodos));
        cbbNiveles.setRenderer(new Nivel.ListCellRenderer());
        cbbGrados.setModel(new DefaultComboBoxModel( VPrincipal.gradosConTodos));
        cbbGrados.setRenderer(new Grado.ListCellRenderer());
        cbbSecciones.setModel(new DefaultComboBoxModel( VPrincipal.seccionesConTodos));
        cbbSecciones.setRenderer(new Seccion.ListCellRenderer());
        cbbSeguros.setModel(new DefaultComboBoxModel( VPrincipal.segurosConTodos));
        cbbSeguros.setRenderer(new Seguro.ListCellRenderer());
    }

    public void actualizar() {
        filtros.clear();
        if (!txtCodigo.getText().trim().isBlank()) {
            String busqueda = txtCodigo.getText().trim().toUpperCase();
            filtros.add(RowFilter.regexFilter(busqueda,0));
        }
        if (!txtNombres.getText().trim().isBlank()) {
            String busqueda = txtNombres.getText().trim().toUpperCase();
            filtros.add(RowFilter.regexFilter(busqueda,1));
            System.out.println(busqueda);
        }
        if(checkSoloMatriculados.isSelected()){
            String año=Utilidades.año.format(new Date());
            filtros.add(RowFilter.regexFilter(año,7));
        }
        if(((Seguro)cbbSeguros.getSelectedItem()).getId()!=null){
            Seguro seguro= (Seguro) cbbSeguros.getSelectedItem();
            filtros.add(RowFilter.regexFilter(seguro.getCodigo(),3));
        }
        if(((Nivel)cbbNiveles.getSelectedItem()).getId()!=null){
            Nivel nivel= (Nivel) cbbNiveles.getSelectedItem();
            filtros.add(RowFilter.regexFilter(nivel.getDescripcion(),4));
        }
        if(((Grado)cbbGrados.getSelectedItem()).getId()!=null){
            Grado grado= (Grado) cbbGrados.getSelectedItem();
            filtros.add(RowFilter.regexFilter(grado.getGrado(),5));
        }
        if(((Seccion)cbbSecciones.getSelectedItem()).getId()!=null){
            Seccion seccion=(Seccion) cbbSecciones.getSelectedItem();
            filtros.add(RowFilter.regexFilter(seccion.getSeccion(),6));
        }
        filtroand = RowFilter.andFilter(filtros);
        modeloOrdenado.setRowFilter(filtroand);
    }

    private void limpiarFiltros(){
        txtNombres.setText(null);
        txtCodigo.setText(null);
        checkSoloMatriculados.setSelected(false);
        cbbSeguros.setSelectedIndex(0);
        cbbNiveles.setSelectedIndex(0);
        cbbGrados.setSelectedIndex(0);
        cbbSecciones.setSelectedIndex(0);
    }
}

