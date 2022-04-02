package com.devel.views.tabs;

import com.devel.Principal;
import com.devel.custom.TabPanel;
import com.devel.models.*;
import com.devel.utilities.JButoonEditors.JButtonActionAlumnos;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.TablecellRendered.TodosLosFamiliaresCellRendered;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.TodosLosFamiliaresAbstractModel;
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

public class VFamiliares extends JFrame {
    private JPanel panel1;
    private TabPanel panelPrincipal;
    private JTextField txtDni;
    private JTextField txtNombresYApellidos;
    private JButton btnExportar;
    private JTable tablaFamiliares;
    private JCheckBox checkSoloApoderados;
    private JButton limpiarFiltrosButton;
    private JComboBox cbbTipoFamiliares;
    private Map<Integer, String> listaFiltros = new HashMap<Integer, String>();
    private TodosLosFamiliaresAbstractModel todosLosFamiliaresAbstractModel;
    private TableRowSorter<TodosLosFamiliaresAbstractModel> modeloOrdenado;
    private List<RowFilter<TodosLosFamiliaresAbstractModel, String>> filtros = new ArrayList<>();
    private RowFilter filtroand;

    public VFamiliares() {
        iniciarComponentes();
        txtDni.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                actualizar();
            }
        });
        txtNombresYApellidos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                actualizar();
            }
        });
        cbbTipoFamiliares.addItemListener(new ItemListener() {
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
        checkSoloApoderados.addActionListener(new ActionListener() {
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
        setTitle("Familiares");
        panelPrincipal.setIcon(new ImageIcon(Principal.class.getResource("Icons/x24/inicio.png")));
        cargarFamiliares(VPrincipal.familiares);
        cargarComboBox();
    }

    private void cargarFamiliares(Vector<Persona> familiares) {
        todosLosFamiliaresAbstractModel = new TodosLosFamiliaresAbstractModel(familiares);
        tablaFamiliares.setModel(todosLosFamiliaresAbstractModel);
//        tablaFamiliares.getColumnModel().getColumn(todosLosFamiliaresAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonActionAlumnos(tablaFamiliares));
        modeloOrdenado = new TableRowSorter<>(todosLosFamiliaresAbstractModel);
        tablaFamiliares.setRowSorter(modeloOrdenado);
        TableCellRenderer renderer1 = tablaFamiliares.getDefaultRenderer(JButton.class);
        tablaFamiliares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        TodosLosFamiliaresCellRendered.setCellRenderer(listaFiltros, tablaFamiliares);
        Utilidades.headerNegrita(tablaFamiliares);
    }

    private void exportarRelacionAlumnos() {
        ExportarAlumnos exportarAlumnos = new ExportarAlumnos(tablaFamiliares);
        exportarAlumnos.setVisible(true);
    }

    private void cargarComboBox() {
        cbbTipoFamiliares.setModel(new DefaultComboBoxModel(VPrincipal.tipoRelacionesConTodos));
        cbbTipoFamiliares.setRenderer(new TipoRelacion.ListCellRenderer());
    }

    public void actualizar() {
        filtros.clear();
        String busqueda;

        busqueda = txtDni.getText().trim().toUpperCase();
        filtros.add(RowFilter.regexFilter(busqueda, 0));
        listaFiltros.put(0, busqueda);

        busqueda = txtNombresYApellidos.getText().trim().toUpperCase();
        filtros.add(RowFilter.regexFilter(busqueda, 1));
        listaFiltros.put(1, busqueda);

        if (checkSoloApoderados.isSelected()) {
            String año = Utilidades.año.format(new Date());
            filtros.add(RowFilter.regexFilter(año, 8));
        }
        filtroand = RowFilter.andFilter(filtros);
        modeloOrdenado.setRowFilter(filtroand);
    }

    private void limpiarFiltros() {
        checkSoloApoderados.setSelected(false);
        txtDni.setText(null);
        txtNombresYApellidos.setText(null);
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
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
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
        txtDni = new JTextField();
        txtDni.setSelectionEnd(0);
        txtDni.setSelectionStart(0);
        txtDni.setText("");
        panel3.add(txtDni, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("DNI");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 5, 5));
        panel2.add(panel4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        txtNombresYApellidos = new JTextField();
        panel4.add(txtNombresYApellidos, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(250, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Nombres y apellidos:");
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), -1, -1));
        panelPrincipal.add(panel5, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(null, "Acciones", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer3 = new Spacer();
        panel5.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        btnExportar = new JButton();
        btnExportar.setText("Exportar");
        panel5.add(btnExportar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, 5));
        panelPrincipal.add(panel6, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(null, "Familiares", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel6.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablaFamiliares = new JTable();
        tablaFamiliares.setRowHeight(25);
        tablaFamiliares.setRowSelectionAllowed(true);
        tablaFamiliares.setShowVerticalLines(true);
        scrollPane1.setViewportView(tablaFamiliares);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(4, 2, new Insets(5, 5, 5, 5), 2, 5));
        panelPrincipal.add(panel7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(null, "Filtros", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        checkSoloApoderados = new JCheckBox();
        Font checkSoloApoderadosFont = this.$$$getFont$$$(null, -1, 14, checkSoloApoderados.getFont());
        if (checkSoloApoderadosFont != null) checkSoloApoderados.setFont(checkSoloApoderadosFont);
        checkSoloApoderados.setHorizontalAlignment(0);
        checkSoloApoderados.setHorizontalTextPosition(2);
        checkSoloApoderados.setText("Solo apoderados");
        panel7.add(checkSoloApoderados, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel7.add(spacer4, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        limpiarFiltrosButton = new JButton();
        limpiarFiltrosButton.setText("Limpiar filtros");
        panel7.add(limpiarFiltrosButton, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Tipo:");
        panel7.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbTipoFamiliares = new JComboBox();
        panel7.add(cbbTipoFamiliares, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return panel1;
    }
}
