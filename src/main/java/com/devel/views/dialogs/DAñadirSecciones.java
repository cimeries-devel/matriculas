package com.devel.views.dialogs;

import com.devel.models.Seccion;
import com.devel.models.Seguro;
import com.devel.utilities.Utilities;
import com.devel.views.VPrincipal;
import com.sun.istack.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DAñadirSecciones extends JDialog{
    private JTextField txtSeccion;
    private JButton btnRegistrar;
    private JPanel panelPrincipal;
    private JButton btnHecho;
    private Seccion seccion;
    public DAñadirSecciones(@Nullable Seccion seccion1) {
        if(seccion1==null){
            setTitle("Registrar Sección");
            seccion=new Seccion();
        }else{
            seccion=seccion1;
            cargarSeccion();
            setTitle("Editar Sección");
            btnRegistrar.setText("Guardar");
        }
        iniciarComponentes();
        btnRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(guardarSeccion()){
                    if(seccion1==null){
                        VPrincipal.secciones.add(seccion);
                        Utilities.sendNotification("Éxito","Sección registrada", TrayIcon.MessageType.INFO);
                        txtSeccion.setText(null);
                        seccion=null;
                        seccion=new Seccion();
                    }else{
                        Utilities.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
                    }
                }else{
                    Utilities.sendNotification("Error","Complete todos los campos", TrayIcon.MessageType.ERROR);
                }
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }
    private boolean guardarSeccion(){
        System.out.println("tamaño: "+txtSeccion.getText().length());
        if(txtSeccion.getText().length()>0){
            seccion.setSeccion(txtSeccion.getText().trim().toUpperCase());
            seccion.guardar();
            return true;
        }else{
            return false;
        }
    }
    private void cargarSeccion(){
        txtSeccion.setText(seccion.getSeccion());
    }
    private void iniciarComponentes(){
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }
}
