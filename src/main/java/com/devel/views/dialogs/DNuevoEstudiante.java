package com.devel.views.dialogs;
import com.devel.models.*;
import com.devel.utilities.JButoonEditors.JButtonEditorFamiliares;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.modelosTablas.FamiliaresAbstractModel;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.Period;
import java.util.Vector;

public class DNuevoEstudiante extends JDialog{
    private JTextField textField1;
    private JComboBox cbbTipoDocumento;
    private DatePicker datePicker1;
    private JTextField txtEdad;
    private JPanel panelPrincipal;
    private JButton añadirFamiliarButton;
    private JButton añdirCelularButton;
    private JTable tablaCelulares;
    private JTable tablaFamiliares;
    private JButton guardarButton;
    private JButton hechoButton;
    private JScrollPane jScrollPane;
    private Persona persona=new Persona();
    private FamiliaresAbstractModel model;
    private Vector<Persona> familiares=new Vector<>();
    private Vector<Relacion> relaciones=new Vector<>();
    private Vector<Documento> documentos=new Vector<>();
    public DNuevoEstudiante(){
        iniciarComponentes();
        datePicker1.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                Period edad = Period.between(datePicker1.getDate(), LocalDate.now());
                txtEdad.setDisabledTextColor(new JTextField().getForeground());
                txtEdad.setText(String.valueOf(edad.getYears()));
            }
        });
        añdirCelularButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarAgregarCelular();
            }
        });
        añadirFamiliarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarAgregarFamiliar();
                tablaFamiliares.updateUI();
            }
        });
        hechoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
    }
    private void cargarAgregarCelular(){
        DAñadirCelular dañadirCelular=new DAñadirCelular(persona);
        dañadirCelular.pack();
        dañadirCelular.setLocationRelativeTo(null);
        dañadirCelular.setVisible(true);
        tablaCelulares.updateUI();
    }
    private void cargarAgregarFamiliar(){
        DAñadirFamiliar dAñadirFamiliar=new DAñadirFamiliar(relaciones,familiares,persona,documentos);
        dAñadirFamiliar.pack();
        dAñadirFamiliar.setLocationRelativeTo(null);
        dAñadirFamiliar.setVisible(true);
        tablaCelulares.updateUI();
    }
    private void iniciarComponentes(){
        setTitle("Nuevo estudiante");
        setContentPane(panelPrincipal);
        setResizable(false);
        setModal(true);
        cargarComboBox();
        cargarTalbaFamiliares(relaciones);
    }
    private void cargarComboBox(){
        cbbTipoDocumento.setModel(new DefaultComboBoxModel<>(VPrincipal.tipoDocumentos));
        cbbTipoDocumento.setRenderer(new TipoDocumento.ListCellRenderer());
    }
    private void cargarTalbaFamiliares(Vector<Relacion> relaciones){
        model=new FamiliaresAbstractModel(relaciones);
        tablaFamiliares.setModel(model);
        tablaFamiliares.getColumnModel().getColumn(model.getColumnCount() - 1).setCellEditor(new JButtonEditorFamiliares());
        TableCellRenderer renderer1 = tablaFamiliares.getDefaultRenderer(JButton.class);
        tablaFamiliares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        datePicker1=new DatePicker();
        datePicker1.getComponentDateTextField().setBorder(new JTextField().getBorder());
        datePicker1.getComponentDateTextField().setEnabled(false);
        datePicker1.getComponentDateTextField().setDisabledTextColor(new JTextField().getForeground());
        datePicker1.getComponentDateTextField().setPreferredSize(new Dimension(150,new JTextField().getHeight()));
//        datePicker1.setPreferredSize(new Dimension(200,new JTextField().getHeight()));
//        DateTimeFormatter a=DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        datePicker1.getSettings().setFormatForDatesCommonEra("dd/MM/yyyy");
    }
}
