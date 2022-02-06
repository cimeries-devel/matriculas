package com.devel.views.dialogs;

import com.devel.controllers.Documentos;
import com.devel.controllers.Personas;
import com.devel.models.*;
import com.devel.utilities.Utilities;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DAñadirFamiliar extends JDialog{
    private DatePicker datePicker1;
    private JTextField txtEdad;
    private JTextField txtNombres;
    private JButton hechoButton;
    private JTextField txtCelular;
    private JCheckBox ckVivenJuntos;
    private JButton añadirButton1;
    private JPanel panelPrincipal;
    private JComboBox cbbTipoDocumento;
    private JButton buscarButton;
    private JTextField txtApellidos;
    private JTextField txtDescripcionCelular;
    private JTextField txtDireccion;
    private JTextField txtEmail;
    private JTextField txtRelacion;
    private JTextField txtDni;
    private JComboBox cbbGenero;
    private Persona persona;
    private Vector<String> errores=new Vector<>();

    public DAñadirFamiliar(Persona persona){
        this.persona=persona;
        iniciarComponentes();
        datePicker1.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                Period edad = Period.between(datePicker1.getDate(), LocalDate.now());
                txtEdad.setDisabledTextColor(new JTextField().getForeground());
                txtEdad.setText(String.valueOf(edad.getYears()));
            }
        });
        hechoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
        añadirButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                registrarFamiliar();
            }
        });
    }
    private void iniciarComponentes(){
        setTitle("Añadir nuevo familiar");
        setContentPane(panelPrincipal);
        setModal(true);
        cargarComboBox();
    }
    private void registrarFamiliar(){
        errores.clear();
        if(añadirFamiliar(crearPersona())){
            limpiarControles();
            Utilities.sendNotification("Éxito", "Familiar registrado", TrayIcon.MessageType.INFO);
        }else{
            Utilities.sendNotification("Error", "Ocurrió un error", TrayIcon.MessageType.ERROR);
        }
    }
    private Persona crearPersona(){
        if(txtDni.getText().length()<8){
            errores.add("Documento");
            return null;
        }
        if(Utilities.espacioEnblanco(txtNombres)){
            errores.add("nombres");
            return null;
        }
        if(Utilities.espacioEnblanco(txtApellidos)){
            errores.add("apellidos");
            return null;
        }
        if(Utilities.espacioEnblanco(txtEdad)){
            errores.add("fecha de nacimiento");
            return null;
        }
        if(Utilities.espacioEnblanco(txtRelacion)){
            errores.add("relacion");
            return null;
        }
        Persona familiar;
        Documento doc= Documentos.getByDni(txtDni.getText());
        if(doc!=null){
            familiar= doc.getPerson();
        }else{
            doc=new Documento();
            familiar=new Persona();
        }
        familiar.setNombres(txtNombres.getText().trim());
        familiar.setApellidos(txtApellidos.getText().trim());
        familiar.setCumpleaños(Date.valueOf(datePicker1.getDate()));
        familiar.setCreacion(new java.util.Date());
        familiar.setActualizacion(new java.util.Date());
        familiar.setDireccion(txtDireccion.getText().trim());
        familiar.setEdad(Integer.parseInt(txtEdad.getText()));
        familiar.setEmail(txtEmail.getText().trim());
        familiar.setGenero(cbbGenero.getSelectedIndex() == 0);
        doc.setPerson(familiar);
        doc.setTypeDocument((TipoDocumento) cbbTipoDocumento.getSelectedItem());
        doc.setNumero(txtDni.getText().trim());
        familiar.guardar();
        doc.guardar();
        if(txtCelular.getText().length()>=9){
            Celular celular=new Celular();
            celular.setNumero(txtCelular.getText().trim());
            celular.setDescipcion(txtDescripcionCelular.getText().length()==0?"personal":txtDescripcionCelular.getText());
            familiar.getCelulars().add(celular);
        }
        return familiar;
    }
    private boolean añadirFamiliar(Persona familiar){
        if(familiar!=null){
            Relacion relacion=new Relacion();
            relacion.setPersona(persona);
            relacion.setPersona1(familiar);
            relacion.setTipoRelacion(txtRelacion.getText().trim());
            relacion.setVivenJuntos(ckVivenJuntos.isSelected());
            if(persona.getRelaciones().isEmpty()){
                relacion.setApoderado(true);
            }else{
                relacion.setApoderado(false);
            }
            persona.getRelaciones().add(relacion);
            return true;
        }
        return false;

    }
    private void limpiarControles(){
        txtCelular.setText(null);
        txtDni.setText(null);
        txtRelacion.setText(null);
        txtApellidos.setText(null);
        txtNombres.setText(null);
        txtRelacion.setText(null);
        txtEdad.setText(null);
        txtDireccion.setText(null);
        txtDescripcionCelular.setText(null);
        ckVivenJuntos.setSelected(false);
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
    }
}
