package com.devel.views.dialogs;
import com.devel.models.*;
import com.devel.utilities.JButoonEditors.JButtonEditorCelulares;
import com.devel.utilities.JButoonEditors.JButtonEditorFamiliares;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.CelularesAbstractModel;
import com.devel.utilities.modelosTablas.FamiliaresAbstractModel;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Vector;

public class DNuevoEstudiante extends JDialog{
    private JTextField txtNombres;
    private JComboBox cbbTipoDocumento;
    private DatePicker pickerEdad;
    private JLabel labelEdad;
    private JPanel panelPrincipal;
    private JButton btnRegistrar;
    private JButton btnHecho;
    private JTextField txtDni;
    private JTextField txtApellidos;
    private JTextField txtEmail;
    private JTextField txtCodigo;
    private JComboBox cbbGenero;
    private JComboBox cbbSeguro;
    private JButton btnNuevoSeguro;
    private JTabbedPane tabbedPane1;
    private JButton btnNuevoCelular;
    private JTable tablaCelulares;
    private JButton btnNuevoFamiliar;
    private JTable tablaFamiliares;
    private Persona persona;
    private FamiliaresAbstractModel familiaresAbstractModel;
    private CelularesAbstractModel modelCelulares;
    private Documento documento;
    private java.util.Date cumpleaños;
    private Seguro seguro;
    private boolean genero;

