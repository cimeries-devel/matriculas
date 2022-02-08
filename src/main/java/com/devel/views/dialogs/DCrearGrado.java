package com.devel.views.dialogs;

import com.devel.models.Grado;
import com.devel.models.Nivel;
import com.devel.models.TipoDocumento;
import com.devel.utilities.Utilities;
import com.devel.views.VPrincipal;
import com.sun.istack.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DCrearGrado extends JDialog {
    private JTextField txtGrado;
    private JButton añadirButton;
    private JPanel panelPrincipal;
    private JButton btnHecho;
    private JComboBox cbbNiveles;
    private Grado grado;
    private String error;
    public DCrearGrado(@Nullable Grado grado1) {
        iniciarComponentes();
        if(grado1==null){
            grado=new Grado();
            setTitle("Crear grado");
        }else{
            grado=grado1;
            txtGrado.setText(grado1.getGrado());
            cbbNiveles.setSelectedItem(grado1.getNivel());
            setTitle("Editar grado");
            añadirButton.setText("Guardar");
        }
        añadirButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(guardarGrado()){
                    if(grado1==null){
                        VPrincipal.grados.add(grado);
                        txtGrado.setText(null);
                        grado=null;
                        grado=new Grado();
                        Utilities.sendNotification("Éxito","Grado creado", TrayIcon.MessageType.INFO);
                    }else{
                        Utilities.sendNotification("Éxito","Cambios guardados", TrayIcon.MessageType.INFO);
                    }
                }else{
                    switch (error){
                        case "nivel":
                            Utilities.sendNotification("Error","Primero debe registrar un nivel", TrayIcon.MessageType.ERROR);
                            break;
                        case "grado":
                            Utilities.sendNotification("Error","Rellene todos los campos", TrayIcon.MessageType.ERROR);
                            break;
                    }

                }
            }
        });
        btnHecho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
        cargarCombox();
    }
    private void cargarCombox(){
        cbbNiveles.setModel(new DefaultComboBoxModel<>(VPrincipal.niveles));
        cbbNiveles.setRenderer(new Nivel.ListCellRenderer());
    }
    private boolean guardarGrado(){
        if(txtGrado.getText().length()>0){
            if(cbbNiveles.getItemCount()!=0){
                grado.setNivel((Nivel) cbbNiveles.getSelectedItem());
                grado.setGrado(txtGrado.getText().trim());
                grado.guardar();
                ((Nivel) cbbNiveles.getSelectedItem()).getGrados().add(grado);
                return true;
            }else{
                error="nivel";
            }
        }else{
            error="grado";
        }
        return false;
    }
}
