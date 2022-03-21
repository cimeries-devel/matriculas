package com.devel.views.dialogs;

import com.devel.models.Grado;
import com.devel.models.Nivel;
import com.devel.models.Salon;
import com.devel.models.Seccion;
import com.devel.utilities.Utilidades;
import com.devel.validators.SalonValidator;
import com.devel.views.VPrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import java.util.Vector;

public class DAñadirSalon extends JDialog{
    private JPanel panelPrincipal;
    private JComboBox cbbNiveles;
    private JComboBox cbbGrado;
    private JComboBox cbbSeccion;
    private JButton btnHecho;
    private JButton btnRegistrar;
    private Salon salon;
    private Nivel nivel;
    private Grado grado;
    private Seccion seccion;

    public DAñadirSalon() {
        salon=new Salon();
        iniciarComponentes();
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrar();
            }
        });
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public DAñadirSalon(Salon salon) {
        this.salon=salon;
        iniciarComponentes();
        paraActualizar();
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar();
            }
        });
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void iniciarComponentes(){
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        cargarComboBox();
    }

    private void registrar(){
        Nivel nivel= (Nivel) cbbNiveles.getSelectedItem();
        Grado grado= (Grado) cbbGrado.getSelectedItem();
        Seccion seccion= (Seccion) cbbSeccion.getSelectedItem();

        salon.setNivel(nivel);
        salon.setGrado(grado);
        salon.setSeccion(seccion);

        SalonValidator validator = new SalonValidator();
        Set<ConstraintViolation<Salon>> errors = validator.loadViolations(salon);
        if(errors.isEmpty()){
            salon.setNombre(grado.getGrado()+ " "+seccion.getSeccion());
            salon.guardar();
            VPrincipal.salones.add(salon);
            salon=new Salon();
            Utilidades.sendNotification("Éxito","Salon registrada", TrayIcon.MessageType.INFO);
            limpiarControles();
        }else {
            SalonValidator.mostrarErrores(errors);
        }
    }

    private void actualizar(){
        Nivel nivel= (Nivel) cbbNiveles.getSelectedItem();
        Grado grado= (Grado) cbbGrado.getSelectedItem();
        Seccion seccion= (Seccion) cbbSeccion.getSelectedItem();

        salon.setNivel(nivel);
        salon.setGrado(grado);
        salon.setSeccion(seccion);
        salon.setNombre(grado.getGrado()+ " "+seccion.getSeccion());

        SalonValidator validator = new SalonValidator();
        Set<ConstraintViolation<Salon>> errors = validator.loadViolations(salon);
        if(errors.isEmpty()){
            salon.guardar();
            Utilidades.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            cerrar();
        }else {
            SalonValidator.mostrarErrores(errors);
        }
    }

    private void paraActualizar(){
        setTitle("Editar Salón");
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

    private void cargarComboBox(){
        cbbNiveles.setModel(new DefaultComboBoxModel(VPrincipal.niveles));
        cbbNiveles.setRenderer(new Nivel.ListCellRenderer());

        cbbGrado.setModel(new DefaultComboBoxModel(VPrincipal.grados));
        cbbGrado.setRenderer(new Grado.ListCellRenderer());

        cbbSeccion.setModel(new DefaultComboBoxModel(VPrincipal.secciones));
        cbbSeccion.setRenderer(new Seccion.ListCellRenderer());
    }
    private void guardarCopia(){
        nivel=salon.getNivel();
        grado=salon.getGrado();
        seccion=salon.getSeccion();
        cbbGrado.setName(salon.getNombre());
    }

    private void onCancel(){
        salon.setNivel(nivel);
        salon.setGrado(grado);
        salon.setSeccion(seccion);
        salon.setNombre(cbbGrado.getName());
        cerrar();
    }

    private void cerrar(){
        dispose();
    }
    private void limpiarControles(){
        cbbNiveles.setSelectedItem(0);
        cbbGrado.setSelectedItem(0);
        cbbSeccion.setSelectedItem(0);
    }
}