    public DNuevoEstudiante(Persona persona){
        iniciarComponentes();
        this.persona=persona;
        paraActualizar();
        pickerEdad.addDateChangeListener(dateChangeEvent -> {
            if(pickerEdad.getDate()!=null){
                labelEdad.setText(String.valueOf(Utilidades.calcularaños(Date.valueOf(pickerEdad.getDate()))));
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onCancel();
            }
        });
        btnNuevoSeguro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DAñadirSeguro añadirSeguro=new DAñadirSeguro();
                añadirSeguro.setVisible(true);
                cbbSeguro.setSelectedIndex(cbbSeguro.getItemCount()-1);
            }
        });
        btnNuevoFamiliar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarAgregarFamiliar();
            }
        });
        btnNuevoCelular.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarAgregarCelular();
            }
        });
    }

    public DNuevoEstudiante(){
        iniciarComponentes();
        persona=new Persona();
        pickerEdad.addDateChangeListener(dateChangeEvent -> {
            if(pickerEdad.getDate()!=null){
                labelEdad.setText(String.valueOf(Utilidades.calcularaños(Date.valueOf(pickerEdad.getDate()))));
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cerrar();
            }
        });
        btnNuevoSeguro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DAñadirSeguro añadirSeguro=new DAñadirSeguro();
                añadirSeguro.setVisible(true);
                cbbSeguro.setSelectedIndex(cbbSeguro.getItemCount()-1);
            }
        });
        btnNuevoFamiliar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        btnNuevoCelular.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarAgregarCelular();
            }
        });
    }
    private void iniciarComponentes(){
        setTitle("Nuevo estudiante");
        setContentPane(panelPrincipal);
        cargarComboBox();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }
    private void paraActualizar(){
        setTitle("Editar Tipo de documento");
        btnRegistrar.setText("Guardar");
        btnHecho.setText("Cancelar");

        guardarCopia();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                onCancel();
            }
        });
    }

    private void cargarEstudiante(){
        txtNombres.setText(persona.getNombres());
        txtApellidos.setText(persona.getApellidos());
        txtDni.setText(persona.getDocumentos().get(0).getNumero());
        txtCodigo.setText(persona.getCodigo());
        txtEmail.setText(persona.getEmail());
        pickerEdad.setDate(LocalDate.from(persona.getCumpleaños().toInstant()));
        labelEdad.setText(String.valueOf(persona.getEdad()));
        cbbGenero.setSelectedIndex(persona.isGenero()?0:1);
        cbbSeguro.setSelectedItem(persona.getSeguros().get(0));
        cbbTipoDocumento.setSelectedItem(persona.getDocumentos().get(0));
    }

    private void guardarCopia(){
        txtNombres.setName(persona.getNombres());
        txtApellidos.setName(persona.getApellidos());
        txtCodigo.setName(persona.getCodigo());
        txtEmail.setName(persona.getEmail());
        txtDni.setName(persona.getDocumentos().get(0).getNumero());
        labelEdad.setName(String.valueOf(persona.getEdad()));
        genero=persona.isGenero();
        cumpleaños=persona.getCumpleaños();
        seguro=persona.getSecures().get(0);
        documento=persona.getDocumentos().get(0);
    }

    private void onCancel(){
        persona.setNombres(txtNombres.getName());
        persona.setApellidos(txtApellidos.getName());
        persona.setCodigo(txtCodigo.getName());
        persona.setEmail(txtEmail.getName());
        persona.getDocumentos().get(0).setTypeDocument(documento.getTypeDocument());
        persona.getDocumentos().get(0).setNumero(documento.getNumero());
        persona.setEdad(Integer.parseInt(labelEdad.getName()));
        cerrar();
    }

    private void limpiarControles(){
        txtCodigo.setText(null);
        txtNombres.setText(null);
        txtApellidos.setText(null);
        labelEdad.setText(null);
        txtEmail.setText(null);
    }

    private void cargarComboBox(){
        cbbTipoDocumento.setModel(new DefaultComboBoxModel<>(VPrincipal.tipoDocumentos));
        cbbTipoDocumento.setRenderer(new TipoDocumento.ListCellRenderer());
        cbbSeguro.setModel(new DefaultComboBoxModel(VPrincipal.seguros));
        cbbSeguro.setRenderer(new Seguro.ListCellRenderer());
    }

    private void cargarAgregarFamiliar(){
        DAñadirFamiliar dAñadirFamiliar=new DAñadirFamiliar(persona);
        dAñadirFamiliar.setVisible(true);
        cargarTablaFamiliares(new Vector<>(persona.getRelaciones()));
        definirColumnas();
    }

    private void cargarAgregarCelular(){
        DAñadirCelular dañadirCelular=new DAñadirCelular(persona);
        dañadirCelular.setVisible(true);
        cargarTablaCelulares(new Vector<>(persona.getCelulares()));
        definirColumnas();
    }

    private void cargarTablaCelulares(Vector<Celular> celulares){
        modelCelulares=new CelularesAbstractModel(new Vector<>(persona.getCelulares()));
        tablaCelulares.setModel(modelCelulares);
        tablaCelulares.getColumnModel().getColumn(modelCelulares.getColumnCount()-1).setCellEditor(new JButtonEditorCelulares(tablaCelulares));
        TableCellRenderer renderer1=tablaCelulares.getDefaultRenderer(JButton.class);
        tablaCelulares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.headerNegrita(tablaCelulares);
    }

    private void cargarTablaFamiliares(Vector<Relacion> relaciones){
        familiaresAbstractModel=new FamiliaresAbstractModel(relaciones);
        tablaFamiliares.setModel(familiaresAbstractModel);
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonEditorFamiliares(relaciones,tablaFamiliares,"editar"));
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 2).setCellEditor(new JButtonEditorFamiliares(relaciones,tablaFamiliares,"apoderado"));
        TableCellRenderer renderer1=tablaFamiliares.getDefaultRenderer(JButton.class);
        tablaFamiliares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.headerNegrita(tablaFamiliares);
    }

    private void definirColumnas(){
        tablaFamiliares.removeColumn(tablaFamiliares.getColumn("Dirección"));
        Utilidades.definirTamaño(tablaFamiliares.getColumn("Apoderado"),70);
        Utilidades.definirTamaño(tablaCelulares.getColumn(""),30);
        Utilidades.definirTamaño(tablaCelulares.getColumn("Número"),120);
    }

    private void cerrar(){
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        pickerEdad =new DatePicker();
        pickerEdad.getComponentDateTextField().setBorder(new JTextField().getBorder());
        pickerEdad.getComponentDateTextField().setDisabledTextColor(new JTextField().getForeground());
        int ge=new JTextField().getHeight()+15;
        pickerEdad.setPreferredSize(new Dimension(200,ge));
        pickerEdad.getComponentDateTextField().setPreferredSize(new JTextField().getPreferredSize());
        pickerEdad.getComponentDateTextField().setHorizontalAlignment(JTextField.CENTER);
        pickerEdad.getComponentDateTextField().setBackground(new JTextField().getBackground());
        pickerEdad.getSettings().setFormatForDatesCommonEra("dd-MM-yyyy");
    }
}
