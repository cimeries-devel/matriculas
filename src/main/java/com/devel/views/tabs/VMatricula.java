package com.devel.views.tabs;

import com.devel.ForResources;
import com.devel.controllers.Documentos;
import com.devel.controllers.Tarifas;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton btnRegistrarMatricula;
    private JButton btnAñadirFamiliar;
    private JLabel lblNombres;
    private JCheckBox matriculadoCheckBox;
    private JTextField txtMonto;
    private JLabel lblCodigo;
    private JTable tablaCelulares;
    private JButton btnAñadirCelular;
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
        btnNuevoEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarNuevoEstudiante();
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarAlumno();
            }
        });
        cbbNiveles.addActionListener(evt -> {
            cargarNiveles();
        });
        cbbTarifas.addActionListener(e -> {
            cargarMonto();
        });
        btnAñadirFamiliar.addActionListener(e -> {
            cargarAgregarFamiliar();
        });
        btnAñadirCelular.addActionListener(e -> {
            cargarAgregarCelular();
        });
        matriculadoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarMatricula();
            }
        });
        btnRegistrarMatricula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
        Utilidades.actualizarTabla(tablaFamiliares);
    }
    private void cargarMonto(){
        if(cbbTarifas.getItemCount()>0){
            if(cbbTarifas.getSelectedItem()!=null){
                txtMonto.setText(sol.format(((Tarifa) cbbTarifas.getSelectedItem()).getPrecio()));
            }
        }
    }
    private void cargarNiveles(){
        if(cbbNiveles.getItemCount()>0){
            if(cbbNiveles.getSelectedItem()!=null){
                cargarGradosPorNivel();
            }
        }
    }
    private void buscarAlumno(){
        if(txtDni.getText().length()>=8){
            Documento dni= Documentos.getByDni(txtDni.getText().trim());
            if(dni!=null){
                if(dni.getPerson().getCodigo()!=null){
                    persona=dni.getPerson();
                    lblNombres.setText(persona.getNombres()+" "+persona.getApellidos());
                    lblCodigo.setText(persona.getCodigo());
                    cargarTablaFamiliares();
                    cargarTablaCelulares();
                    definirColumnas();
                    verificarMatricula();
                    btnAñadirCelular.setEnabled(true);
                    btnAñadirFamiliar.setEnabled(true);
                    btnRegistrarMatricula.setEnabled(true);
                }else{
                    persona=new Persona();
                    lblNombres.setText("--");
                    lblCodigo.setText("--");
                    cargarTablaFamiliares();
                    cargarTablaCelulares();
                    definirColumnas();
                    btnAñadirCelular.setEnabled(false);
                    btnAñadirFamiliar.setEnabled(false);
                    btnRegistrarMatricula.setEnabled(false);
                    Utilidades.sendNotification("Error","No es estudiante", TrayIcon.MessageType.ERROR);
                }
            }else{
                Utilidades.sendNotification("No hay datos","Alumno no encontrado", TrayIcon.MessageType.INFO);
                btnAñadirFamiliar.removeAll();
            }
        }else{
            Utilidades.sendNotification("Error","Ingrese el dni", TrayIcon.MessageType.ERROR);
        }
    }
    private void registrarMatricula() {

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
        if(cbbNiveles.getSelectedItem()!=null){
            cbbGrados.setModel(new DefaultComboBoxModel(new Vector(((Nivel)cbbNiveles.getSelectedItem()).getGrados())));
            cbbGrados.setRenderer(new Grado.ListCellRenderer());
        }
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
        cargarTarifaPorDefecto();
        cargarGradosPorNivel();
    }
    private void cargarTarifaPorDefecto(){
        if(!VPrincipal.tarifas.isEmpty()){
            cbbTarifas.setSelectedItem(Tarifas.tarifaActiva());
            txtMonto.setText(sol.format(((Tarifa) cbbTarifas.getSelectedItem()).getPrecio()));
        }
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
        familiaresAbstractModel=new FamiliaresAbstractModel(persona.getFamiliaresparaEstudiante());
        tablaFamiliares.setModel(familiaresAbstractModel);
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonEditorFamiliares(persona.getFamiliaresparaEstudiante(),tablaFamiliares,"editar"));
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 2).setCellEditor(new JButtonEditorFamiliares(persona.getFamiliaresparaEstudiante(),tablaFamiliares,"apoderado"));
        TableCellRenderer renderer1=tablaFamiliares.getDefaultRenderer(JButton.class);
        tablaFamiliares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.cellsRendered(tablaFamiliares,persona.getFamiliaresparaEstudiante(),true);
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
        Utilidades.actualizarTabla(tablaCelulares);
    }
    private void definirColumnas(){
        tablaFamiliares.removeColumn(tablaFamiliares.getColumn("Dirección"));
    }
}
