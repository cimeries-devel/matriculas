package com.devel.views.tabs;

import com.devel.Principal;
import com.devel.controllers.Documentos;
import com.devel.controllers.Tarifas;
import com.devel.custom.TabPanel;
import com.devel.models.*;
import com.devel.utilities.JButoonEditors.JButtonEditorCelulares;
import com.devel.utilities.JButoonEditors.JButtonEditorFamiliares;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.TablecellRendered.CelularesCellRendered;
import com.devel.utilities.TablecellRendered.FamiliaresCellRendered;
import com.devel.utilities.TablecellRendered.MatriculaCellRendered;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.MatriculaAbstractModel;
import com.devel.utilities.modelosTablas.CelularesAbstractModel;
import com.devel.utilities.modelosTablas.FamiliaresAbstractModel;
import com.devel.validators.RegistroValidator;
import com.devel.views.VPrincipal;
import com.devel.views.dialogs.DAñadirCelular;
import com.devel.views.dialogs.DAñadirFamiliar;
import com.devel.views.dialogs.DAñadirEstudiante;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

public class VMatricula extends JFrame {
    private TabPanel panelPrincipal;
    private JTable tablaFamiliares;
    private JTable tablaMatriculas;
    private JComboBox cbbNiveles;
    private JTextField txtDni;
    private JButton buscarButton;
    private JButton btnNuevoEstudiante;
    private JButton btnRegistrarMatricula;
    private JButton btnAñadirFamiliar;
    private JLabel lblNombres;
    private JCheckBox matriculadoCheckBox;
    private JLabel lblCodigo;
    private JTable tablaCelulares;
    private JButton btnAñadirCelular;
    private JComboBox cbbGrados;
    private JComboBox cbbTarifas;
    private JComboBox cbbSalones;
    private JComboBox cbbSecciones;
    private JLabel lblMonto;
    private FamiliaresAbstractModel familiaresAbstractModel;
    private DateFormat año = new SimpleDateFormat("yyyy");
    private MatriculaAbstractModel matriculadosAbstractModel;
    private CelularesAbstractModel modelCelulares;
    private NumberFormat sol = NumberFormat.getCurrencyInstance();
    private Persona persona;
    private Registro registro;

