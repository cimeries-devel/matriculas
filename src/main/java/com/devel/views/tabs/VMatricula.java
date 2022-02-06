package com.devel.views.tabs;

import com.devel.models.Relacion;
import com.devel.utilities.JButoonEditors.JButtonEditorFamiliares;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.modelosTablas.FamiliaresAbstractModel;
import com.devel.views.dialogs.DNuevoEstudiante;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class VMatricula extends JFrame{
    private JPanel panelPrincipal;
    private JTable tablaFamiliares;
    private JTable tablaMatriculas;
    private JTextField txtEdad;
    private JButton button1;
    private JComboBox comboBox2;
    private JTextField textField1;
    private JButton buscarButton;
    private JButton btnNuevoEstudiante;
    private JScrollPane jScrollPane1;
    private FamiliaresAbstractModel model;
    public VMatricula() {
        iniciarComponentes();
        btnNuevoEstudiante.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarNuevoEstudiante();
            }
        });
    }
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    private void cargarNuevoEstudiante(){
        setTitle("Matrícula");
        DNuevoEstudiante dNuevoEstudiante=new DNuevoEstudiante();
        dNuevoEstudiante.pack();
        dNuevoEstudiante.setLocationRelativeTo(null);
        dNuevoEstudiante.setVisible(true);
        tablaFamiliares.updateUI();
    }
    private void iniciarComponentes(){
        cargarTalbaFamiliares(new Vector<>());
    }
    private void cargarTalbaFamiliares(Vector<Relacion> relaciones){
        model=new FamiliaresAbstractModel(relaciones);
        tablaFamiliares.setModel(model);
        tablaFamiliares.getColumnModel().getColumn(model.getColumnCount() - 1).setCellEditor(new JButtonEditorFamiliares(relaciones));
        TableCellRenderer renderer1 = tablaFamiliares.getDefaultRenderer(JButton.class);
        tablaFamiliares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
