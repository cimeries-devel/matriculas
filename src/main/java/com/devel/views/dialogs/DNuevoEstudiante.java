package com.devel.views.dialogs;
import com.devel.models.*;
import com.devel.utilities.JButoonEditors.*;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.*;
import com.devel.validators.CelularValidator;
import com.devel.validators.DocumentoValidator;
import com.devel.validators.PersonaValidator;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.DatePicker;
import jakarta.validation.ConstraintViolation;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
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
    private JTable tablaSeguros;
    private JButton btnAñadirDocumento;
    private JTable tablaDocumentos;
    private JButton btnAñadirSeguro;
    private Persona persona;
    private FamiliaresAbstractModel familiaresAbstractModel;
    private CelularesAbstractModel celularesAbstractModel;
    private SeguroPersonaAbstractModel segurosAbstractModel;
    private DocumentoAbstractModel documentoAbstractModel;
    private java.util.Date nacimiento;

    public DNuevoEstudiante(Persona persona){
        this.persona=persona;
        iniciarComponentes();
        paraActualizar();
        pickerEdad.addDateChangeListener(dateChangeEvent -> {
            if(pickerEdad.getDate()!=null){
                labelEdad.setText(String.valueOf(Utilidades.calcularaños(Date.valueOf(pickerEdad.getDate()))));
            }
        });
        btnRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                actualizar();
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onCancel();
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
        btnAñadirDocumento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarAgregarDocumento();
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    public DNuevoEstudiante(){
        persona=new Persona();
        iniciarComponentes();
        pickerEdad.addDateChangeListener(dateChangeEvent -> {
            if(pickerEdad.getDate()!=null){
                labelEdad.setText(String.valueOf(Utilidades.calcularaños(Date.valueOf(pickerEdad.getDate()))));
            }
        });
        btnRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                registrar();
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cerrar();
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

        btnAñadirDocumento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarAgregarDocumento();
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        btnAñadirSeguro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarAgregarSeguro();
            }
        });
    }

    private void registrar(){
        String nombres=txtNombres.getText().trim();
        String apellidos= txtApellidos.getText().trim();
        java.util.Date nacimiento= pickerEdad.getDate()==null?null:Utilidades.localDateToDate(pickerEdad.getDate());
        int edad= Integer.parseInt(labelEdad.getText());
        boolean genero= cbbGenero.getSelectedIndex() == 0;
        String codigo=txtCodigo.getText().trim();
        String email=txtEmail.getText().trim();

        persona.setNombres(nombres);
        persona.setApellidos(apellidos);
        persona.setCumpleaños(nacimiento);
        persona.setEdad(edad);
        persona.setGenero(genero);
        persona.setCodigo(codigo);
        persona.setEmail(email);
        persona.setActualizacion(new java.util.Date());
        persona.setCreacion(new java.util.Date());
        PersonaValidator validator = new PersonaValidator();
        Set<ConstraintViolation<Persona>> errors = validator.loadViolations(persona);
        if(errors.isEmpty()){
            if(persona.getDocumentos().isEmpty()){
                Utilidades.sendNotification("Error","Debe registrar al menos un documento de identificación", TrayIcon.MessageType.WARNING);
            } else if(persona.getApoderado()==null){
                Utilidades.sendNotification("Error","Debe registrar un apoderado", TrayIcon.MessageType.WARNING);
            }else{
                for(Celular celular:persona.getCelulares()){
                    celular.guardar();
                }
                persona.guardar();
                for (Relacion relacion:persona.getFamiliaresparaEstudiante()){
                    relacion.getPersona1().getCelulares().get(0).guardar();
                    relacion.getPersona1().guardar();
                    relacion.getPersona1().getDocumentos().get(0).guardar();
                    relacion.guardar();
                }
                for (Documento documento:persona.getDocumentos()){
                    documento.guardar();
                }
                persona=new Persona();
                cargarTablas();
                Utilidades.sendNotification("Éxito","Alumno registrado", TrayIcon.MessageType.INFO);
                limpiarControles();
            }
        }else {
            PersonaValidator.mostrarErrores(errors);
        }
    }

    private void actualizar(){
        String nombres=txtNombres.getText().trim();
        String apellidos= txtApellidos.getText().trim();
        java.util.Date nacimiento= pickerEdad.getDate()==null?null:Utilidades.localDateToDate(pickerEdad.getDate());
        int edad= Integer.parseInt(labelEdad.getText());
        boolean genero= cbbGenero.getSelectedIndex() == 0;
        String codigo=txtCodigo.getText().trim();
        String email=txtEmail.getText().trim();

        persona.setNombres(nombres);
        persona.setApellidos(apellidos);
        persona.setCumpleaños(nacimiento);
        persona.setEdad(edad);
        persona.setGenero(genero);
        persona.setCodigo(codigo);
        persona.setEmail(email);

        PersonaValidator validator = new PersonaValidator();
        Set<ConstraintViolation<Persona>> errors = validator.loadViolations(persona);
        if(errors.isEmpty()){
            if(persona.getDocumentos().isEmpty()){
                Utilidades.sendNotification("Error","Debe registrar al menos un documento de identificación", TrayIcon.MessageType.WARNING);
            } else if(persona.getApoderado()==null){
                Utilidades.sendNotification("Error","Debe registrar un apoderado", TrayIcon.MessageType.WARNING);
            }else{
                persona.guardar();
            }
            Utilidades.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            limpiarControles();
        }else {
            PersonaValidator.mostrarErrores(errors);
        }
    }

    private void iniciarComponentes(){
        setTitle("Nuevo estudiante");
        setContentPane(panelPrincipal);
        cargarComboBox();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        cargarTablas();
    }

    private void paraActualizar(){
        setTitle("Editar Tipo de documento");
        btnRegistrar.setText("Guardar");
        btnHecho.setText("Cancelar");
        guardarCopia();
        cargarEstudiante();
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
    }
    private void cargarTablas(){
        cargarDocumentos();
        cargarCelulares();
        cargarFamiliares();
        cargarSeguros();
    }
    private void guardarCopia(){
        txtNombres.setName(persona.getNombres());
        txtApellidos.setName(persona.getApellidos());
        txtCodigo.setName(persona.getCodigo());
        txtEmail.setName(persona.getEmail());
        txtDni.setName(persona.getDocumentos().get(0).getNumero());
        labelEdad.setName(String.valueOf(persona.getEdad()));
        nacimiento=persona.getCumpleaños();
    }

    private void onCancel(){
        persona.setNombres(txtNombres.getName());
        persona.setApellidos(txtApellidos.getName());
        persona.setCodigo(txtCodigo.getName());
        persona.setEmail(txtEmail.getName());
        persona.setCumpleaños(nacimiento);
        persona.setEdad(Utilidades.calcularaños(nacimiento));
        cerrar();
    }

    private void limpiarControles(){
        txtDni.setText(null);
        txtCodigo.setText(null);
        txtNombres.setText(null);
        txtApellidos.setText(null);
        labelEdad.setText(null);
        txtEmail.setText(null);
    }

    private void cargarComboBox(){
        cbbTipoDocumento.setModel(new DefaultComboBoxModel((Vector) VPrincipal.tipoDocumentos));
        cbbTipoDocumento.setRenderer(new TipoDocumento.ListCellRenderer());
        cbbSeguro.setModel(new DefaultComboBoxModel((Vector) VPrincipal.seguros));
        cbbSeguro.setRenderer(new Seguro.ListCellRenderer());
    }

    private void cargarAgregarFamiliar(){
        DAñadirFamiliar dAñadirFamiliar=new DAñadirFamiliar(persona);
        dAñadirFamiliar.setVisible(true);
        cargarFamiliares();
    }

    private void cargarAgregarCelular(){
        DAñadirCelular dañadirCelular=new DAñadirCelular(persona);
        dañadirCelular.setVisible(true);
        cargarCelulares();
    }
    private void cargarAgregarSeguro(){
        persona.getSeguros().add((Seguro) cbbSeguro.getSelectedItem());
        cargarSeguros();
    }
    private void cargarAgregarDocumento(){
        Documento documento=new Documento();
        documento.setTypeDocument((TipoDocumento) cbbTipoDocumento.getSelectedItem());
        documento.setNumero(txtDni.getText().trim());
        documento.setPerson(persona);
        DocumentoValidator validator=new DocumentoValidator();
        Set<ConstraintViolation<Documento>> errors = validator.loadViolations(documento);
        if(errors.isEmpty()){
            persona.getDocumentos().add(documento);
            cargarDocumentos();
            Utilidades.sendNotification("Éxito","Documento registrado", TrayIcon.MessageType.INFO);
            txtDni.setText(null);
        }else {
            DocumentoValidator.mostrarErrores(errors);
        }
    }

    private void cargarDocumentos(){
        documentoAbstractModel=new DocumentoAbstractModel(persona.getDocumentos());
        tablaDocumentos.setModel(documentoAbstractModel);
        tablaDocumentos.getColumnModel().getColumn(documentoAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorDocumentos(persona,tablaDocumentos,"eliminar"));
        tablaDocumentos.getColumnModel().getColumn(documentoAbstractModel.getColumnCount()-2).setCellEditor(new JButtonEditorDocumentos(persona,tablaDocumentos,"editar"));
        TableCellRenderer renderer1=tablaDocumentos.getDefaultRenderer(JButton.class);
        tablaDocumentos.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.cellsRendered(tablaDocumentos);
        Utilidades.headerNegrita(tablaDocumentos);

    }

    private void cargarSeguros(){
        segurosAbstractModel=new SeguroPersonaAbstractModel(persona.getSeguros());
        tablaSeguros.setModel(segurosAbstractModel);
        tablaSeguros.getColumnModel().getColumn(segurosAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorSeguros(tablaSeguros,persona));
        TableCellRenderer renderer1=tablaCelulares.getDefaultRenderer(JButton.class);
        tablaCelulares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.headerNegrita(tablaSeguros);
        Utilidades.cellsRendered(tablaSeguros);
    }

    private void cargarCelulares(){
        celularesAbstractModel=new CelularesAbstractModel(persona.getCelulares());
        tablaCelulares.setModel(celularesAbstractModel);
        tablaCelulares.getColumnModel().getColumn(celularesAbstractModel.getColumnCount()-1).setCellEditor(new JButtonEditorCelulares(tablaCelulares));
        TableCellRenderer renderer1=tablaCelulares.getDefaultRenderer(JButton.class);
        tablaCelulares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.definirTamaño(tablaCelulares.getColumn(""),30);
        Utilidades.definirTamaño(tablaCelulares.getColumn("Número"),120);
        Utilidades.headerNegrita(tablaCelulares);
        Utilidades.cellsRendered(tablaCelulares);
    }

    private void cargarFamiliares(){
        Vector<Relacion> relaciones=new Vector<>(persona.getFamiliaresparaEstudiante());
        familiaresAbstractModel=new FamiliaresAbstractModel(relaciones);
        tablaFamiliares.setModel(familiaresAbstractModel);
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonEditorFamiliares(relaciones,tablaFamiliares,"editar"));
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 2).setCellEditor(new JButtonEditorFamiliares(relaciones,tablaFamiliares,"apoderado"));
        TableCellRenderer renderer1=tablaFamiliares.getDefaultRenderer(JButton.class);
        tablaFamiliares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        tablaFamiliares.removeColumn(tablaFamiliares.getColumn("Dirección"));
        Utilidades.definirTamaño(tablaFamiliares.getColumn("Apoderado"),70);
        Utilidades.headerNegrita(tablaFamiliares);
        Utilidades.cellsRendered(tablaFamiliares,relaciones,true);
    }

    private void cerrar(){
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        pickerEdad =new DatePicker();
        pickerEdad.getComponentDateTextField().setBorder(new JTextField().getBorder());
        pickerEdad.getComponentDateTextField().setHorizontalAlignment(JTextField.CENTER);
        pickerEdad.getComponentDateTextField().setBackground(new JTextField().getBackground());
        pickerEdad.getSettings().setFormatForDatesCommonEra("dd-MM-yyyy");
    }
}
