package com.devel.views.dialogs;

import com.devel.models.Grado;
import com.devel.models.Nivel;
import com.devel.models.Salon;
import com.devel.models.Seccion;
import com.devel.utilities.Colors;
import com.devel.utilities.Utilidades;
import com.devel.validators.SalonValidator;
import com.devel.views.VPrincipal;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DAñadirSalon extends JDialog {
    private JPanel panelPrincipal;
    private JComboBox cbbNiveles;
    private JComboBox cbbGrado;
    private JComboBox cbbSeccion;
    private JButton btnHecho;
    private JButton btnAñadir;
    private Salon salon;
    private Nivel nivel;
    private Grado grado;
    private Seccion seccion;

    public DAñadirSalon() {
        salon = new Salon();
        iniciarComponentes();
        btnAñadir.addActionListener(e -> {
            registrar();
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

    public DAñadirSalon(Salon salon) {
        this.salon = salon;
        iniciarComponentes();
        paraActualizar();
        btnAñadir.addActionListener(e -> {
            actualizar();
        });
        btnHecho.addActionListener(e -> {
            onCancel();
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void iniciarComponentes() {
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        cargarComboBox();
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void registrar() {
        Nivel nivel = (Nivel) cbbNiveles.getSelectedItem();
        Grado grado = (Grado) cbbGrado.getSelectedItem();
        Seccion seccion = (Seccion) cbbSeccion.getSelectedItem();

        salon.setNivel(nivel);
        salon.setGrado(grado);
        salon.setSeccion(seccion);

        Set<ConstraintViolation<Salon>> errors = SalonValidator.loadViolations(salon);
        if (errors.isEmpty()) {
            salon.setNombre(grado.getGrado() + " " + seccion.getSeccion());
            salon.guardar();
            VPrincipal.salones.add(salon);
            salon = new Salon();
            Utilidades.sendNotification("Éxito", "Salon registrada", TrayIcon.MessageType.INFO);
            limpiarControles();
        } else {
            SalonValidator.mostrarErrores(errors);
        }
    }

    private void actualizar() {
        Nivel nivel = (Nivel) cbbNiveles.getSelectedItem();
        Grado grado = (Grado) cbbGrado.getSelectedItem();
        Seccion seccion = (Seccion) cbbSeccion.getSelectedItem();

        salon.setNivel(nivel);
        salon.setGrado(grado);
        salon.setSeccion(seccion);
        salon.setNombre(grado.getGrado() + " " + seccion.getSeccion());

        Set<ConstraintViolation<Salon>> errors = SalonValidator.loadViolations(salon);
        if (errors.isEmpty()) {
            salon.guardar();
            Utilidades.sendNotification("Éxito", "Cambios guardados", TrayIcon.MessageType.INFO);
            cerrar();
        } else {
            SalonValidator.mostrarErrores(errors);
        }
    }

    private void paraActualizar() {
        setTitle("Editar Salón");
        btnAñadir.setText("Guardar");
        btnHecho.setText("Cancelar");
        guardarCopia();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                onCancel();
            }
        });
    }

    private void cargarComboBox() {
        cbbNiveles.setModel(new DefaultComboBoxModel(VPrincipal.niveles));
        cbbNiveles.setRenderer(new Nivel.ListCellRenderer());

        cbbGrado.setModel(new DefaultComboBoxModel(VPrincipal.grados));
        cbbGrado.setRenderer(new Grado.ListCellRenderer());

        cbbSeccion.setModel(new DefaultComboBoxModel(VPrincipal.secciones));
        cbbSeccion.setRenderer(new Seccion.ListCellRenderer());
    }

    private void guardarCopia() {
        nivel = salon.getNivel();
        grado = salon.getGrado();
        seccion = salon.getSeccion();
        cbbGrado.setName(salon.getNombre());
        cargarSalon();
    }

    private void onCancel() {
        salon.setNivel(nivel);
        salon.setGrado(grado);
        salon.setSeccion(seccion);
        salon.setNombre(cbbGrado.getName());
        cerrar();
    }

    private void cerrar() {
        dispose();
    }

    private void cargarSalon() {
        cbbNiveles.setSelectedItem(salon.getNivel());
        cbbSeccion.setSelectedItem(salon.getSeccion());
        cbbGrado.setSelectedItem(salon.getGrado());
    }

    private void limpiarControles() {
        cbbNiveles.setSelectedItem(0);
        cbbGrado.setSelectedItem(0);
        cbbSeccion.setSelectedItem(0);
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Sección:");
        panel1.add(label1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbSeccion = new JComboBox();
        panel1.add(cbbSeccion, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Grado:");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbGrado = new JComboBox();
        panel1.add(cbbGrado, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Nivel:");
        panel1.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbNiveles = new JComboBox();
        panel1.add(cbbNiveles, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnHecho = new JButton();
        btnHecho.setText("Hecho");
        panel2.add(btnHecho, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btnAñadir = new JButton();
        btnAñadir.setText("Registrar");
        panel2.add(btnAñadir, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelPrincipal;
    }
}
