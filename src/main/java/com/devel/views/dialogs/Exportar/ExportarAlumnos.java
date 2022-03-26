package com.devel.views.dialogs.Exportar;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ExportarAlumnos extends JDialog {
    private JPanel panelPrincipal;
    private JButton btnExportar;
    private JButton btnHecho;
    private JCheckBox ckbxTodos;
    private JCheckBox ckbxCodigo;
    private JCheckBox ckbxEstudiante;
    private JCheckBox ckbxEdad;
    private JCheckBox ckbxSeguro;
    private JCheckBox ckbxApoderado;
    private JCheckBox ckbxNivel;
    private JCheckBox ckbxGrado;
    private JCheckBox ckbxSeccion;
    private JCheckBox ckbxUltimaMatricula;

    public ExportarAlumnos() {
        iniciarComponentes();
        btnExportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportar();
            }
        });
        btnHecho.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                cerrar();
            }
        });

        ckbxTodos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                todosLosCampos();
            }
        });
    }

    private void exportar() {
        dispose();
    }

    private void cerrar() {
        dispose();
    }

    private void todosLosCampos(){
        boolean estado=ckbxTodos.isSelected();
        ckbxCodigo.setSelected(estado);
        ckbxEstudiante.setSelected(estado);
        ckbxEdad.setSelected(estado);
        ckbxSeguro.setSelected(estado);
        ckbxApoderado.setSelected(estado);
        ckbxNivel.setSelected(estado);
        ckbxGrado.setSelected(estado);
        ckbxSeccion.setSelected(estado);
        ckbxUltimaMatricula.setSelected(estado);
    }
    private void iniciarComponentes(){
        setTitle("Exportar Relación Alumnos");
        getRootPane().setDefaultButton(btnExportar);
        setContentPane(panelPrincipal);
        setModal(true);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

    }
}
