package com.devel.views.dialogs;

import com.devel.models.Tarifa;
import com.devel.utilities.Colors;
import com.devel.utilities.Utilidades;
import com.devel.validators.TarifaValidator;
import com.devel.views.VPrincipal;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class DAñadirTarifa extends JDialog {
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JPanel panelPrincipal;
    private Tarifa tarifa;

    public DAñadirTarifa() {
        iniciarComponentes();
        tarifa = new Tarifa();

        btnAñadir.addActionListener(e -> {
            registrar();
        });
        btnHecho.addActionListener(e -> {
            cerrar();
        });
        txtPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Utilidades.precioEsValido(e, txtPrecio.getText().trim())) {
                    e.consume();
                }
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public DAñadirTarifa(Tarifa tarifa) {
        iniciarComponentes();
        this.tarifa = tarifa;
        paraActualizar();
        btnAñadir.addActionListener(e -> {
            actualizar();
        });
        btnHecho.addActionListener(e -> {
            onCancel();
        });
        txtPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Utilidades.precioEsValido(e, txtPrecio.getText().trim())) {
                    e.consume();
                }
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void registrar() {
        String desripcion = txtDescripcion.getText().trim();
        Double precio = Double.valueOf(String.valueOf(txtPrecio.getText().trim().isEmpty() ? 0 : txtPrecio.getText().trim()));
        tarifa.setDescripcion(desripcion);
        tarifa.setPrecio(precio);
        tarifa.setCreacion(new Date());
        tarifa.setDefecto(false);

        Set<ConstraintViolation<Tarifa>> errors = TarifaValidator.loadViolations(tarifa);
        if (errors.isEmpty()) {
            tarifa.guardar();
            VPrincipal.tarifas.add(tarifa);
            tarifa = new Tarifa();
            Utilidades.sendNotification("Éxito", "Tarifa registrado", TrayIcon.MessageType.INFO);
            limpiarControles();
        } else {
            TarifaValidator.mostrarErrores(errors);
        }
    }

    private void actualizar() {
        String desripcion = txtDescripcion.getText().trim();
        Double precio = Double.valueOf(String.valueOf(txtPrecio.getText().trim().isEmpty() ? 0 : txtPrecio.getText().trim()));
        tarifa.setDescripcion(desripcion);
        tarifa.setPrecio(precio);

        Set<ConstraintViolation<Tarifa>> errors = TarifaValidator.loadViolations(tarifa);
        if (errors.isEmpty()) {
            tarifa.guardar();
            Utilidades.sendNotification("Éxito", "Cambios guardados", TrayIcon.MessageType.INFO);
            cerrar();
        } else {
            TarifaValidator.mostrarErrores(errors);
        }
    }

    private void iniciarComponentes() {
        setTitle("Registrar tarifa");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void paraActualizar() {
        setTitle("Editar Tarifa");
        btnAñadir.setText("Guardar");
        btnHecho.setText("Cancelar");
        cargarTarifa();
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

    private void cargarTarifa() {
        txtDescripcion.setText(tarifa.getDescripcion());
        txtPrecio.setText(String.valueOf(tarifa.getPrecio()));
    }

    private void guardarCopia() {
        txtDescripcion.setName(tarifa.getDescripcion());
        txtPrecio.setName(String.valueOf(tarifa.getPrecio()));
    }

    private void onCancel() {
        tarifa.setDescripcion(txtDescripcion.getName());
        tarifa.setPrecio(Double.valueOf(txtPrecio.getName()));
        cerrar();
    }

    private void cerrar() {
        dispose();
    }

    private void limpiarControles() {
        txtDescripcion.setText(null);
        txtPrecio.setText(null);
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
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btnHecho = new JButton();
        btnHecho.setText("Hecho");
        panel1.add(btnHecho, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnAñadir = new JButton();
        btnAñadir.setText("Registrar");
        panel1.add(btnAñadir, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        txtDescripcion = new JTextField();
        txtDescripcion.setText("");
        panel2.add(txtDescripcion, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtPrecio = new JTextField();
        panel2.add(txtPrecio, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Precio:");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Descripción:");
        panel2.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelPrincipal;
    }
}
