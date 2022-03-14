package com.devel.views.dialogs;

import com.devel.models.Nivel;
import com.devel.utilities.Utilidades;
import com.devel.validators.NivelValidator;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class DCrearNivel extends JDialog{
    private JTextField txtDescripcion;
    private JButton btnRegistrar;
    private JButton btnHecho;
    private TimePicker horaInicio;
    private TimePicker horaFin;
    private JPanel panelPrincipal;
    private Nivel nivel;
    private Date inicio;
    private Date fin;

    public DCrearNivel(){
        iniciarComponentes();
        nivel=new Nivel();
        btnRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
    }
    public DCrearNivel(Nivel nivel){
        iniciarComponentes();
        this.nivel=nivel;
        paraActualizar();
        btnRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
    }
    private void iniciarComponentes(){
        setTitle("Registrar Nivel");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }
//    private boolean registrarNivel(){
//        if(txtDescripcion.getText().length()>0&&horaFin.getTime()!=null&&horaFin.getTime()!=null){
//            Calendar calendar=Calendar.getInstance();
//            calendar.set(Calendar.HOUR_OF_DAY,horaInicio.getTime().getHour());
//            calendar.set(Calendar.MINUTE,horaInicio.getTime().getMinute());
//            nivel.setDescripcion(txtDescripcion.getText().trim());
//            nivel.setCreacion(new Date());
//            nivel.setHoraInicio(calendar.getTime());
//            calendar.set(Calendar.HOUR_OF_DAY,horaFin.getTime().getHour());
//            calendar.set(Calendar.MINUTE,horaFin.getTime().getMinute());
//            nivel.setHoraFin(calendar.getTime());
//            nivel.guardar();
//            return true;
//        }else{
//            return false;
//        }
//    }

    private void registrar(){
        String descripcion=txtDescripcion.getText().trim();
        Date inicio= horaInicio.getTime()==null?null:Utilidades.convertLocalTimeToDate(horaInicio.getTime());
        Date fin= horaFin.getTime()==null?null:Utilidades.convertLocalTimeToDate(horaFin.getTime());

        nivel.setCreacion(new Date());
        nivel.setDescripcion(descripcion);
        nivel.setHoraInicio(inicio);
        nivel.setHoraFin(fin);

        NivelValidator validator = new NivelValidator();
        Set<ConstraintViolation<Nivel>> errors = validator.loadViolations(nivel);
        if(errors.isEmpty()){
            nivel.guardar();
            VPrincipal.niveles.add(nivel);
            nivel=new Nivel();
            Utilidades.sendNotification("Éxito","Nivel registrado", TrayIcon.MessageType.INFO);
            limpiarControles();
        }else {
            NivelValidator.mostrarErrores(errors);
        }
    }

    private void actualizar(){
        String descripcion=txtDescripcion.getText().trim();
        Date inicio= horaInicio.getTime()==null?null:Utilidades.convertLocalTimeToDate(horaInicio.getTime());
        Date fin= horaFin.getTime()==null?null:Utilidades.convertLocalTimeToDate(horaFin.getTime());

        nivel.setDescripcion(descripcion);
        nivel.setHoraInicio(inicio);
        nivel.setHoraFin(fin);

        NivelValidator validator = new NivelValidator();
        Set<ConstraintViolation<Nivel>> errors = validator.loadViolations(nivel);
        if(errors.isEmpty()){
            nivel.guardar();
            Utilidades.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            cerrar();
        }else {
            NivelValidator.mostrarErrores(errors);
        }
    }

    private void paraActualizar(){
        setTitle("Editar Nivel");
        btnRegistrar.setText("Guardar");
        btnHecho.setText("Cancelar");
        cargarNivel();
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
    private void cargarNivel(){
        txtDescripcion.setText(nivel.getDescripcion());
        horaInicio.setTime(LocalTime.parse(Utilidades.formatoHora.format(nivel.getHoraInicio())));
        horaFin.setTime(LocalTime.parse(Utilidades.formatoHora.format(nivel.getHoraFin())));
    }
    private void guardarCopia(){
        txtDescripcion.setName(nivel.getDescripcion());
        inicio=nivel.getHoraInicio();
        fin=nivel.getHoraFin();
    }
    private void onCancel(){
        nivel.setDescripcion(txtDescripcion.getName());
        nivel.setHoraInicio(inicio);
        nivel.setHoraFin(fin);
        cerrar();
    }
    private void cerrar(){
        dispose();
    }
    private void limpiarControles(){
        txtDescripcion.setText(null);
        horaInicio.getComponentTimeTextField().setText(null);
        horaInicio.setTime(null);
        horaFin.getComponentTimeTextField().setText(null);
        horaFin.setTime(null);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        horaInicio=new TimePicker();
        horaFin=new TimePicker();
        horaInicio.getSettings().setFormatForDisplayTime("hh:mm a");
        horaInicio.getSettings().setFormatForMenuTimes("hh:mm a");
        horaFin.getSettings().setFormatForDisplayTime("hh:mm a");
        horaFin.getSettings().setFormatForMenuTimes("hh:mm a");
        horaInicio.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.valueOf("FifteenMinutes"),null,null);
        horaFin.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.valueOf("FifteenMinutes"),null,null);
        horaInicio.getComponentTimeTextField().setBorder(new JTextField().getBorder());
        horaFin.getComponentTimeTextField().setBorder(new JTextField().getBorder());
        horaInicio.getComponentTimeTextField().setEditable(false);
        horaFin.getComponentTimeTextField().setEditable(false);
    }
}