    public VMatricula() {
        persona = new Persona();
        iniciarComponentes();
        btnNuevoEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarNuevoEstudiante();
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarAlumno();
            }
        });
        cbbNiveles.addActionListener(evt -> {
            cargarNiveles();
        });
        cbbTarifas.addActionListener(e -> {
            cargarMonto();
        });
        btnAñadirFamiliar.addActionListener(e -> {
            cargarAgregarFamiliar();
        });
        btnAñadirCelular.addActionListener(e -> {
            cargarAgregarCelular();
        });
        matriculadoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarMatricula();
            }
        });
        btnRegistrarMatricula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarMatricula();
            }
        });
    }

    public TabPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    private void cargarNuevoEstudiante() {
        DAñadirEstudiante dAñadirEstudiante = new DAñadirEstudiante();
        dAñadirEstudiante.setVisible(true);
    }

    private void cargarAgregarFamiliar() {
        DAñadirFamiliar dAñadirFamiliar = new DAñadirFamiliar(persona);
        dAñadirFamiliar.setVisible(true);
        Utilidades.actualizarTabla(tablaFamiliares);
    }

    private void cargarMonto() {
        if (cbbTarifas.getItemCount() > 0) {
            if (cbbTarifas.getSelectedItem() != null) {
                lblMonto.setText(sol.format(((Tarifa) cbbTarifas.getSelectedItem()).getPrecio()));
            }
        }
    }

    private void cargarNiveles() {
        if (cbbNiveles.getItemCount() > 0) {
            if (cbbNiveles.getSelectedItem() != null) {
                cargarGradosPorNivel();
            }
        }
    }

    private void buscarAlumno() {
        if (txtDni.getText().length() >= 8) {
            Documento dni = Documentos.getByDni(txtDni.getText().trim());
            if (dni != null) {
                if (dni.getPerson().getCodigo() != null) {
                    persona = dni.getPerson();
                    cargarEstudiante();
                    cargarTablasDelEstudiante();
                    botones(true);
                } else {
                    persona = new Persona();
                    lblNombres.setText("--");
                    lblCodigo.setText("--");
                    cargarTablasDelEstudiante();
                    botones(false);
                    Utilidades.sendNotification("Error", "No es estudiante", TrayIcon.MessageType.ERROR);
                }
            } else {
                Utilidades.sendNotification("No hay datos", "Alumno no encontrado", TrayIcon.MessageType.INFO);
                btnAñadirFamiliar.removeAll();
            }
        } else {
            Utilidades.sendNotification("Error", "Ingrese el dni", TrayIcon.MessageType.ERROR);
        }
    }

    private void registrarMatricula() {
        if (!matriculadoCheckBox.isSelected()) {
            int sioNo = JOptionPane.showOptionDialog(null, "¿Está seguro?", "Confirmar matrícula", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
            if (sioNo == 0) {
                registro = new Registro();
                Tarifa tarifa = (Tarifa) cbbTarifas.getSelectedItem();
                Salon salon = (Salon) cbbSalones.getSelectedItem();

                registro.setCreacion(new Date());
                registro.setActualizacion(new Date());
                registro.setEstudiante(persona);
                registro.setSalon(salon);
                registro.setTarifa(tarifa);

                Set<ConstraintViolation<Registro>> errors = RegistroValidator.loadViolations(registro);
                if (errors.isEmpty()) {
                    registro.guardar();
                    if (persona.getRegistros().isEmpty()) {
                        VPrincipal.alumnos.add(persona);
                    }
                    persona.getRegistros().add(registro);
                    VPrincipal.alumnosMatriculados.add(registro);
                    Utilidades.actualizarTabla(tablaMatriculas);
                    registro = new Registro();
                    persona = new Persona();
                    limpiarControles();
                    Utilidades.sendNotification("Éxito", "Matricula registrada", TrayIcon.MessageType.INFO);
                } else {
                    RegistroValidator.mostrarErrores(errors);
                }
            }
        } else {
            Utilidades.sendNotification("Error", "El alumno ya se encuentra matriculado", TrayIcon.MessageType.ERROR);
        }

    }

    private void verificarMatricula() {
        if (!persona.getRegistros().isEmpty()) {
            Registro registro = persona.getRegistros().get(persona.getRegistros().size() - 1);
            if (año.format(registro.getActualizacion().getTime()).equals(año.format(new Date()))) {
                matriculadoCheckBox.setSelected(true);
            } else {
                matriculadoCheckBox.setSelected(false);
            }
        } else {
            matriculadoCheckBox.setSelected(false);
        }
    }

    private void cargarGradosPorNivel() {
        if (cbbNiveles.getSelectedItem() != null) {
            cbbGrados.setModel(new DefaultComboBoxModel(new Vector(((Nivel) cbbNiveles.getSelectedItem()).getGrados())));
            cbbGrados.setRenderer(new Grado.ListCellRenderer());
        }
    }

    private void iniciarComponentes() {
        setTitle("Matrícula");
        panelPrincipal.setIcon(new ImageIcon(Principal.class.getResource("Icons/x24/inicio.png")));
        cargarMatriculas();
        cargarComboBox();
        cargarTarifaPorDefecto();
        cargarGradosPorNivel();
        cargarTablasDelEstudiante();
        botones(false);
        panelPrincipal.setTable(tablaMatriculas);
    }

    private void cargarTablasDelEstudiante() {
        cargarTablaFamiliares();
        cargarTablaCelulares();
        verificarMatricula();
    }

    private void cargarEstudiante() {
        lblNombres.setText(persona.getNombres() + " " + persona.getApellidos());
        lblCodigo.setText(persona.getCodigo());
        if (!persona.getRegistros().isEmpty()) {
            cbbTarifas.setSelectedItem(persona.getRegistros().get(persona.getRegistros().size() - 1).getTarifa());
            cbbNiveles.setSelectedItem(persona.getRegistros().get(persona.getRegistros().size() - 1).getSalon().getNivel());
            cbbGrados.setSelectedItem(persona.getRegistros().get(persona.getRegistros().size() - 1).getSalon().getGrado());
            cbbSecciones.setSelectedItem(persona.getRegistros().get(persona.getRegistros().size() - 1).getSalon().getSeccion());
        }
    }

    private void cargarTarifaPorDefecto() {
        if (!VPrincipal.tarifas.isEmpty()) {
            cbbTarifas.setSelectedItem(Tarifas.tarifaActiva());
            lblMonto.setText(sol.format(((Tarifa) cbbTarifas.getSelectedItem()).getPrecio()));
        }
    }

    private void cargarComboBox() {
        cbbNiveles.setModel(new DefaultComboBoxModel(VPrincipal.niveles));
        cbbNiveles.setRenderer(new Nivel.ListCellRenderer());
        cbbGrados.setModel(new DefaultComboBoxModel(VPrincipal.grados));
        cbbGrados.setRenderer(new Grado.ListCellRenderer());
        cbbTarifas.setModel(new DefaultComboBoxModel(VPrincipal.tarifas));
        cbbTarifas.setRenderer(new Tarifa.ListCellRenderer());
        cbbSecciones.setModel(new DefaultComboBoxModel(VPrincipal.secciones));
        cbbSecciones.setRenderer(new Seccion.ListCellRenderer());
        cbbSalones.setModel(new DefaultComboBoxModel(VPrincipal.salones));
        cbbSalones.setRenderer(new Salon.ListCellRenderer());
    }

    private void cargarMatriculas() {
        matriculadosAbstractModel = new MatriculaAbstractModel(VPrincipal.alumnosMatriculados);
        tablaMatriculas.setModel(matriculadosAbstractModel);
        MatriculaCellRendered.setCellRenderer(tablaMatriculas);
        Utilidades.headerNegrita(tablaMatriculas);
    }

    private void cargarTablaFamiliares() {
        familiaresAbstractModel = new FamiliaresAbstractModel(persona.getFamiliaresparaEstudiante());
        tablaFamiliares.setModel(familiaresAbstractModel);
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonEditorFamiliares(persona.getFamiliaresparaEstudiante(), tablaFamiliares, "editar"));
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 2).setCellEditor(new JButtonEditorFamiliares(persona.getFamiliaresparaEstudiante(), tablaFamiliares, "apoderado"));
        TableCellRenderer renderer1 = tablaFamiliares.getDefaultRenderer(JButton.class);
        tablaFamiliares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        FamiliaresCellRendered.setCellRenderer(tablaFamiliares, persona.getFamiliaresparaEstudiante());
        Utilidades.headerNegrita(tablaFamiliares);
    }

    private void cargarTablaCelulares() {
        modelCelulares = new CelularesAbstractModel(persona.getCelulares());
        tablaCelulares.setModel(modelCelulares);
        tablaCelulares.getColumnModel().getColumn(modelCelulares.getColumnCount() - 1).setCellEditor(new JButtonEditorCelulares(tablaCelulares));
        TableCellRenderer renderer1 = tablaCelulares.getDefaultRenderer(JButton.class);
        tablaCelulares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        CelularesCellRendered.setCellRenderer(tablaCelulares);
        Utilidades.headerNegrita(tablaCelulares);
    }

    private void cargarAgregarCelular() {
        DAñadirCelular dañadirCelular = new DAñadirCelular(persona);
        dañadirCelular.setVisible(true);
        Utilidades.actualizarTabla(tablaCelulares);
    }

    private void limpiarControles() {
        txtDni.setText(null);
        lblCodigo.setText("--");
        lblNombres.setText("--");
        cargarTablasDelEstudiante();
        botones(false);
    }

    private void botones(boolean estado) {
        btnRegistrarMatricula.setEnabled(estado);
        btnAñadirFamiliar.setEnabled(estado);
        btnAñadirCelular.setEnabled(estado);
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
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), 10, 10));
        panelPrincipal = new TabPanel();
        panelPrincipal.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), 5, 5));
        panel1.add(panelPrincipal, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(7, 4, new Insets(5, 5, 5, 5), 5, 5));
        panelPrincipal.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 270), new Dimension(-1, 270), new Dimension(-1, 270), 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(null, "Matrícula", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("DNi:");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDni = new JTextField();
        panel2.add(txtDni, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Alumno:");
        panel2.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Nivel:");
        panel2.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbNiveles = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        cbbNiveles.setModel(defaultComboBoxModel1);
        panel2.add(cbbNiveles, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, -1, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Pago por:");
        panel2.add(label4, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbTarifas = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        cbbTarifas.setModel(defaultComboBoxModel2);
        panel2.add(cbbTarifas, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$(null, -1, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Monto:");
        panel2.add(label5, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$(null, -1, 14, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("Código:");
        panel2.add(label6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnRegistrarMatricula = new JButton();
        btnRegistrarMatricula.setEnabled(true);
        btnRegistrarMatricula.setText("Registrar Matricula");
        panel2.add(btnRegistrarMatricula, new GridConstraints(6, 1, 1, 3, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buscarButton = new JButton();
        buscarButton.setText("Buscar");
        panel2.add(buscarButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnNuevoEstudiante = new JButton();
        btnNuevoEstudiante.setText("Nuevo");
        panel2.add(btnNuevoEstudiante, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        matriculadoCheckBox = new JCheckBox();
        matriculadoCheckBox.setEnabled(true);
        matriculadoCheckBox.setFocusPainted(false);
        matriculadoCheckBox.setHorizontalTextPosition(2);
        matriculadoCheckBox.setRequestFocusEnabled(true);
        matriculadoCheckBox.setText("Matriculado");
        matriculadoCheckBox.setVisible(true);
        panel2.add(matriculadoCheckBox, new GridConstraints(1, 2, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblNombres = new JLabel();
        lblNombres.setText("--");
        panel2.add(lblNombres, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        lblCodigo = new JLabel();
        lblCodigo.setText("--");
        panel2.add(lblCodigo, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$(null, -1, 14, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setText("Grado:");
        panel2.add(label7, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbGrados = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        cbbGrados.setModel(defaultComboBoxModel3);
        panel2.add(cbbGrados, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$(null, -1, 14, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setText("Sección:");
        panel2.add(label8, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbSecciones = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        cbbSecciones.setModel(defaultComboBoxModel4);
        panel2.add(cbbSecciones, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        Font label9Font = this.$$$getFont$$$(null, -1, 14, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setText("Salón:");
        panel2.add(label9, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbSalones = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        cbbSalones.setModel(defaultComboBoxModel5);
        panel2.add(cbbSalones, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblMonto = new JLabel();
        Font lblMontoFont = this.$$$getFont$$$(null, -1, 14, lblMonto.getFont());
        if (lblMontoFont != null) lblMonto.setFont(lblMontoFont);
        lblMonto.setText("S/ 0.0");
        panel2.add(lblMonto, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panel3, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablaMatriculas = new JTable();
        tablaMatriculas.setRowHeight(25);
        scrollPane1.setViewportView(tablaMatriculas);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), 5, 5));
        panelPrincipal.add(panel4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 270), new Dimension(-1, 270), new Dimension(-1, 270), 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(null, "Celulares", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        btnAñadirCelular = new JButton();
        btnAñadirCelular.setEnabled(true);
        btnAñadirCelular.setText("Añadir celular");
        panel4.add(btnAñadirCelular, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel4.add(scrollPane2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tablaCelulares = new JTable();
        tablaCelulares.setRowHeight(25);
        scrollPane2.setViewportView(tablaCelulares);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), 5, 5));
        panelPrincipal.add(panel5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 270), new Dimension(-1, 270), new Dimension(-1, 270), 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(null, "Familiares", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        btnAñadirFamiliar = new JButton();
        btnAñadirFamiliar.setEnabled(true);
        btnAñadirFamiliar.setFocusable(true);
        btnAñadirFamiliar.setText("Nuevo familiar");
        panel5.add(btnAñadirFamiliar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel5.add(scrollPane3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tablaFamiliares = new JTable();
        tablaFamiliares.setRowHeight(25);
        scrollPane3.setViewportView(tablaFamiliares);
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
