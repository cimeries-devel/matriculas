package com.devel.views.menus;

import com.devel.custom.DnDTabbedPane;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilidades;
import com.devel.views.tabs.VAlumnos;
import com.devel.views.tabs.VFamiliares;
import com.devel.views.tabs.VMatriculas;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class MenuReportes {
    private JButton btnAlumnos;
    private JPanel panelPrincipal;
    private JButton btnFamiliares;
    private JButton btnMatriculas;
    private DnDTabbedPane tabContenido;
    private Propiedades propiedades;
    private VAlumnos alumnos;
    private VFamiliares familiares;
    private VMatriculas matriculas;

    public MenuReportes(DnDTabbedPane tabContenido) {
        this.propiedades = propiedades;
        this.tabContenido = tabContenido;
        btnAlumnos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnFamiliares.setBackground(new JButton().getBackground());
                btnMatriculas.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(btnAlumnos);
                cargarAlumnos();
            }
        });
        btnFamiliares.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAlumnos.setBackground(new JButton().getBackground());
                btnMatriculas.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(btnFamiliares);
                cargarFamiliares();
            }
        });
        btnMatriculas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAlumnos.setBackground(new JButton().getBackground());
                btnFamiliares.setBackground(new JButton().getBackground());
                Utilidades.buttonSelectedOrEntered(btnMatriculas);
                cargarMatriculas();
            }
        });
    }

    public JPanel traerReportesOpciones() {
        return panelPrincipal;
    }


    public void cargarAlumnos() {
        if (alumnos == null) {
            alumnos = new VAlumnos();
        }
        if (tabContenido.indexOfComponent(alumnos.getPanelPrincipal()) == -1) {
            alumnos = new VAlumnos();
            alumnos.getPanelPrincipal().setOption(btnAlumnos);
            tabContenido.addTab(alumnos.getTitle(), alumnos.getPanelPrincipal().getIcon(), alumnos.getPanelPrincipal());
        }
        tabContenido.setSelectedComponent(alumnos.getPanelPrincipal());
    }

    public void cargarFamiliares() {
        if (familiares == null) {
            familiares = new VFamiliares();
        }
        if (tabContenido.indexOfComponent(familiares.getPanelPrincipal()) == -1) {
            familiares = new VFamiliares();
            familiares.getPanelPrincipal().setOption(btnFamiliares);
            tabContenido.addTab(familiares.getTitle(), familiares.getPanelPrincipal().getIcon(), familiares.getPanelPrincipal());

        }
        tabContenido.setSelectedComponent(familiares.getPanelPrincipal());
    }

    public void cargarMatriculas() {
        if (matriculas == null) {
            matriculas = new VMatriculas();
        }
        if (tabContenido.indexOfComponent(matriculas.getPanelPrincipal()) == -1) {
            matriculas = new VMatriculas();
            matriculas.getPanelPrincipal().setOption(btnMatriculas);
            tabContenido.addTab(matriculas.getTitle(), matriculas.getPanelPrincipal().getIcon(), matriculas.getPanelPrincipal());

        }
        tabContenido.setSelectedComponent(matriculas.getPanelPrincipal());
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
        panelPrincipal.setLayout(new GridLayoutManager(4, 1, new Insets(0, 5, 5, 5), 0, 10));
        panelPrincipal.setMaximumSize(new Dimension(140, 235));
        panelPrincipal.setMinimumSize(new Dimension(140, 220));
        panelPrincipal.setOpaque(true);
        panelPrincipal.setPreferredSize(new Dimension(140, 220));
        btnAlumnos = new JButton();
        btnAlumnos.setFocusPainted(false);
        Font btnAlumnosFont = this.$$$getFont$$$(null, -1, 14, btnAlumnos.getFont());
        if (btnAlumnosFont != null) btnAlumnos.setFont(btnAlumnosFont);
        btnAlumnos.setText("Alumnos");
        panelPrincipal.add(btnAlumnos, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelPrincipal.add(spacer1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        btnFamiliares = new JButton();
        btnFamiliares.setFocusPainted(false);
        Font btnFamiliaresFont = this.$$$getFont$$$(null, -1, 14, btnFamiliares.getFont());
        if (btnFamiliaresFont != null) btnFamiliares.setFont(btnFamiliaresFont);
        btnFamiliares.setText("Familiares");
        panelPrincipal.add(btnFamiliares, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnMatriculas = new JButton();
        btnMatriculas.setFocusPainted(false);
        Font btnMatriculasFont = this.$$$getFont$$$(null, -1, 14, btnMatriculas.getFont());
        if (btnMatriculasFont != null) btnMatriculas.setFont(btnMatriculasFont);
        btnMatriculas.setText("Matriculas");
        panelPrincipal.add(btnMatriculas, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
