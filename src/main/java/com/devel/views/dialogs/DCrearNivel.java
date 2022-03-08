package com.devel.views.dialogs;

import com.devel.models.Nivel;
import com.devel.utilities.Utilities;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.sun.istack.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class DCrearNivel extends JDialog{
    private JTextField txtDescripcion;
    private JButton añadirButton;
    private JButton hechoButton;
    private TimePicker horaInicio;
    private TimePicker horaFin;
    private JPanel panelPrincipal;
    private Nivel nivel;
    DateFormat format=new SimpleDateFormat("HH:mm");
    public DCrearNivel(@Nullable Nivel nivel1){
        if(nivel1==null){
            setTitle("Nuevo nivel");
            nivel=new Nivel();
        }else {
            nivel=nivel1;
            cargarNivel();
            setTitle("Editar nivel");
            añadirButton.setText("Guardar");
            hechoButton.setText("Cancelar");
        }
        iniciarComponentes();
        añadirButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(registrarNivel()){
                    if(nivel1==null){
                        VPrincipal.niveles.add(nivel);
                        limpiarControles();
                        nivel=null;
                        nivel=new Nivel();
                        Utilities.sendNotification("Éxito","Nivel creado", TrayIcon.MessageType.INFO);
                    }else{
                        Utilities.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
                        dispose();
                    }
                }else{
                    Utilities.sendNotification("Error","Rellene todos los campos", TrayIcon.MessageType.ERROR);
                }

            }
        });
        hechoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }
    private void iniciarComponentes(){
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }
    private void cargarNivel(){
        txtDescripcion.setText(nivel.getDescripcion());
        horaInicio.setTime(LocalTime.parse(format.format(nivel.getHoraInicio())));
        horaFin.setTime(LocalTime.parse(format.format(nivel.getHoraFin())));
    }
    private boolean registrarNivel(){
        if(txtDescripcion.getText().length()>0&&horaFin.getTime()!=null&&horaFin.getTime()!=null){
            Calendar calendar=Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,horaInicio.getTime().getHour());
            calendar.set(Calendar.MINUTE,horaInicio.getTime().getMinute());
            nivel.setDescripcion(txtDescripcion.getText().trim());
            nivel.setCreacion(new Date());
            nivel.setHoraInicio(calendar.getTime());
            calendar.set(Calendar.HOUR_OF_DAY,horaFin.getTime().getHour());
            calendar.set(Calendar.MINUTE,horaFin.getTime().getMinute());
            nivel.setHoraFin(calendar.getTime());
            nivel.guardar();
            return true;
        }else{
            return false;
        }
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
