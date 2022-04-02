package com.devel.views.dialogs;

import com.devel.models.Seccion;
import com.devel.models.TipoRelacion;
import com.devel.utilities.Utilidades;
import com.devel.validators.SeccionValidator;
import com.devel.validators.TipoRelacionValidator;
import com.devel.views.VPrincipal;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DAñadirTipoRelacion extends JDialog{
    private JTextField txtTipoRelacion;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private TipoRelacion tipoRelacion;

    public DAñadirTipoRelacion(){
        iniciarComponentes();
        tipoRelacion=new TipoRelacion();
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

    public DAñadirTipoRelacion(TipoRelacion tipoRelacion) {
        iniciarComponentes();
        this.tipoRelacion=tipoRelacion;
        paraActualizar();
        guardarCopia();
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
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void registrar(){
        String tipo=txtTipoRelacion.getText().trim();
        tipoRelacion.setTipo(tipo);

        Set<ConstraintViolation<TipoRelacion>> errors = TipoRelacionValidator.loadViolations(tipoRelacion);
        if(errors.isEmpty()){
            tipoRelacion.guardar();
            VPrincipal.tipoRelaciones.add(tipoRelacion);
            tipoRelacion=new TipoRelacion();
            Utilidades.sendNotification("Éxito","Tipo de relación registrada", TrayIcon.MessageType.INFO);
            limpiarControles();
        }else {
            TipoRelacionValidator.mostrarErrores(errors);
        }
    }

    private void actualizar(){
        String tipo=txtTipoRelacion.getText().trim();
        tipoRelacion.setTipo(tipo);

        Set<ConstraintViolation<TipoRelacion>> errors = TipoRelacionValidator.loadViolations(tipoRelacion);
        if(errors.isEmpty()){
            tipoRelacion.guardar();
            Utilidades.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
            cerrar();
        }else {
            TipoRelacionValidator.mostrarErrores(errors);
        }
    }

    private void paraActualizar(){
        setTitle("Editar Tipo Relación");
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

    private void guardarCopia(){
        txtTipoRelacion.setText(tipoRelacion.getTipo());
        txtTipoRelacion.setName(tipoRelacion.getTipo());
    }
    private void onCancel(){
        tipoRelacion.setTipo(txtTipoRelacion.getName());
        cerrar();
    }

    private void cerrar(){
        dispose();
    }

    private void limpiarControles(){
        txtTipoRelacion.setText(null);
    }

}
