package com.devel.views.dialogs;

import com.devel.controllers.Documentos;
import com.devel.models.*;
import com.devel.utilities.Utilidades;
import com.devel.validators.*;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Set;

public class DAñadirFamiliar extends JDialog{
    private DatePicker datePicker1;
    private JLabel lblEdad;
    private JTextField txtNombres;
    private JButton btnHecho;
    private JTextField txtCelular;
    private JCheckBox ckVivenJuntos;
    private JButton btnAñadir;
    private JPanel panelPrincipal;
    private JComboBox cbbTipoDocumento;
    private JButton btnBuscar;
    private JTextField txtApellidos;
    private JTextField txtDescripcionCelular;
    private JTextField txtDireccion;
    private JTextField txtEmail;
    private JTextField txtRelacion;
    private JTextField txtDni;
    private JComboBox cbbGenero;
    private Persona persona;
    private Persona familiar;

    public DAñadirFamiliar(Persona persona){
        this.persona=persona;
        iniciarComponentes();
        datePicker1.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                if(datePicker1.getDate()!=null){
                    int edad= Utilidades.calcularaños(Date.valueOf(datePicker1.getDate()));
                    lblEdad.setText(String.valueOf(edad));
                }
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cerrar();
            }
        });
        btnAñadir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                registrar();
            }
        });
        btnBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(txtDni.getText().length()==8){
                    buscarPersona();
                }
            }
        });
    }

    public DAñadirFamiliar(Persona persona,Persona familiar){
        this.persona=persona;
        this.familiar=familiar;
        iniciarComponentes();
        datePicker1.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                if(datePicker1.getDate()!=null){
                    int edad= Utilidades.calcularaños(Date.valueOf(datePicker1.getDate()));
                    lblEdad.setText(String.valueOf(edad));
                }
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onCancel();
            }
        });
        btnAñadir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                actualizar();
            }
        });
        btnBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(txtDni.getText().length()==8){
                    buscarPersona();
                }
            }
        });
    }

    private void iniciarComponentes(){
        setTitle("Añadir nuevo familiar");
        setContentPane(panelPrincipal);
        pack();
        setModal(true);
        cargarComboBox();
        setLocationRelativeTo(null);
    }

    private void paraActualizar(){
        setTitle("Editar Familiar");
        btnAñadir.setText("Guardar");
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

    private void registrar(){
        String nombres=txtNombres.getText().trim();
        String apellidos=txtNombres.getText().trim();
        Date cumpleaños=Date.valueOf(datePicker1.getDate());
        String direccion=txtNombres.getText().trim();
        int edad= Integer.parseInt(lblEdad.getText().trim());
        String email=txtEmail.getText().trim();
        boolean genero=cbbGenero.getSelectedIndex() == 0;

        familiar.setNombres(nombres);
        familiar.setApellidos(apellidos);
        familiar.setCumpleaños(cumpleaños);
        familiar.setCreacion(new java.util.Date());
        familiar.setActualizacion(new java.util.Date());
        familiar.setDireccion(direccion);
        familiar.setEdad(edad);
        familiar.setEmail(email);
        familiar.setGenero(genero);

        PersonaValidator validator = new PersonaValidator();
        Set<ConstraintViolation<Persona>> errors = validator.loadViolations(familiar);
        if(errors.isEmpty()){
            Celular celular=new Celular();
            String numero=txtCelular.getText().trim();
            String descCelular=txtDescripcionCelular.getText().trim();

            celular.setNumero(numero);
            celular.setDescipcion(descCelular);

            CelularValidator validator2 = new CelularValidator();
            Set<ConstraintViolation<Celular>> errors2 = validator2.loadViolations(celular);
            if(errors2.isEmpty()){
                Documento documento=new Documento();
                String numeroDocumento=txtDni.getText().trim();
                TipoDocumento tipoDocumento=(TipoDocumento) cbbTipoDocumento.getSelectedItem();

                documento.setTypeDocument(tipoDocumento);
                documento.setNumero(numeroDocumento);
                documento.setPerson(familiar);

                DocumentoValidator validator3 = new DocumentoValidator();
                Set<ConstraintViolation<Documento>> errors3 = validator3.loadViolations(documento);
                if(errors3.isEmpty()){
                    Relacion relacion=new Relacion();
                    String tipoRelacion=txtRelacion.getText().trim();
                    boolean vivenJuntos=ckVivenJuntos.isSelected();
                    boolean esApoderado=persona.getApoderado()==null? true:false;

                    relacion.setTipoRelacion(tipoRelacion);
                    relacion.setVivenJuntos(vivenJuntos);
                    relacion.setPersona(persona);
                    relacion.setPersona1(familiar);
                    relacion.setApoderado(esApoderado);

                    RelacionValidator validator4 = new RelacionValidator();
                    Set<ConstraintViolation<Relacion>> errors4 = validator4.loadViolations(relacion);
                    if(errors4.isEmpty()){
                        persona.getRelaciones().add(relacion);
                        Utilidades.sendNotification("Éxito","Familiar registrado", TrayIcon.MessageType.INFO);
                        limpiarControles();
                    }else {
                        RelacionValidator.mostrarErrores(errors4);
                    }
                }else {
                    DocumentoValidator.mostrarErrores(errors3);
                }
            }else {
                CelularValidator.mostrarErrores(errors2);
            }
        }else {
            PersonaValidator.mostrarErrores(errors);
        }
    }

    private void actualizar(){
        String nombres=txtNombres.getText().trim();
        String apellidos=txtNombres.getText().trim();
        Date cumpleaños=Date.valueOf(datePicker1.getDate());
        String direccion=txtNombres.getText().trim();
        int edad= Integer.parseInt(lblEdad.getText().trim());
        String email=txtEmail.getText().trim();
        boolean genero=cbbGenero.getSelectedIndex() == 0;

        familiar.setNombres(nombres);
        familiar.setApellidos(apellidos);
        familiar.setCumpleaños(cumpleaños);
        familiar.setActualizacion(new java.util.Date());
        familiar.setDireccion(direccion);
        familiar.setEdad(edad);
        familiar.setEmail(email);
        familiar.setGenero(genero);

        PersonaValidator validator = new PersonaValidator();
        Set<ConstraintViolation<Persona>> errors = validator.loadViolations(familiar);
        if(errors.isEmpty()){
            Celular celular=familiar.getCelulares().get(0);
            String numero=txtCelular.getText().trim();
            String descCelular=txtDescripcionCelular.getText().trim();

            celular.setNumero(numero);
            celular.setDescipcion(descCelular);

            CelularValidator validator2 = new CelularValidator();
            Set<ConstraintViolation<Celular>> errors2 = validator2.loadViolations(celular);
            if(errors2.isEmpty()){
                Relacion relacion= persona.getRelacion(familiar);
                String tipoRelacion=txtRelacion.getText().trim();
                boolean vivenJuntos=ckVivenJuntos.isSelected();
                boolean esApoderado=persona.getApoderado()==null? true:false;

                relacion.setTipoRelacion(tipoRelacion);
                relacion.setVivenJuntos(vivenJuntos);
                relacion.setApoderado(esApoderado);

                RelacionValidator validator4 = new RelacionValidator();
                Set<ConstraintViolation<Relacion>> errors4 = validator4.loadViolations(relacion);
                if(errors4.isEmpty()){
                    relacion.guardar();
                    celular.guardar();
                    familiar.guardar();
                    Utilidades.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
                    cerrar();
                }else {
                    RelacionValidator.mostrarErrores(errors4);
                }
            }else {
                CelularValidator.mostrarErrores(errors2);
            }
        }else {
            PersonaValidator.mostrarErrores(errors);
        }
    }

    private void buscarPersona(){
        Documento documento=Documentos.getByDni(txtDni.getText().trim());
        if(documento!=null){
            familiar= documento.getPerson();
            cargarDatos();
        }else{
            DataAPIDNI dataAPIDNI=DataAPIDNI.getDatosDni(txtDni.getText().trim());
            if(dataAPIDNI!=null){
                cargarDatosDataappi(dataAPIDNI);
            }else{
                Utilidades.sendNotification("Sin datos","", TrayIcon.MessageType.INFO);
            }
        }
    }

    private void cargarDatosDataappi(DataAPIDNI dataAPIDNI){
        txtApellidos.setText(dataAPIDNI.getApellido_paterno()+" "+dataAPIDNI.getApellido_materno());
        txtNombres.setText(dataAPIDNI.getNombres());
        txtDireccion.setText(dataAPIDNI.getDireccion_completa()==null?"-":dataAPIDNI.getDireccion_completa());
    }

    private void cargarDatos(){
        DateFormat a=new SimpleDateFormat("yyyy-MM-dd");
        txtNombres.setText(familiar.getNombres());
        txtApellidos.setText(familiar.getApellidos());
        txtDireccion.setText(familiar.getDireccion());
        datePicker1.setDate(LocalDate.parse(a.format(familiar.getCumpleaños())));
        txtEmail.setText(familiar.getEmail());
    }

    private void cargarComboBox(){
        cbbTipoDocumento.setModel(new DefaultComboBoxModel<>(VPrincipal.tipoDocumentos));
        cbbTipoDocumento.setRenderer(new TipoDocumento.ListCellRenderer());
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        datePicker1=new DatePicker();
        datePicker1.getComponentDateTextField().setBorder(new JTextField().getBorder());
        datePicker1.getComponentDateTextField().setEnabled(false);
        datePicker1.getComponentDateTextField().setDisabledTextColor(new JTextField().getForeground());
        datePicker1.setSize(new JTextField().getSize());
    }

    private void guardarCopia(){
        txtNombres.setName(familiar.getNombres());
        txtApellidos.setName(familiar.getApellidos());
        txtEmail.setName(familiar.getEmail());
        txtDireccion.setName(familiar.getDireccion());
        txtRelacion.setName(familiar.getRelacionDeApoderado());
        txtDescripcionCelular.setName(familiar.getCelulares().get(0).getDescipcion());
        txtCelular.setName(familiar.getCelulares().get(0).getNumero());
    }

    private void onCancel(){
        familiar.setNombres(txtNombres.getName());
        familiar.setApellidos(txtApellidos.getName());
        familiar.setEmail(txtEmail.getName());
        familiar.setDireccion(txtDireccion.getName());
        familiar.setRelacionDeApoderado(txtRelacion.getName());
        familiar.getCelulares().get(0).setDescipcion(txtDescripcionCelular.getName());
        familiar.getCelulares().get(0).setNumero(txtCelular.getName());
        cerrar();
    }

    private void cerrar(){
        dispose();
    }

    private void limpiarControles(){
        txtCelular.setText(null);
        txtDni.setText(null);
        txtRelacion.setText(null);
        txtApellidos.setText(null);
        txtNombres.setText(null);
        txtRelacion.setText(null);
        lblEdad.setText(null);
        txtDireccion.setText(null);
        txtDescripcionCelular.setText(null);
        ckVivenJuntos.setSelected(false);
        txtEmail.setText(null);
        datePicker1.getComponentDateTextField().setText(null);
    }
}
