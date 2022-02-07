package com.devel.views.dialogs;

import com.devel.models.Grado;
import com.devel.models.Nivel;
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
    private Grado grado;
    public DCrearGrado(@Nullable Grado grado1) {
        if(grado1==null){
            grado=new Grado();
            setTitle("Crear grado");
        }else{
            grado=grado1;
            txtGrado.setText(grado1.getGrado());
            setTitle("Editar grado");
            añadirButton.setText("Guardar");
        }
        iniciarComponentes();
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
                    Utilities.sendNotification("Error","Rellene todos los campos", TrayIcon.MessageType.ERROR);
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
    }

    private boolean guardarGrado(){
        if(txtGrado.getText().length()>0){
            grado.setGrado(txtGrado.getText().trim());
            grado.guardar();
            return true;
        }else{
            return false;
        }
    }
}
