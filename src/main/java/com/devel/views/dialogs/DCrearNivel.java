package com.devel.views.dialogs;

import com.devel.models.Nivel;
import com.devel.utilities.Colors;
import com.devel.utilities.Utilidades;
import com.devel.validators.NivelValidator;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class DCrearNivel extends JDialog {
    private JTextField txtDescripcion;
    private JButton btnAñadir;
    private JButton btnHecho;
    private TimePicker horaInicio;
    private TimePicker horaFin;
    private JPanel panelPrincipal;
    private Nivel nivel;
    private Date inicio;
    private Date fin;

    public DCrearNivel() {
        nivel = new Nivel();
        $$$setupUI$$$();
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

    public DCrearNivel(Nivel nivel) {
        this.nivel = nivel;
        $$$setupUI$$$();
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
        setTitle("Registrar Nivel");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void registrar() {
        String descripcion = txtDescripcion.getText().trim();
        Date inicio = horaInicio.getTime() == null ? null : Utilidades.convertLocalTimeToDate(horaInicio.getTime());
        Date fin = horaFin.getTime() == null ? null : Utilidades.convertLocalTimeToDate(horaFin.getTime());

        nivel.setCreacion(new Date());
        nivel.setDescripcion(descripcion);
        nivel.setHoraInicio(inicio);
        nivel.setHoraFin(fin);

        Set<ConstraintViolation<Nivel>> errors = NivelValidator.loadViolations(nivel);
        if (errors.isEmpty()) {
            nivel.guardar();
            VPrincipal.niveles.add(nivel);
            nivel = new Nivel();
            Utilidades.sendNotification("Éxito", "Nivel registrado", TrayIcon.MessageType.INFO);
            limpiarControles();
        } else {
            NivelValidator.mostrarErrores(errors);
        }
    }

    private void actualizar() {
        String descripcion = txtDescripcion.getText().trim();
        Date inicio = horaInicio.getTime() == null ? null : Utilidades.convertLocalTimeToDate(horaInicio.getTime());
        Date fin = horaFin.getTime() == null ? null : Utilidades.convertLocalTimeToDate(horaFin.getTime());

        nivel.setDescripcion(descripcion);
        nivel.setHoraInicio(inicio);
        nivel.setHoraFin(fin);

        Set<ConstraintViolation<Nivel>> errors = NivelValidator.loadViolations(nivel);
        if (errors.isEmpty()) {
            nivel.guardar();
            Utilidades.sendNotification("Éxito", "Cambios guardados", TrayIcon.MessageType.INFO);
            cerrar();
        } else {
            NivelValidator.mostrarErrores(errors);
        }
    }

    private void paraActualizar() {
        setTitle("Editar Nivel");
        btnAñadir.setText("Guardar");
        btnHecho.setText("Cancelar");
        cargarNivel();
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

    private void cargarNivel() {
        txtDescripcion.setText(nivel.getDescripcion());
        horaInicio.setTime(LocalTime.parse(Utilidades.formatoHora.format(nivel.getHoraInicio())));
        horaFin.setTime(LocalTime.parse(Utilidades.formatoHora.format(nivel.getHoraFin())));
    }

    private void guardarCopia() {
        txtDescripcion.setName(nivel.getDescripcion());
        inicio = nivel.getHoraInicio();
        fin = nivel.getHoraFin();
    }

    private void onCancel() {
        nivel.setDescripcion(txtDescripcion.getName());
        nivel.setHoraInicio(inicio);
        nivel.setHoraFin(fin);
        cerrar();
    }

    private void cerrar() {
        dispose();
    }

    private void limpiarControles() {
        txtDescripcion.setText(null);
        horaInicio.getComponentTimeTextField().setText(null);
        horaInicio.setTime(null);
        horaFin.getComponentTimeTextField().setText(null);
        horaFin.setTime(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        horaInicio = new TimePicker();
        horaFin = new TimePicker();
        horaInicio.getSettings().setFormatForDisplayTime("hh:mm a");
        horaInicio.getSettings().setFormatForMenuTimes("hh:mm a");
        horaFin.getSettings().setFormatForDisplayTime("hh:mm a");
        horaFin.getSettings().setFormatForMenuTimes("hh:mm a");
        horaInicio.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.valueOf("FifteenMinutes"), null, null);
        horaFin.getSettings().generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.valueOf("FifteenMinutes"), null, null);
        horaInicio.getComponentTimeTextField().setBorder(new JTextField().getBorder());
        horaFin.getComponentTimeTextField().setBorder(new JTextField().getBorder());
        horaInicio.getComponentTimeTextField().setEditable(false);
        horaFin.getComponentTimeTextField().setEditable(false);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, 10));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Descripción:");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDescripcion = new JTextField();
        panel1.add(txtDescripcion, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Hora inicio:");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        horaInicio.setForeground(new Color(-1));
        horaInicio.setOpaque(true);
        panel1.add(horaInicio, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Hora fin:");
        panel1.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        horaFin.setForeground(new Color(-1));
        panel1.add(horaFin, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnAñadir = new JButton();
        btnAñadir.setText("Registrar");
        panel2.add(btnAñadir, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btnHecho = new JButton();
        btnHecho.setText("Hecho");
        panel2.add(btnHecho, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
