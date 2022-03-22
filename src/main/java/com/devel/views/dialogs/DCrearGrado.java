package com.devel.views.dialogs;

import com.devel.models.Grado;
import com.devel.models.Nivel;
import com.devel.utilities.Colors;
import com.devel.utilities.Utilidades;
import com.devel.validators.GradoValidator;
import com.devel.views.VPrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import java.util.Vector;

public class DCrearGrado extends JDialog {
    private JTextField txtGrado;
    private JButton btnAñadir;
    private JPanel panelPrincipal;
    private JButton btnHecho;
    private JComboBox cbbNiveles;
    private Grado grado;

    public DCrearGrado() {
        iniciarComponentes();
        grado=new Grado();
        btnAñadir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                registrar();
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cerrar();
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public DCrearGrado(Grado grado) {
        iniciarComponentes();
        this.grado=grado;
        paraActualizar();
        btnAñadir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                actualizar();
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onCancel();
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void registrar(){
        String sgrado=txtGrado.getText().trim();
        Nivel nivel= (Nivel) cbbNiveles.getSelectedItem();
        grado.setGrado(sgrado);
        grado.setNivel(nivel);

        Set<ConstraintViolation<Grado>> errors = GradoValidator.loadViolations(grado);
        if(errors.isEmpty()){
            grado.guardar();
            VPrincipal.grados.add(grado);
            grado=new Grado();
            Utilidades.sendNotification("Éxito","Grado registrado", TrayIcon.MessageType.INFO);
            limpiarControles();
        }else {
            GradoValidator.mostrarErrores(errors);
        }
    }

    private void actualizar(){
        String sgrado=txtGrado.getText().trim();
        Nivel nivel= (Nivel) cbbNiveles.getSelectedItem();
        grado.setGrado(sgrado);
        grado.setNivel(nivel);

        Set<ConstraintViolation<Grado>> errors = GradoValidator.loadViolations(grado);
        if(errors.isEmpty()){
            grado.guardar();
            Utilidades.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            cerrar();
        }else {
            GradoValidator.mostrarErrores(errors);
        }
    }

    private void iniciarComponentes(){
        setTitle("Registrar Grado");
        setContentPane(panelPrincipal);
        cargarNiveles();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        cargarConfiguracion();
    }

    private void cargarNiveles(){
        cbbNiveles.setModel(new DefaultComboBoxModel(VPrincipal.niveles));
        cbbNiveles.setRenderer(new Nivel.ListCellRenderer());
    }

    private void paraActualizar(){
        setTitle("Editar Grado");
        btnAñadir.setText("Guardar");
        btnHecho.setText("Cancelar");
        cargarTarifa();
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
    private void cargarTarifa(){
        txtGrado.setText(grado.getGrado());
        cbbNiveles.setSelectedItem(grado.getNivel());
    }
    private void guardarCopia(){
        txtGrado.setName(grado.getGrado());
        cbbNiveles.setName(String.valueOf(cbbNiveles.getSelectedIndex()));
    }
    private void onCancel(){
        grado.setGrado(txtGrado.getName());
        grado.setNivel((Nivel) cbbNiveles.getItemAt(Integer.parseInt(cbbNiveles.getName())));
        cerrar();
    }
    private void cerrar(){
        dispose();
    }

    private void limpiarControles(){
        txtGrado.setText(null);
    }

    private void cargarConfiguracion(){
        switch (VPrincipal.tema){
            case "oscuro":
                btnHecho.setForeground(new Color(0xFFFFFF));
                btnAñadir.setBackground(Colors.buttonDefect2);
                break;
            default:
                btnHecho.setForeground(new Color(0x000000));
                btnAñadir.setForeground(Color.white);
                btnAñadir.setBackground(Colors.buttonDefect1);
                break;
        }
    }
}
