package com.devel.views.tabs;

import com.devel.controllers.Documentos;
import com.devel.models.Documento;
import com.devel.models.Persona;
import com.devel.models.Registro;
import com.devel.models.Relacion;
import com.devel.utilities.JButoonEditors.JButtonEditorFamiliares;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.Utilities;
import com.devel.utilities.modelosTablas.AlumnosMatriculadosAbstractModel;
import com.devel.utilities.modelosTablas.FamiliaresAbstractModel;
import com.devel.views.VPrincipal;
import com.devel.views.dialogs.DAñadirFamiliar;
import com.devel.views.dialogs.DNuevoEstudiante;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class VMatricula extends JFrame{
    private JPanel panelPrincipal;
    private JTable tablaFamiliares;
    private JTable tablaMatriculas;
    private JTextField txtEdad;
    private JComboBox comboBox2;
    private JTextField txtDni;
    private JButton buscarButton;
    private JButton btnNuevoEstudiante;
    private JButton registrarMatriculaButton;
    private JButton nuevoFamiliarButton;
    private JTextField txtNombres;
    private JCheckBox matriculadoCheckBox;
    private JTextField textField1;
    private JScrollPane jScrollPane1;
    private FamiliaresAbstractModel familiaresAbstractModel;
    private Persona persona;
    private DateFormat año=new SimpleDateFormat("yyyy");
    private AlumnosMatriculadosAbstractModel matriculadosAbstractModel;
    public VMatricula() {
        iniciarComponentes();
        btnNuevoEstudiante.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarNuevoEstudiante();
            }
        });
        buscarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        buscarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                buscarAlumno();
            }
        });
    }
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    private void cargarNuevoEstudiante(){
        DNuevoEstudiante dNuevoEstudiante=new DNuevoEstudiante();
        dNuevoEstudiante.pack();
        dNuevoEstudiante.setLocationRelativeTo(null);
        dNuevoEstudiante.setVisible(true);
    }
    private void cargarAgregarFamiliar(){
        DAñadirFamiliar dAñadirFamiliar=new DAñadirFamiliar(persona);
        dAñadirFamiliar.pack();
        dAñadirFamiliar.setLocationRelativeTo(null);
        dAñadirFamiliar.setVisible(true);
        cargarTablaFamiliares(new Vector<>(persona.getRelaciones()));
        tablaFamiliares.updateUI();
    }
    private void buscarAlumno(){
        if(txtDni.getText().length()>=8){
            Documento dni= Documentos.getByDni(txtDni.getText().trim());
            if(dni!=null){
                if(dni.getPerson().getCodigo()!=null){
                    persona=dni.getPerson();
                    txtNombres.setText(persona.getNombres()+" "+persona.getApellidos());
                    verificarMatricula();
                    nuevoFamiliarButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            cargarAgregarFamiliar();
                        }
                    });
                }else{
                    Utilities.sendNotification("Error","No es estudiante", TrayIcon.MessageType.ERROR);
                }

            }else{
                Utilities.sendNotification("No hay datos","Alumno no encontrado", TrayIcon.MessageType.INFO);
                nuevoFamiliarButton.removeAll();
            }
        }else{
            Utilities.sendNotification("Error","Ingrese el dni", TrayIcon.MessageType.ERROR);
        }

    }
    private void verificarMatricula(){
        if(!persona.getRegistros().isEmpty()){
            Registro registro=persona.getRegistros().get(persona.getRegistros().size());
            if(año.format(registro.getActualizacion().getTime()).equals(año.format(new Date()))){
                matriculadoCheckBox.setSelected(true);
            }else{
                matriculadoCheckBox.setSelected(false);
            }
        }else{
            matriculadoCheckBox.setSelected(false);
        }

    }
    private void iniciarComponentes(){
        persona=new Persona();
        setTitle("Matrícula");
        cargarTablaFamiliares(new Vector<>(persona.getRelaciones()));
        cargarMatriculas();
    }
    private void cargarMatriculas(){
        matriculadosAbstractModel=new AlumnosMatriculadosAbstractModel(VPrincipal.alumnosMatriculados);
        tablaMatriculas.setModel(matriculadosAbstractModel);
        Utilities.headerNegrita(tablaMatriculas);
    }
    private void cargarTablaFamiliares(Vector<Relacion> relaciones){
        familiaresAbstractModel=new FamiliaresAbstractModel(relaciones);
        tablaFamiliares.setModel(familiaresAbstractModel);
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonEditorFamiliares(relaciones));
        TableCellRenderer renderer1 = tablaFamiliares.getDefaultRenderer(JButton.class);
        tablaFamiliares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilities.headerNegrita(tablaFamiliares);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
