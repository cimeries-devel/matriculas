package com.devel.views.dialogs;

import com.devel.controllers.Documentos;
import com.devel.controllers.TiposRelaciones;
import com.devel.models.*;
import com.devel.utilities.Utilidades;
import com.devel.validators.*;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.DatePicker;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.Set;
import java.util.Vector;

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
    private JComboBox cbbTipoRelación;
    private JButton nuevoButton;
    private Persona persona;
    private Persona familiar;
    private boolean vivenJuntos;

    public DAñadirFamiliar(Persona persona){
        this.persona=persona;
        familiar=new Persona();
        iniciarComponentes();
        datePicker1.addDateChangeListener(dateChangeEvent -> {
            if(datePicker1.getDate()!=null){
                int edad= Utilidades.calcularaños(Date.valueOf(datePicker1.getDate()));
                lblEdad.setText(String.valueOf(edad));
            }
        });
        btnAñadir.addActionListener(e -> {
            registrar();
        });
        btnHecho.addActionListener(e -> {
            cerrar();
        });
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtDni.getText().length()==8){
                    buscarPersona();
                }
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public DAñadirFamiliar(Persona persona,Persona familiar){
        this.persona=persona;
        this.familiar=familiar;
        iniciarComponentes();
        paraActualizar();
        datePicker1.addDateChangeListener(dateChangeEvent -> {
            if(datePicker1.getDate()!=null){
                int edad= Utilidades.calcularaños(Date.valueOf(datePicker1.getDate()));
                lblEdad.setText(String.valueOf(edad));
            }
        });
        btnAñadir.addActionListener(e -> {
            actualizar();
        });
        btnHecho.addActionListener(e -> {
            onCancel();
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void iniciarComponentes(){
        setTitle("Añadir nuevo familiar");
        setContentPane(panelPrincipal);
        pack();
        setModal(true);
        cargarComboBox();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void paraActualizar(){
        setTitle("Editar Familiar");
        btnAñadir.setText("Guardar");
        btnHecho.setText("Cancelar");
        txtDni.setEnabled(false);
        btnBuscar.setEnabled(false);
        cbbTipoDocumento.setEnabled(false);
        txtNombres.setEnabled(false);
        txtApellidos.setEnabled(false);
        cbbGenero.setEnabled(false);
        datePicker1.setEnabled(false);
        guardarCopia();
        cargarFamiliar();
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
        Documento documento;
        Celular celular;
        if(familiar.getId()==null){
            familiar=new Persona();
            documento=new Documento();
            celular=new Celular();
            familiar.getCelulares().add(celular);
            familiar.getDocumentos().add(documento);
        }else{
            documento=familiar.getDocumentos().get(0);
            celular=familiar.getCelulares().get(0);
        }
        String nombres=txtNombres.getText().trim();
        String apellidos=txtApellidos.getText().trim();
        Date cumpleaños=datePicker1.getDate()==null?null:Date.valueOf(datePicker1.getDate());
        String direccion=txtDireccion.getText().trim();
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

        Set<ConstraintViolation<Persona>> errors = PersonaValidator.loadViolations(familiar);
        if(errors.isEmpty()){
            System.out.println("está en celular");
            String numero=txtCelular.getText().trim();
            String descCelular=txtDescripcionCelular.getText().trim();

            celular.setNumero(numero);
            celular.setDescipcion(descCelular);

            Set<ConstraintViolation<Celular>> errors2 = CelularValidator.loadViolations(celular);
            if(errors2.isEmpty()){
                System.out.println("está en documento");
                String numeroDocumento=txtDni.getText().trim();
                TipoDocumento tipoDocumento=(TipoDocumento) cbbTipoDocumento.getSelectedItem();

                documento.setTypeDocument(tipoDocumento);
                documento.setNumero(numeroDocumento);
                documento.setPerson(familiar);

                Set<ConstraintViolation<Documento>> errors3 = DocumentoValidator.loadViolations(documento);
                if(errors3.isEmpty()){
                    System.out.println("está en relacion");
                    if(persona.getRelacionAFamiliar(familiar)==null){
                        if(!persona.existeDocumento(documento.getNumero())){
                            Relacion relacion=new Relacion();
                            boolean vivenJuntos=ckVivenJuntos.isSelected();
                            boolean esApoderado= persona.getApoderado() == null;
                            TipoRelacion tipoRelacion=(TipoRelacion) cbbTipoRelación.getSelectedItem();

                            relacion.setTipoRelacion(tipoRelacion);
                            relacion.setVivenJuntos(vivenJuntos);
                            relacion.setPersona(persona);
                            relacion.setPersona1(familiar);
                            relacion.setApoderado(esApoderado);

                            Set<ConstraintViolation<Relacion>> errors4 = RelacionValidator.loadViolations(relacion);
                            if(errors4.isEmpty()){
                                if(persona.getId()!=null){
                                    celular.guardar();
                                    familiar.guardar();
                                    documento.guardar();
                                    relacion.guardar();
                                    persona.guardar();
                                }
                                persona.getFamiliaresparaEstudiante().add(relacion);
                                familiar.getRelaciones().add(relacion);
                                Utilidades.sendNotification("Éxito","Familiar registrado", TrayIcon.MessageType.INFO);
                                limpiarControles();
                                familiar=new Persona();
                            }else {
                                RelacionValidator.mostrarErrores(errors4);
                            }
                        }else{
                            Utilidades.sendNotification("Error","Es la misma persona",TrayIcon.MessageType.ERROR);
                        }
                    }else{
                        Utilidades.sendNotification("Error","familiar ya registrado", TrayIcon.MessageType.ERROR);
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
        String direccion=txtDireccion.getText().trim();
        String email=txtEmail.getText().trim();

        familiar.setActualizacion(new java.util.Date());
        familiar.setDireccion(direccion);
        familiar.setEmail(email);

        Set<ConstraintViolation<Persona>> errors = PersonaValidator.loadViolations(familiar);
        if(errors.isEmpty()){
            Celular celular=familiar.getCelulares().get(0);
            String numero=txtCelular.getText().trim();
            String descCelular=txtDescripcionCelular.getText().trim();

            celular.setNumero(numero);
            celular.setDescipcion(descCelular);

            Set<ConstraintViolation<Celular>> errors2 = CelularValidator.loadViolations(celular);
            if(errors2.isEmpty()){
                Relacion relacion= familiar.getRelacion(persona);
                boolean vivenJuntos=ckVivenJuntos.isSelected();
                boolean esApoderado=relacion.isApoderado();
                TipoRelacion tipoRelacion=(TipoRelacion) cbbTipoRelación.getSelectedItem();

                relacion.setTipoRelacion(tipoRelacion);
                relacion.setVivenJuntos(vivenJuntos);
                relacion.setApoderado(esApoderado);

                Set<ConstraintViolation<Relacion>> errors4 = RelacionValidator.loadViolations(relacion);
                if(errors4.isEmpty()){
                    if(persona.getId()!=null){
                        familiar.guardar();
                        relacion.guardar();
                        celular.guardar();
                        persona.guardar();
                    }
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
        txtDireccion.setText(dataAPIDNI.getDireccion_completa()==null?"--":dataAPIDNI.getDireccion_completa());
    }

    private void cargarDatos(){
        txtNombres.setText(familiar.getNombres());
        txtApellidos.setText(familiar.getApellidos());
        txtDireccion.setText(familiar.getDireccion());
        txtEmail.setText(familiar.getEmail());
        cbbGenero.setSelectedIndex(familiar.isGenero()?0:1);
        datePicker1.setDate(Utilidades.dateToLocalDate(familiar.getCumpleaños()));
        lblEdad.setText(String.valueOf(familiar.getEdad()));
        txtDescripcionCelular.setText(familiar.getCelulares().get(0).getDescipcion());
        txtCelular.setText(familiar.getCelulares().get(0).getNumero());
    }

    private void cargarComboBox(){
        cbbTipoDocumento.setModel(new DefaultComboBoxModel<>(VPrincipal.tipoDocumentos));
        cbbTipoDocumento.setRenderer(new TipoDocumento.ListCellRenderer());

        cbbTipoRelación.setModel(new DefaultComboBoxModel<>(VPrincipal.tipoRelaciones));
        cbbTipoRelación.setRenderer(new TipoRelacion.ListCellRenderer());
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        datePicker1 =new DatePicker();
        datePicker1.getComponentDateTextField().setBorder(new JTextField().getBorder());
        datePicker1.getComponentDateTextField().setHorizontalAlignment(JTextField.CENTER);
        datePicker1.getComponentDateTextField().setBackground(new JTextField().getBackground());
        datePicker1.getSettings().setFormatForDatesCommonEra("dd-MM-yyyy");
    }

    private void guardarCopia(){
        txtEmail.setName(familiar.getEmail());
        txtDireccion.setName(familiar.getDireccion());
        txtRelacion.setName(String.valueOf(familiar.getTipoRelacion(persona).getId()));
        txtDescripcionCelular.setName(familiar.getCelulares().get(0).getDescipcion());
        txtCelular.setName(familiar.getCelulares().get(0).getNumero());
        vivenJuntos=familiar.getRelacion(persona).isVivenJuntos();
    }
    private void cargarFamiliar(){
        cbbTipoDocumento.setSelectedItem(familiar.getDocumentos().get(0).getTypeDocument());
        txtDni.setText(familiar.getDocumentos().get(0).getNumero());
        txtNombres.setText(familiar.getNombres());
        txtApellidos.setText(familiar.getApellidos());
        txtEmail.setText(familiar.getEmail());
        txtDireccion.setText(familiar.getDireccion());
        cbbTipoRelación.setSelectedItem(familiar.getRelacion(persona).getTipoRelacion());
        txtDescripcionCelular.setText(familiar.getCelulares().get(0).getDescipcion());
        txtCelular.setText(familiar.getCelulares().get(0).getNumero());
        cbbGenero.setSelectedIndex(familiar.isGenero()?0:1);
        ckVivenJuntos.setSelected(familiar.getRelacion(persona).isVivenJuntos());
        datePicker1.setDate(Utilidades.dateToLocalDate(familiar.getCumpleaños()));
        lblEdad.setText(String.valueOf(familiar.getEdad()));
    }
    private void onCancel(){
        familiar.setEmail(txtEmail.getName());
        familiar.setDireccion(txtDireccion.getName());
        familiar.setTipoRelacionRelacion(persona, TiposRelaciones.get(Integer.valueOf(txtRelacion.getName())));
        familiar.getCelulares().get(0).setDescipcion(txtDescripcionCelular.getName());
        familiar.getCelulares().get(0).setNumero(txtCelular.getName());
        familiar.getRelacion(persona).setVivenJuntos(vivenJuntos);
        cerrar();
    }

    private void cerrar(){
        dispose();
    }

    private void limpiarControles(){
        txtCelular.setText(null);
        txtDni.setText(null);
        txtApellidos.setText(null);
        txtNombres.setText(null);
        lblEdad.setText(null);
        txtDireccion.setText(null);
        txtDescripcionCelular.setText(null);
        ckVivenJuntos.setSelected(false);
        txtEmail.setText(null);
        datePicker1.getComponentDateTextField().setText(null);
    }
}
