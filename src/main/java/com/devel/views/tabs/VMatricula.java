package com.devel.views.tabs;

import com.devel.ForResources;
import com.devel.controllers.Documentos;
import com.devel.custom.TabPanel;
import com.devel.models.*;
import com.devel.utilities.JButoonEditors.JButtonEditorCelulares;
import com.devel.utilities.JButoonEditors.JButtonEditorFamiliares;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.MatriculasAbstractModel;
import com.devel.utilities.modelosTablas.CelularesAbstractModel;
import com.devel.utilities.modelosTablas.FamiliaresAbstractModel;
import com.devel.views.VPrincipal;
import com.devel.views.dialogs.DAñadirCelular;
import com.devel.views.dialogs.DAñadirFamiliar;
import com.devel.views.dialogs.DNuevoEstudiante;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class VMatricula extends JFrame{
    private TabPanel panelPrincipal;
    private JTable tablaFamiliares;
    private JTable tablaMatriculas;
    private JTextField txtEdad;
    private JComboBox cbbNiveles;
    private JTextField txtDni;
    private JButton buscarButton;
    private JButton btnNuevoEstudiante;
    private JButton registrarMatriculaButton;
    private JButton nuevoFamiliarButton;
    private JTextField txtNombres;
    private JCheckBox matriculadoCheckBox;
    private JTextField txtMonto;
    private JTextField txtCodigo;
    private JTable tablaCelulares;
    private JButton añdirCelularButton;
    private JComboBox cbbGrados;
    private JComboBox cbbTarifas;
    private JScrollPane jScrollPane1;
    private FamiliaresAbstractModel familiaresAbstractModel;
    private Persona persona;
    private DateFormat año=new SimpleDateFormat("yyyy");
    private MatriculasAbstractModel matriculadosAbstractModel;
    private CelularesAbstractModel modelCelulares;
    private NumberFormat sol = NumberFormat.getCurrencyInstance();

    public VMatricula() {
        iniciarComponentes();
        btnNuevoEstudiante.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarNuevoEstudiante();
            }
        });
        buscarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                buscarAlumno();
            }
        });
        cbbTarifas.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(cbbTarifas.getItemCount()>0){
                    if(cbbTarifas.getSelectedItem()!=null){
                        txtMonto.setText(sol.format(((Tarifa) cbbTarifas.getSelectedItem()).getPrecio()));
                    }
                }
            }
        });
        cbbNiveles.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(cbbNiveles.getItemCount()>0){
                    if(cbbNiveles.getSelectedItem()!=null){
                        cargarGradosPorNivel();
                    }
                }
            }
        });
    }
    public TabPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    private void cargarNuevoEstudiante(){
        DNuevoEstudiante dNuevoEstudiante=new DNuevoEstudiante();
        dNuevoEstudiante.setVisible(true);
    }
    private void cargarAgregarFamiliar(){
        DAñadirFamiliar dAñadirFamiliar=new DAñadirFamiliar(persona);
        dAñadirFamiliar.setVisible(true);
        cargarTablaFamiliares();
        definirColumnas();
    }
    private void buscarAlumno(){
        if(txtDni.getText().length()>=8){
            Documento dni= Documentos.getByDni(txtDni.getText().trim());
            if(dni!=null){
                if(dni.getPerson().getCodigo()!=null){
                    persona=dni.getPerson();
                    txtNombres.setText(persona.getNombres()+" "+persona.getApellidos());
                    txtCodigo.setText(persona.getCodigo());
                    cargarTablaFamiliares();
                    cargarTablaCelulares();
                    definirColumnas();
                    verificarMatricula();
                    nuevoFamiliarButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            cargarAgregarFamiliar();
                        }
                    });
                    añdirCelularButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            cargarAgregarCelular();
                        }
                    });
                }else{
                    Utilidades.sendNotification("Error","No es estudiante", TrayIcon.MessageType.ERROR);
                }

            }else{
                Utilidades.sendNotification("No hay datos","Alumno no encontrado", TrayIcon.MessageType.INFO);
                nuevoFamiliarButton.removeAll();
            }
        }else{
            Utilidades.sendNotification("Error","Ingrese el dni", TrayIcon.MessageType.ERROR);
        }

    }
    private void verificarMatricula(){
        if(!persona.getRegistros().isEmpty()){
            Registro registro=persona.getRegistros().get(persona.getRegistros().size());
            if(año.format(registro.getActualizacion().getTime()).equals(año.format(new Date()))){
                matriculadoCheckBox.setSelected(true);
            }else{
                matriculadoCheckBox.setSelected(false);
            }
        }else{
            matriculadoCheckBox.setSelected(false);
        }
    }
    private void cargarGradosPorNivel(){
        cbbGrados.setModel(new DefaultComboBoxModel(new Vector(((Nivel)cbbNiveles.getSelectedItem()).getGrados())));
        cbbGrados.setRenderer(new Grado.ListCellRenderer());
    }
    private void iniciarComponentes(){
        persona=new Persona();
        setTitle("Matrícula");
        cargarTablaFamiliares();
        cargarTablaCelulares();
        definirColumnas();
        cargarMatriculas();
        cargarComboBox();
        panelPrincipal.setIcon(new ImageIcon(ForResources.class.getResource("Icons/x24/inicio.png")));
    }
    private void cargarComboBox(){
        cbbNiveles.setModel(new DefaultComboBoxModel(VPrincipal.niveles));
        cbbNiveles.setRenderer(new Nivel.ListCellRenderer());
        cbbGrados.setModel(new DefaultComboBoxModel( VPrincipal.grados));
        cbbGrados.setRenderer(new Grado.ListCellRenderer());
        cbbTarifas.setModel(new DefaultComboBoxModel( VPrincipal.tarifas));
        cbbTarifas.setRenderer(new Tarifa.ListCellRenderer());
    }
    private void cargarMatriculas(){
        matriculadosAbstractModel=new MatriculasAbstractModel(VPrincipal.alumnosMatriculados);
        tablaMatriculas.setModel(matriculadosAbstractModel);
        Utilidades.headerNegrita(tablaMatriculas);
        Utilidades.cellsRendered(tablaMatriculas);

    }
    private void cargarTablaFamiliares( ){
        familiaresAbstractModel=new FamiliaresAbstractModel(persona.getRelaciones());
        tablaFamiliares.setModel(familiaresAbstractModel);
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonEditorFamiliares(persona.getRelaciones(),tablaFamiliares,"editar"));
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 2).setCellEditor(new JButtonEditorFamiliares(persona.getRelaciones(),tablaFamiliares,"apoderado"));
        TableCellRenderer renderer1=tablaFamiliares.getDefaultRenderer(JButton.class);
        tablaFamiliares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.cellsRendered(tablaFamiliares,persona.getRelaciones(),true);
        Utilidades.headerNegrita(tablaFamiliares);
    }
    private void cargarTablaCelulares(){
        modelCelulares=new CelularesAbstractModel(persona.getCelulares());
        tablaCelulares.setModel(modelCelulares);
        tablaCelulares.getColumnModel().getColumn(modelCelulares.getColumnCount()-1).setCellEditor(new JButtonEditorCelulares(tablaCelulares));
        TableCellRenderer renderer1=tablaCelulares.getDefaultRenderer(JButton.class);
        tablaCelulares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.cellsRendered(tablaCelulares);
        Utilidades.headerNegrita(tablaCelulares);
    }
    private void cargarAgregarCelular(){
        DAñadirCelular dañadirCelular=new DAñadirCelular(persona);
        dañadirCelular.setVisible(true);
        cargarTablaCelulares();
        definirColumnas();
    }
    private void definirColumnas(){
        tablaFamiliares.removeColumn(tablaFamiliares.getColumn("Dirección"));
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
