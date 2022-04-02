package com.devel.views.tabs;

import com.devel.Principal;
import com.devel.custom.TabPanel;
import com.devel.models.*;
import com.devel.utilities.JButoonEditors.JButtonActionAlumnos;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.TodosLosFamiliaresAbstractModel;
import com.devel.views.VPrincipal;
import com.devel.views.dialogs.Exportar.ExportarAlumnos;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.util.*;

public class VFamiliares extends JFrame{
    private JPanel panel1;
    private TabPanel panelPrincipal;
    private JTextField txtDni;
    private JTextField txtNombresYApellidos;
    private JButton btnExportar;
    private JTable tablaFamiliares;
    private JCheckBox checkSoloApoderados;
    private JButton limpiarFiltrosButton;
    private JComboBox cbbTipoFamiliares;
    private Map<Integer, String> listaFiltros = new HashMap<Integer, String>();
    private TodosLosFamiliaresAbstractModel todosLosFamiliaresAbstractModel;
    private TableRowSorter<TodosLosFamiliaresAbstractModel> modeloOrdenado;
    private List<RowFilter<TodosLosFamiliaresAbstractModel, String>> filtros = new ArrayList<>();
    private RowFilter filtroand;

    public VFamiliares() {
        iniciarComponentes();
        txtDni.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                actualizar();
            }
        });
        txtNombresYApellidos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                actualizar();
            }
        });
        cbbTipoFamiliares.addItemListener(new ItemListener() {
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
        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarRelacionAlumnos();
            }
        });
        checkSoloApoderados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar();
            }
        });
    }

    public TabPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    private void iniciarComponentes(){
        setTitle("Familiares");
        panelPrincipal.setIcon(new ImageIcon(Principal.class.getResource("Icons/x24/inicio.png")));
        cargarFamiliares(VPrincipal.familiares);
        cargarComboBox();
    }
    private void cargarFamiliares(Vector<Persona> familiares){
        todosLosFamiliaresAbstractModel=new TodosLosFamiliaresAbstractModel(familiares);
        tablaFamiliares.setModel(todosLosFamiliaresAbstractModel);
        tablaFamiliares.getColumnModel().getColumn(todosLosFamiliaresAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonActionAlumnos(tablaFamiliares));
        modeloOrdenado=new TableRowSorter<>(todosLosFamiliaresAbstractModel);
        tablaFamiliares.setRowSorter(modeloOrdenado);
        TableCellRenderer renderer1=tablaFamiliares.getDefaultRenderer(JButton.class);
        tablaFamiliares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.cellsRendered(listaFiltros,tablaFamiliares);
        Utilidades.headerNegrita(tablaFamiliares);
    }

    private void exportarRelacionAlumnos(){
        ExportarAlumnos exportarAlumnos=new ExportarAlumnos(tablaFamiliares);
        exportarAlumnos.setVisible(true);
    }
    private void cargarComboBox(){
        cbbTipoFamiliares.setModel(new DefaultComboBoxModel(VPrincipal.tipoRelacionesConTodos));
        cbbTipoFamiliares.setRenderer(new TipoRelacion.ListCellRenderer());
    }

    public void actualizar() {
        filtros.clear();
        String busqueda;

        busqueda = txtDni.getText().trim().toUpperCase();
        filtros.add(RowFilter.regexFilter(busqueda,0));
        listaFiltros.put(0,busqueda);

        busqueda = txtNombresYApellidos.getText().trim().toUpperCase();
        filtros.add(RowFilter.regexFilter(busqueda,1));
        listaFiltros.put(1,busqueda);

        if(checkSoloApoderados.isSelected()){
            String año=Utilidades.año.format(new Date());
            filtros.add(RowFilter.regexFilter(año,8));
        }
        filtroand = RowFilter.andFilter(filtros);
        modeloOrdenado.setRowFilter(filtroand);
    }

    private void limpiarFiltros(){
        checkSoloApoderados.setSelected(false);
        txtDni.setText(null);
        txtNombresYApellidos.setText(null);
    }
}
