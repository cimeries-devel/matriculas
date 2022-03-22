package com.devel.views.tabs;

import com.devel.Principal;
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
import com.devel.validators.RegistroValidator;
import com.devel.views.VPrincipal;
import com.devel.views.dialogs.DAñadirCelular;
import com.devel.views.dialogs.DAñadirFamiliar;
import com.devel.views.dialogs.DAñadirEstudiante;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.Vector;

public class VMatricula extends JFrame{
    private TabPanel panelPrincipal;
    private JTable tablaFamiliares;
    private JTable tablaMatriculas;
    private JComboBox cbbNiveles;
    private JTextField txtDni;
    private JButton buscarButton;
    private JButton btnNuevoEstudiante;
    private JButton btnRegistrarMatricula;
    private JButton btnAñadirFamiliar;
    private JLabel lblNombres;
    private JCheckBox matriculadoCheckBox;
    private JLabel lblCodigo;
    private JTable tablaCelulares;
    private JButton btnAñadirCelular;
    private JComboBox cbbGrados;
    private JComboBox cbbTarifas;
    private JComboBox cbbSalones;
    private JComboBox cbbSecciones;
    private JLabel lblMonto;
    private FamiliaresAbstractModel familiaresAbstractModel;
    private DateFormat año=new SimpleDateFormat("yyyy");
    private MatriculasAbstractModel matriculadosAbstractModel;
    private CelularesAbstractModel modelCelulares;
    private NumberFormat sol = NumberFormat.getCurrencyInstance();
    private Persona persona;
    private Registro registro;

    public VMatricula() {
        persona=new Persona();
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
                registrarMatricula();
            }
        });
    }
    public TabPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    private void cargarNuevoEstudiante(){
        DAñadirEstudiante dAñadirEstudiante =new DAñadirEstudiante();
        dAñadirEstudiante.setVisible(true);
    }
    private void cargarAgregarFamiliar(){
        DAñadirFamiliar dAñadirFamiliar=new DAñadirFamiliar(persona);
        dAñadirFamiliar.setVisible(true);
        Utilidades.actualizarTabla(tablaFamiliares);
    }
    private void cargarMonto(){
        if(cbbTarifas.getItemCount()>0){
            if(cbbTarifas.getSelectedItem()!=null){
                lblMonto.setText(sol.format(((Tarifa) cbbTarifas.getSelectedItem()).getPrecio()));
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
                    cargarTablasDelEstudiante();
                    habilitarBotones();
                }else{
                    persona=new Persona();
                    lblNombres.setText("--");
                    lblCodigo.setText("--");
                    cargarTablasDelEstudiante();
                    deshabilitarBotones();
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
        if(!matriculadoCheckBox.isSelected()){
            registro=new Registro();
            Tarifa tarifa=(Tarifa) cbbTarifas.getSelectedItem();
            Salon salon=(Salon) cbbSalones.getSelectedItem();

            registro.setCreacion(new Date());
            registro.setActualizacion(new Date());
            registro.setEstudiante(persona);
            registro.setSalon(salon);
            registro.setTarifa(tarifa);

            Set<ConstraintViolation<Registro>> errors = RegistroValidator.loadViolations(registro);
            if(errors.isEmpty()){
                registro.guardar();
                persona.getRegistros().add(registro);
                VPrincipal.alumnosMatriculados.add(registro);
                Utilidades.actualizarTabla(tablaMatriculas);
                registro=new Registro();
                persona=new Persona();
                limpiarControles();
                Utilidades.sendNotification("Éxito","Matricula registrada", TrayIcon.MessageType.INFO);
            }else {
                RegistroValidator.mostrarErrores(errors);
            }
        }else{
            Utilidades.sendNotification("Error","El alumno ya se encuentra matriculado", TrayIcon.MessageType.ERROR);
        }

    }
    private void verificarMatricula(){
        if(!persona.getRegistros().isEmpty()){
            Registro registro=persona.getRegistros().get(persona.getRegistros().size()-1);
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
        setTitle("Matrícula");
        panelPrincipal.setIcon(new ImageIcon(Principal.class.getResource("Icons/x24/inicio.png")));
        cargarMatriculas();
        cargarComboBox();
        cargarTarifaPorDefecto();
        cargarGradosPorNivel();
        cargarTablasDelEstudiante();
        deshabilitarBotones();
    }
    private void cargarTablasDelEstudiante(){
        cargarTablaFamiliares();
        cargarTablaCelulares();
        verificarMatricula();
    }
    private void cargarTarifaPorDefecto(){
        if(!VPrincipal.tarifas.isEmpty()){
            cbbTarifas.setSelectedItem(Tarifas.tarifaActiva());
            lblMonto.setText(sol.format(((Tarifa) cbbTarifas.getSelectedItem()).getPrecio()));
        }
    }
    private void cargarComboBox(){
        cbbNiveles.setModel(new DefaultComboBoxModel(VPrincipal.niveles));
        cbbNiveles.setRenderer(new Nivel.ListCellRenderer());
        cbbGrados.setModel(new DefaultComboBoxModel( VPrincipal.grados));
        cbbGrados.setRenderer(new Grado.ListCellRenderer());
        cbbTarifas.setModel(new DefaultComboBoxModel( VPrincipal.tarifas));
        cbbTarifas.setRenderer(new Tarifa.ListCellRenderer());
        cbbSecciones.setModel(new DefaultComboBoxModel( VPrincipal.secciones));
        cbbSecciones.setRenderer(new Seccion.ListCellRenderer());
        cbbSalones.setModel(new DefaultComboBoxModel( VPrincipal.salones));
        cbbSalones.setRenderer(new Salon.ListCellRenderer());
    }
    private void cargarMatriculas(){
        matriculadosAbstractModel=new MatriculasAbstractModel(VPrincipal.alumnosMatriculados);
        tablaMatriculas.setModel(matriculadosAbstractModel);
        Utilidades.cellsRendered(tablaMatriculas);
        Utilidades.headerNegrita(tablaMatriculas);
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
    private void limpiarControles(){
        txtDni.setText(null);
        lblCodigo.setText("--");
        lblNombres.setText("--");
        cargarTablasDelEstudiante();
        deshabilitarBotones();
    }

    private void deshabilitarBotones(){
        btnRegistrarMatricula.setEnabled(false);
        btnAñadirFamiliar.setEnabled(false);
        btnAñadirCelular.setEnabled(false);
    }

    private void habilitarBotones(){
        btnRegistrarMatricula.setEnabled(true);
        btnAñadirFamiliar.setEnabled(true);
        btnAñadirCelular.setEnabled(true);
    }
}
