package com.devel.views.tabs;

import com.devel.Principal;
import com.devel.custom.TabPanel;
import com.devel.models.*;
import com.devel.utilities.JButoonEditors.JButtonActionAlumnos;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.TablecellRendered.AlumnosCellRendered;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.AlumnosAbstractModel;
import com.devel.views.VPrincipal;
import com.devel.views.dialogs.Exportar.ExportarAlumnos;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class VAlumnos extends JFrame {
    private TabPanel panelPrincipal;
    private JTextField txtCodigo;
    private JCheckBox checkSoloMatriculados;
    private JComboBox cbbSeguros;
    private JButton btnExportar;
    private JButton limpiarFiltrosButton;
    private JTable tablaAlumnos;
    private JComboBox cbbNiveles;
    private JComboBox cbbGrados;
    private JComboBox cbbSecciones;
    private JTextField txtNombres;
    private JButton exportarButton;
    private AlumnosAbstractModel matriculasAbstractModel;
    private TableRowSorter<AlumnosAbstractModel> modeloOrdenado;
    private List<RowFilter<AlumnosAbstractModel, String>> filtros = new ArrayList<>();
    private RowFilter filtroand;

    private Map<Integer, String> listaFiltros = new HashMap<Integer, String>();

    public VAlumnos() {
        iniciarComponentes();
        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                actualizar();
            }
        });
        txtNombres.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                actualizar();
            }
        });
        cbbSeguros.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                actualizar();
            }
        });
        cbbNiveles.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cargarGradosPorNivel();
                actualizar();
            }
        });
        cbbGrados.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                actualizar();
            }
        });
        cbbSecciones.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                actualizar();
            }
        });
        limpiarFiltrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFiltros();
                actualizar();
            }
        });
        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarRelacionAlumnos();
            }
        });
        checkSoloMatriculados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar();
            }
        });
    }

    public TabPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    private void iniciarComponentes() {
        setTitle("Alumnos");
        panelPrincipal.setIcon(new ImageIcon(Principal.class.getResource("Icons/x24/inicio.png")));
        cargarAlumnos(VPrincipal.alumnos);
        cargarComboBox();
    }

    private void cargarAlumnos(Vector<Persona> alumnos) {
        matriculasAbstractModel = new AlumnosAbstractModel(alumnos);
        tablaAlumnos.setModel(matriculasAbstractModel);
        tablaAlumnos.getColumnModel().getColumn(matriculasAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonActionAlumnos(tablaAlumnos));
        modeloOrdenado = new TableRowSorter<>(matriculasAbstractModel);
        tablaAlumnos.setRowSorter(modeloOrdenado);
        TableCellRenderer renderer1 = tablaAlumnos.getDefaultRenderer(JButton.class);
        tablaAlumnos.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        AlumnosCellRendered.setCellRenderer(listaFiltros, tablaAlumnos);
        Utilidades.headerNegrita(tablaAlumnos);
    }

    private void cargarGradosPorNivel() {
        if (((Nivel) cbbNiveles.getSelectedItem()).getId() != null) {
            cbbGrados.setModel(new DefaultComboBoxModel(new Vector(((Nivel) cbbNiveles.getSelectedItem()).getGradosConTodos())));
            cbbGrados.setRenderer(new Grado.ListCellRenderer());
        } else {
            cbbGrados.setModel(new DefaultComboBoxModel(VPrincipal.gradosConTodos));
            cbbGrados.setRenderer(new Grado.ListCellRenderer());
        }
    }

    private void exportarRelacionAlumnos() {
        ExportarAlumnos exportarAlumnos = new ExportarAlumnos(tablaAlumnos);
        exportarAlumnos.setVisible(true);
    }

    private void cargarComboBox() {
        cbbNiveles.setModel(new DefaultComboBoxModel(VPrincipal.nivelesConTodos));
        cbbNiveles.setRenderer(new Nivel.ListCellRenderer());
        cbbGrados.setModel(new DefaultComboBoxModel(VPrincipal.gradosConTodos));
        cbbGrados.setRenderer(new Grado.ListCellRenderer());
        cbbSecciones.setModel(new DefaultComboBoxModel(VPrincipal.seccionesConTodos));
        cbbSecciones.setRenderer(new Seccion.ListCellRenderer());
        cbbSeguros.setModel(new DefaultComboBoxModel(VPrincipal.segurosConTodos));
        cbbSeguros.setRenderer(new Seguro.ListCellRenderer());
    }

    public void actualizar() {
        filtros.clear();
        String busqueda;

        busqueda = txtCodigo.getText().trim().toUpperCase();
        filtros.add(RowFilter.regexFilter(busqueda, 0));
        listaFiltros.put(0, busqueda);

        busqueda = txtNombres.getText().trim().toUpperCase();
        filtros.add(RowFilter.regexFilter(busqueda, 1));
        listaFiltros.put(1, busqueda);

        if (checkSoloMatriculados.isSelected()) {
            String año = Utilidades.año.format(new Date());
            filtros.add(RowFilter.regexFilter(año, 8));
        }
        if (((Seguro) cbbSeguros.getSelectedItem()).getId() != null) {
            Seguro seguro = (Seguro) cbbSeguros.getSelectedItem();
            filtros.add(RowFilter.regexFilter(seguro.getCodigo(), 3));
        }
        if (((Nivel) cbbNiveles.getSelectedItem()).getId() != null) {
            Nivel nivel = (Nivel) cbbNiveles.getSelectedItem();
            filtros.add(RowFilter.regexFilter(nivel.getDescripcion(), 4));
        }
        if (((Grado) cbbGrados.getSelectedItem()).getId() != null) {
            Grado grado = (Grado) cbbGrados.getSelectedItem();
            filtros.add(RowFilter.regexFilter(grado.getGrado(), 5));
        }
        if (((Seccion) cbbSecciones.getSelectedItem()).getId() != null) {
            Seccion seccion = (Seccion) cbbSecciones.getSelectedItem();
            filtros.add(RowFilter.regexFilter(seccion.getSeccion(), 6));
        }

        filtroand = RowFilter.andFilter(filtros);
        modeloOrdenado.setRowFilter(filtroand);
    }

    private void limpiarFiltros() {
        txtNombres.setText(null);
        txtCodigo.setText(null);
        checkSoloMatriculados.setSelected(false);
        cbbSeguros.setSelectedIndex(0);
        cbbNiveles.setSelectedIndex(0);
        cbbGrados.setSelectedIndex(0);
        cbbSecciones.setSelectedIndex(0);
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel1.setMinimumSize(new Dimension(96, 86));
        panel1.setPreferredSize(new Dimension(96, 86));
        panelPrincipal = new TabPanel();
        panelPrincipal.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panelPrincipal, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 4, new Insets(5, 5, 5, 5), 25, -1));
        panelPrincipal.add(panel2, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(null, "Busqueda", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 5, 5));
        panel2.add(panel3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        txtCodigo = new JTextField();
        txtCodigo.setSelectionEnd(0);
        txtCodigo.setSelectionStart(0);
        txtCodigo.setText("");
        panel3.add(txtCodigo, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Código:");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 5, 5));
        panel2.add(panel4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        txtNombres = new JTextField();
        panel4.add(txtNombres, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(250, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Nombres y apellidos:");
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(7, 2, new Insets(5, 5, 5, 5), 2, 5));
        panelPrincipal.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(null, "Filtros", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        checkSoloMatriculados = new JCheckBox();
        Font checkSoloMatriculadosFont = this.$$$getFont$$$(null, -1, 14, checkSoloMatriculados.getFont());
        if (checkSoloMatriculadosFont != null) checkSoloMatriculados.setFont(checkSoloMatriculadosFont);
        checkSoloMatriculados.setHorizontalAlignment(0);
        checkSoloMatriculados.setHorizontalTextPosition(2);
        checkSoloMatriculados.setText("Solo matriculados");
        panel5.add(checkSoloMatriculados, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel5.add(spacer3, new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Nivel:");
        panel5.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbNiveles = new JComboBox();
        panel5.add(cbbNiveles, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, -1, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Grado:");
        panel5.add(label4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbGrados = new JComboBox();
        panel5.add(cbbGrados, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$(null, -1, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Sección:");
        panel5.add(label5, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbSecciones = new JComboBox();
        panel5.add(cbbSecciones, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        limpiarFiltrosButton = new JButton();
        limpiarFiltrosButton.setText("Limpiar filtros");
        panel5.add(limpiarFiltrosButton, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$(null, -1, 14, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("Seguro:");
        panel5.add(label6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbSeguros = new JComboBox();
        panel5.add(cbbSeguros, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, 5));
        panelPrincipal.add(panel6, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(null, "Alumnos", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel6.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablaAlumnos = new JTable();
        tablaAlumnos.setRowHeight(25);
        tablaAlumnos.setRowSelectionAllowed(true);
        tablaAlumnos.setShowVerticalLines(true);
        scrollPane1.setViewportView(tablaAlumnos);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), -1, -1));
        panelPrincipal.add(panel7, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(null, "Acciones", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer4 = new Spacer();
        panel7.add(spacer4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        btnExportar = new JButton();
        btnExportar.setText("Exportar");
        panel7.add(btnExportar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

}

