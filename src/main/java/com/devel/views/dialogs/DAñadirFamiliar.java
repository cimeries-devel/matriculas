package com.devel.views.dialogs;

import com.devel.models.Persona;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.Period;

public class DAñadirFamiliar extends JDialog{
    private DatePicker datePicker1;
    private JTextField txtEdad;
    private JTextField textField1;
    private JButton hechoButton;
    private JTextField textField2;
    private JCheckBox vivenJuntosCheckBox;
    private JButton añadirButton1;
    private JPanel panelPrincipal;
    private JComboBox comboBox1;
    private JButton buscarButton;
    private Persona persona;
    public DAñadirFamiliar(Persona persona){
        this.persona=persona;
        iniciarComponentes();
        datePicker1.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                Period edad = Period.between(datePicker1.getDate(), LocalDate.now());
                txtEdad.setDisabledTextColor(new JTextField().getForeground());
                txtEdad.setText(String.valueOf(edad.getYears()));
            }
        });
        hechoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
        añadirButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                añadirFamiliar();
            }
        });
    }
    private void iniciarComponentes(){
        setTitle("Añadir nuevo familiar");
        setContentPane(panelPrincipal);
        setModal(true);
    }
    private void añadirFamiliar(){

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        datePicker1=new DatePicker();
    }
}
