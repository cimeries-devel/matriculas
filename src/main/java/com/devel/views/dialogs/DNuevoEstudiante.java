package com.devel.views.dialogs;
import com.devel.controllers.Documentos;
import com.devel.models.*;
import com.devel.utilities.JButoonEditors.JButtonEditorCelulares;
import com.devel.utilities.JButoonEditors.JButtonEditorFamiliares;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilities;
import com.devel.utilities.modelosTablas.CelularesAbstractModel;
import com.devel.utilities.modelosTablas.FamiliaresAbstractModel;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Vector;

public class DNuevoEstudiante extends JDialog{
    private JTextField txtNombres;
    private JComboBox cbbTipoDocumento;
    private DatePicker datePicker1;
    private JTextField txtEdad;
    private JPanel panelPrincipal;
    private JButton registrarEstudianteButton;
    private JButton hechoButton;
    private JTextField txtDni;
    private JTextField txtApellidos;
    private JTextField txtEmail;
    private JTextField txtCodigo;
    private JComboBox cbbGenero;
    private JComboBox cbbSeguro;
    private JButton nuevoButton;
    private Persona persona=new Persona();

    public DNuevoEstudiante(){
        iniciarComponentes();
        datePicker1.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                if(datePicker1.getDate()!=null){
                    int edad=Utilities.calcularaños(Date.valueOf(datePicker1.getDate()));
                    txtEdad.setDisabledTextColor(new JTextField().getForeground());
                    txtEdad.setText(String.valueOf(edad));
                }
            }
        });
        hechoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        registrarEstudianteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(registrarEstudiante()){
                    limpiarControles();
                    Utilities.sendNotification("Éxito","Alumno registrado", TrayIcon.MessageType.INFO);
                }
            }
        });
        nuevoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DAñadirSeguro añadirSeguro=new DAñadirSeguro(null);
                añadirSeguro.setVisible(true);
                cbbSeguro.setSelectedIndex(cbbSeguro.getItemCount()-1);
            }
        });
    }
    private boolean registrarEstudiante(){
        if(txtDni.getText().length()>=8&&txtApellidos.getText().length()>5&&txtNombres.getText().length()>5&&!txtEdad.getText().isEmpty()&&!txtCodigo.getText().isEmpty()&&((Seguro)cbbSeguro.getSelectedItem()).getId()!=null){
            if(cbbTipoDocumento.getItemCount()>0){
            if(!persona.getRelaciones().isEmpty()){
                Documento documento=Documentos.getByDni(txtDni.getText().trim());
                if(documento!=null){
                    persona= documento.getPerson();
                }
                persona.setNombres(txtNombres.getText().trim());
                persona.setApellidos(txtApellidos.getText().trim());
                persona.setGenero(cbbGenero.getSelectedIndex()==0);
                persona.setEdad(Integer.parseInt(txtEdad.getText().trim()));
                persona.setCumpleaños(Date.valueOf(datePicker1.getDate()));
                persona.setCreacion(new java.util.Date());
                persona.setActualizacion(new java.util.Date());
                persona.setEmail(txtEmail.getText().trim());
                persona.setCodigo(txtCodigo.getText().trim());
                persona.guardar();
                guardarPersona();
                return true;
            }else{
                Utilities.sendNotification("Error","Debe registrar un Apoderado", TrayIcon.MessageType.ERROR);
                return false;
            }}else{
                Utilities.sendNotification("Error","Debe registrar un tipo de documento", TrayIcon.MessageType.ERROR);
                return false;
            }
        }else{
            Utilities.sendNotification("Error","Verifique todos los campos", TrayIcon.MessageType.ERROR);
            return false;
        }
    }

    private void guardarPersona(){
        for(Relacion relacion:persona.getRelaciones()){
            relacion.guardar();
        }
        for (Celular celular:persona.getCelulars()){
            celular.guardar();
        }
        Documento documento= Documentos.getByDni(txtDni.getText().trim());
        if(documento==null){
            documento.setTypeDocument((TipoDocumento) cbbTipoDocumento.getSelectedItem());
            documento.setNumero(txtDni.getText().trim());
            documento.setPerson(persona);
            documento.guardar();
        }

    }
    private void limpiarControles(){
        txtCodigo.setText(null);
        txtNombres.setText(null);
        txtApellidos.setText(null);
        txtEdad.setText(null);
        txtEmail.setText(null);
    }
    private void iniciarComponentes(){
        setTitle("Nuevo estudiante");
        setContentPane(panelPrincipal);
        pack();
        setModal(true);
        cargarComboBox();
    }
    private void cargarComboBox(){
        cbbTipoDocumento.setModel(new DefaultComboBoxModel<>(VPrincipal.tipoDocumentos));
        cbbTipoDocumento.setRenderer(new TipoDocumento.ListCellRenderer());
        cbbSeguro.setModel(new DefaultComboBoxModel(VPrincipal.seguros));
        cbbSeguro.setRenderer(new Seguro.ListCellRenderer());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        datePicker1=new DatePicker();
        datePicker1.getComponentDateTextField().setBorder(new JTextField().getBorder());
        datePicker1.getComponentDateTextField().setDisabledTextColor(new JTextField().getForeground());
        int ge=new JTextField().getHeight()+15;
        datePicker1.setPreferredSize(new Dimension(200,ge));
        datePicker1.getComponentDateTextField().setPreferredSize(new JTextField().getPreferredSize());
        datePicker1.getComponentDateTextField().setHorizontalAlignment(JTextField.CENTER);
//        datePicker1.setPreferredSize(new Dimension(200,new JTextField().getHeight()));
//        DateTimeFormatter a=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        datePicker1.getSettings().setFormatForDatesCommonEra("dd-MM-yyyy");
    }
}
