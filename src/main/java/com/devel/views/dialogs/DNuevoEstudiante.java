package com.devel.views.dialogs;

import com.devel.models.Persona;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.Period;

public class DNuevoEstudiante extends JDialog{
    private JTextField textField1;
    private JComboBox comboBox1;
    private DatePicker datePicker1;
    private JTextField txtEdad;
    private JPanel panelPrincipal;
    private JTable table2;
    private JButton añadirFamiliarButton;
    private JButton añdirCelularButton;
    private JTable tablaCelulares;
    private JButton guardarButton;
    private JButton hechoButton;
    private Persona persona=new Persona();
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
        DAñadirFamiliar dAñadirFamiliar=new DAñadirFamiliar(persona);
        dAñadirFamiliar.pack();
        dAñadirFamiliar.setLocationRelativeTo(null);
        dAñadirFamiliar.setVisible(true);
        tablaCelulares.updateUI();
    }
    private void iniciarComponentes(){
        setTitle("Nuevo estudiante");
        setContentPane(panelPrincipal);
        setModal(true);
//        setSize(new Dimension(400,250));
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
