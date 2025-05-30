package com.devel.views.dialogs;

import com.devel.utilities.Colors;
import com.devel.utilities.JButoonEditors.JButtonEditorSalones;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.TablecellRendered.SalonesCellRendered;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.SalonesAbstractModel;
import com.devel.views.VPrincipal;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class DGestionSalon extends JDialog {
    private JTable tablaSalones;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private SalonesAbstractModel salonesAbstractModel;

    public DGestionSalon() {
        iniciarComponentes();
        btnAñadir.addActionListener(e -> {
            cargarNuevoSalon();
        });
        btnHecho.addActionListener(e -> {
            cerrar();
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void iniciarComponentes() {
        setTitle("Salones");
        setContentPane(panelPrincipal);
        cargarTabla();
        pack();
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void cargarNuevoSalon() {
        DAñadirSalon dAñadirSalon = new DAñadirSalon();
        dAñadirSalon.setVisible(true);
        Utilidades.actualizarTabla(tablaSalones);
    }

    private void cargarTabla() {
        salonesAbstractModel = new SalonesAbstractModel(VPrincipal.salones);
        tablaSalones.setModel(salonesAbstractModel);
        tablaSalones.getColumnModel().getColumn(salonesAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonEditorSalones(tablaSalones));
        TableCellRenderer renderer1 = tablaSalones.getDefaultRenderer(JButton.class);
        tablaSalones.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        SalonesCellRendered.setCellRenderer(tablaSalones);
        Utilidades.headerNegrita(tablaSalones);
    }

    private void cerrar() {
        dispose();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, 10));
        final JScrollPane scrollPane1 = new JScrollPane();
        panelPrincipal.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablaSalones = new JTable();
        tablaSalones.setRowHeight(25);
        scrollPane1.setViewportView(tablaSalones);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btnAñadir = new JButton();
        btnAñadir.setText("Nuevo");
        panel1.add(btnAñadir, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnHecho = new JButton();
        btnHecho.setText("Hecho");
        panel1.add(btnHecho, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelPrincipal;
    }
}
