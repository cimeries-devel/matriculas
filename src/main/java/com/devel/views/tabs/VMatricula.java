package com.devel.views.tabs;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class VMatricula extends JFrame{
    private JPanel panelPrincipal;
    private JTable table1;
    private JComboBox comboBox1;
    private JTextField textField1;
    private DatePicker datePicker1;
    private JTextField txtEdad;
    private JComboBox comboBox2;

    public VMatricula() {
        setTitle("Matrícula");
        datePicker1.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                Period edad = Period.between(datePicker1.getDate(), LocalDate.now());
                txtEdad.setDisabledTextColor(new JTextField().getForeground());
                txtEdad.setText(String.valueOf(edad.getYears()));
            }
        });
    }
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        datePicker1=new DatePicker();
        datePicker1.getComponentDateTextField().setBorder(new JTextField().getBorder());
        datePicker1.getComponentDateTextField().setEnabled(false);
        datePicker1.getComponentDateTextField().setDisabledTextColor(new JTextField().getForeground());
        datePicker1.setSize(new JTextField().getSize());
//        DateTimeFormatter a=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        datePicker1.getSettings().setFormatForDatesCommonEra("dd/MM/yyyy");
    }
}
