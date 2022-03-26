package com.devel.views.dialogs;

import com.devel.models.Nivel;
import com.devel.utilities.Colors;
import com.devel.utilities.Utilidades;
import com.devel.validators.NivelValidator;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

public class DCrearNivel extends JDialog{
    private JTextField txtDescripcion;
    private JButton btnAñadir;
    private JButton btnHecho;
    private TimePicker horaInicio;
    private TimePicker horaFin;
    private JPanel panelPrincipal;
    private Nivel nivel;
    private Date inicio;
    private Date fin;

    public DCrearNivel(){
        nivel=new Nivel();
        iniciarComponentes();
        btnAñadir.addActionListener(e -> {
            registrar();
        });
        btnHecho.addActionListener(e -> {
            cerrar();
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    public DCrearNivel(Nivel nivel){
        this.nivel=nivel;
        iniciarComponentes();
        paraActualizar();
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
        setTitle("Registrar Nivel");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void registrar(){
        String descripcion=txtDescripcion.getText().trim();
        Date inicio= horaInicio.getTime()==null?null:Utilidades.convertLocalTimeToDate(horaInicio.getTime());
        Date fin= horaFin.getTime()==null?null:Utilidades.convertLocalTimeToDate(horaFin.getTime());

        nivel.setCreacion(new Date());
        nivel.setDescripcion(descripcion);
        nivel.setHoraInicio(inicio);
        nivel.setHoraFin(fin);

        Set<ConstraintViolation<Nivel>> errors = NivelValidator.loadViolations(nivel);
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

        Set<ConstraintViolation<Nivel>> errors = NivelValidator.loadViolations(nivel);
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
        btnAñadir.setText("Guardar");
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
