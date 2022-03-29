package com.devel.views.dialogs.Exportar;

import com.devel.models.Persona;
import com.devel.utilities.Exportar;
import com.devel.utilities.Utilidades;
import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

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
    private JTable tablaAlumnos;

    public ExportarAlumnos(JTable tablaAlumnos) {
        this.tablaAlumnos=tablaAlumnos;
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
        List<String> columnas=getColumnasSeleccionadas();
        if(!columnas.isEmpty()){
            List<Object[]> datos=getDatos(columnas);
            Exportar.exportar("Relación alumnos",columnas,datos);
        }else{
            Utilidades.sendNotification("Error","Seleccione las columnas", TrayIcon.MessageType.ERROR);
        }
    }

    private void cerrar() {
        dispose();
    }
    private List<String> getColumnasSeleccionadas(){
        List<String> columnas=new ArrayList<>();

        if(ckbxCodigo.isSelected()){
            columnas.add("Código");
        }
        if(ckbxEstudiante.isSelected()){
            columnas.add("Estudiante");
        }
        if(ckbxEdad.isSelected()){
            columnas.add("Edad");
        }
        if(ckbxSeguro.isSelected()){
            columnas.add("Seguro");
        }
        if(ckbxApoderado.isSelected()){
            columnas.add("Apoderado");
        }
        if(ckbxNivel.isSelected()){
            columnas.add("Nivel");
        }
        if(ckbxGrado.isSelected()){
            columnas.add("Grado");
        }
        if(ckbxSeccion.isSelected()){
            columnas.add("Sección");
        }
        if(ckbxUltimaMatricula.isSelected()){
            columnas.add("Última matrícula");
        }
        return columnas;
    }
    private List<Object[]> getDatos(List<String> columnas){
        List<Object[]> datos=new ArrayList<>();
        for(int i=0;i<tablaAlumnos.getRowCount();i++){
            Object[] alumno=new Object[columnas.size()];
            for(int j=0;j<columnas.size();j++){
                alumno[j]=tablaAlumnos.getValueAt(tablaAlumnos.convertRowIndexToModel(i),tablaAlumnos.getColumn(columnas.get(j)).getModelIndex());
            }
            datos.add(alumno);
        }
        return datos;
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
