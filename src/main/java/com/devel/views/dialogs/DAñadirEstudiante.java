package com.devel.views.dialogs;
import com.devel.controllers.Documentos;
import com.devel.controllers.Personas;
import com.devel.models.*;
import com.devel.utilities.JButoonEditors.*;
import com.devel.utilities.TablecellRendered.*;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.*;
import com.devel.validators.DocumentoValidator;
import com.devel.validators.PersonaValidator;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.DatePicker;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

public class DAñadirEstudiante extends JDialog {
    private JTextField txtNombres;
    private JComboBox cbbTipoDocumento;
    private DatePicker pickerEdad;
    private JLabel labelEdad;
    private JPanel panelPrincipal;
    private JButton btnAñadir;
    private JButton btnHecho;
    private JTextField txtDni;
    private JTextField txtApellidos;
    private JTextField txtEmail;
    private JTextField txtCodigo;
    private JComboBox cbbGenero;
    private JComboBox cbbSeguro;
    private JButton btnNuevoSeguro;
    private JTabbedPane tabbedPane1;
    private JButton btnNuevoCelular;
    private JTable tablaCelulares;
    private JButton btnNuevoFamiliar;
    private JTable tablaFamiliares;
    private JTable tablaSeguros;
    private JButton btnAñadirDocumento;
    private JTable tablaDocumentos;
    private JButton btnAñadirSeguro;
    private Persona persona;
    private FamiliaresAbstractModel familiaresAbstractModel;
    private CelularesAbstractModel celularesAbstractModel;
    private SeguroPersonaAbstractModel segurosAbstractModel;
    private DocumentoAbstractModel documentoAbstractModel;
    private java.util.Date nacimiento;

    public DAñadirEstudiante(Persona persona) {
        this.persona = persona;
        $$$setupUI$$$();
        iniciarComponentes();
        paraActualizar();
        pickerEdad.addDateChangeListener(dateChangeEvent -> {
            if (pickerEdad.getDate() != null) {
                labelEdad.setText(String.valueOf(Utilidades.calcularaños(Date.valueOf(pickerEdad.getDate()))));
            }
        });
        btnAñadir.addActionListener(e -> actualizar());
        btnHecho.addActionListener(e -> onCancel());
        btnNuevoFamiliar.addActionListener(e -> cargarAgregarFamiliar());
        btnNuevoCelular.addActionListener(e -> cargarAgregarCelular());
        btnAñadirDocumento.addActionListener(e -> cargarAgregarDocumento());
        btnAñadirSeguro.addActionListener(e -> cargarAgregarSeguro());
        panelPrincipal.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    public DAñadirEstudiante() {
        persona = new Persona();
        $$$setupUI$$$();
        iniciarComponentes();
        pickerEdad.addDateChangeListener(dateChangeEvent -> {
            if (pickerEdad.getDate() != null) {
                labelEdad.setText(String.valueOf(Utilidades.calcularaños(Date.valueOf(pickerEdad.getDate()))));
            }
        });
        btnAñadir.addActionListener(e -> registrar());
        btnHecho.addActionListener(e -> cerrar());
        btnNuevoFamiliar.addActionListener(e -> cargarAgregarFamiliar());
        btnNuevoCelular.addActionListener(e -> cargarAgregarCelular());
        btnAñadirDocumento.addActionListener(e -> cargarAgregarDocumento());
        btnAñadirSeguro.addActionListener(e -> cargarAgregarSeguro());
        panelPrincipal.registerKeyboardAction(e -> cerrar(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


    }

    private void registrar() {
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        java.util.Date nacimiento = pickerEdad.getDate() == null ? null : Utilidades.localDateToDate(pickerEdad.getDate());
        int edad = Integer.parseInt(labelEdad.getText());
        boolean genero = cbbGenero.getSelectedIndex() == 0;
        String codigo = txtCodigo.getText().trim();
        String email = txtEmail.getText().trim();

        persona.setNombres(nombres);
        persona.setApellidos(apellidos);
        persona.setCumpleaños(nacimiento);
        persona.setEdad(edad);
        persona.setGenero(genero);
        persona.setCodigo(codigo);
        persona.setEmail(email);
        persona.setActualizacion(new java.util.Date());
        persona.setCreacion(new java.util.Date());
        PersonaValidator validator = new PersonaValidator();
        Set<ConstraintViolation<Persona>> errors = validator.loadViolations(persona);
        if (errors.isEmpty()) {
            if (persona.getCodigo().length() >= 4) {
                if (!Personas.codigoRegistrado(persona.getCodigo())) {
                    if (persona.getDocumentos().isEmpty()) {
                        Utilidades.sendNotification("Error", "Debe registrar al menos un documento de identificación", TrayIcon.MessageType.WARNING);
                    } else if (persona.getApoderado() == null) {
                        Utilidades.sendNotification("Error", "Debe registrar un apoderado", TrayIcon.MessageType.WARNING);
                    } else {
                        for (Celular celular : persona.getCelulares()) {
                            celular.guardar();
                        }
                        persona.guardar();
                        for (Relacion relacion : persona.getFamiliaresparaEstudiante()) {
                            relacion.getPersona1().getCelulares().get(0).guardar();
                            relacion.getPersona1().guardar();
                            relacion.getPersona1().getDocumentos().get(0).guardar();
                            relacion.guardar();
                        }
                        for (Documento documento : persona.getDocumentos()) {
                            documento.guardar();
                        }
                        VPrincipal.alumnos.add(persona);
                        persona = new Persona();
                        cargarTablas();
                        Utilidades.sendNotification("Éxito", "Alumno registrado", TrayIcon.MessageType.INFO);
                        limpiarControles();
                    }
                } else {
                    Utilidades.sendNotification("Error", "Código ya registrado", TrayIcon.MessageType.ERROR);
                }
            } else {
                Utilidades.sendNotification("Error", "Verifique el campo Código", TrayIcon.MessageType.WARNING);
            }
        } else {
            PersonaValidator.mostrarErrores(errors);
        }
    }

    private void actualizar() {
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        java.util.Date nacimiento = pickerEdad.getDate() == null ? null : Utilidades.localDateToDate(pickerEdad.getDate());
        int edad = Integer.parseInt(labelEdad.getText());
        boolean genero = cbbGenero.getSelectedIndex() == 0;
        String codigo = txtCodigo.getText().trim();
        String email = txtEmail.getText().trim();

        persona.setNombres(nombres);
        persona.setApellidos(apellidos);
        persona.setCumpleaños(nacimiento);
        persona.setEdad(edad);
        persona.setGenero(genero);
        persona.setCodigo(codigo);
        persona.setEmail(email);

        PersonaValidator validator = new PersonaValidator();
        Set<ConstraintViolation<Persona>> errors = validator.loadViolations(persona);
        if (errors.isEmpty()) {
            if (persona.getCodigo().length() >= 4) {
                if (persona.getCodigo().equals(txtCodigo.getName())) {
                    if (persona.getApoderado() == null) {
                        Utilidades.sendNotification("Error", "Debe registrar un apoderado", TrayIcon.MessageType.WARNING);
                    } else {
                        persona.guardar();
                        cerrar();
                        Utilidades.sendNotification("Éxito", "Cambios guardados", TrayIcon.MessageType.INFO);
                    }
                } else {
                    if (!Personas.codigoRegistrado(persona.getCodigo())) {
                        if (persona.getApoderado() == null) {
                            Utilidades.sendNotification("Error", "Debe registrar un apoderado", TrayIcon.MessageType.WARNING);
                        } else {
                            persona.guardar();
                            cerrar();
                            Utilidades.sendNotification("Éxito", "Cambios guardados", TrayIcon.MessageType.INFO);
                        }
                    } else {
                        Utilidades.sendNotification("Error", "Código ya registrado", TrayIcon.MessageType.WARNING);
                    }
                }
            } else {
                Utilidades.sendNotification("Error", "Verifique el campo Código", TrayIcon.MessageType.WARNING);
            }
        } else {
            PersonaValidator.mostrarErrores(errors);
        }
    }

    private void iniciarComponentes() {
        setTitle("Nuevo estudiante");
        setContentPane(panelPrincipal);
        cargarComboBox();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        cargarTablas();
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void paraActualizar() {
        setTitle("Datos Estudiante");
        btnAñadir.setText("Guardar");
        btnHecho.setText("Cancelar");
        guardarCopia();
        cargarEstudiante();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                onCancel();
            }
        });
    }

    private void cargarEstudiante() {
        txtNombres.setText(persona.getNombres());
        txtApellidos.setText(persona.getApellidos());
        txtCodigo.setText(persona.getCodigo());
        txtEmail.setText(persona.getEmail());
        pickerEdad.setDate(Utilidades.dateToLocalDate(persona.getCumpleaños()));
        labelEdad.setText(String.valueOf(persona.getEdad()));
        cbbGenero.setSelectedIndex(persona.isGenero() ? 0 : 1);
    }

    private void cargarTablas() {
        cargarDocumentos();
        cargarCelulares();
        cargarFamiliares();
        cargarSeguros();
    }

    private void guardarCopia() {
        txtNombres.setName(persona.getNombres());
        txtApellidos.setName(persona.getApellidos());
        txtCodigo.setName(persona.getCodigo());
        txtEmail.setName(persona.getEmail());
        txtDni.setName(persona.getDocumentos().get(0).getNumero());
        labelEdad.setName(String.valueOf(persona.getEdad()));
        nacimiento = persona.getCumpleaños();
    }

    private void onCancel() {
        persona.setNombres(txtNombres.getName());
        persona.setApellidos(txtApellidos.getName());
        persona.setCodigo(txtCodigo.getName());
        persona.setEmail(txtEmail.getName());
        persona.setCumpleaños(nacimiento);
        persona.setEdad(Utilidades.calcularaños(nacimiento));
        cerrar();
    }

    private void limpiarControles() {
        txtDni.setText(null);
        txtCodigo.setText(null);
        txtNombres.setText(null);
        txtApellidos.setText(null);
        pickerEdad.setDate(null);
        labelEdad.setText("0");
        txtEmail.setText(null);
    }

    private void cargarComboBox() {
        cbbTipoDocumento.setModel(new DefaultComboBoxModel((Vector) VPrincipal.tipoDocumentos));
        cbbTipoDocumento.setRenderer(new TipoDocumento.ListCellRenderer());
        cbbSeguro.setModel(new DefaultComboBoxModel((Vector) VPrincipal.seguros));
        cbbSeguro.setRenderer(new Seguro.ListCellRenderer());
    }

    private void cargarAgregarFamiliar() {
        DAñadirFamiliar dAñadirFamiliar = new DAñadirFamiliar(persona);
        dAñadirFamiliar.setVisible(true);
        Utilidades.actualizarTabla(tablaFamiliares);
    }

    private void cargarAgregarCelular() {
        DAñadirCelular dañadirCelular = new DAñadirCelular(persona);
        dañadirCelular.setVisible(true);
        Utilidades.actualizarTabla(tablaCelulares);
    }

    private void cargarAgregarSeguro() {
        persona.getSeguros().add((Seguro) cbbSeguro.getSelectedItem());
        Utilidades.actualizarTabla(tablaSeguros);
    }

    private void cargarAgregarDocumento() {
        Documento documento = new Documento();
        documento.setTypeDocument((TipoDocumento) cbbTipoDocumento.getSelectedItem());
        documento.setNumero(txtDni.getText().trim());
        documento.setPerson(persona);
        DocumentoValidator validator = new DocumentoValidator();
        Set<ConstraintViolation<Documento>> errors = validator.loadViolations(documento);
        if (errors.isEmpty()) {
            if (!Documentos.existe(documento.getNumero())) {
                persona.getDocumentos().add(documento);
                Utilidades.actualizarTabla(tablaDocumentos);
                Utilidades.sendNotification("Éxito", "Documento registrado", TrayIcon.MessageType.INFO);
                txtDni.setText(null);
            } else {
                Utilidades.sendNotification("Error", "Documento pertenece a otra persona", TrayIcon.MessageType.ERROR);
            }
        } else {
            DocumentoValidator.mostrarErrores(errors);
        }
    }

    private void cargarDocumentos() {
        documentoAbstractModel = new DocumentoAbstractModel(persona.getDocumentos());
        tablaDocumentos.setModel(documentoAbstractModel);
        tablaDocumentos.getColumnModel().getColumn(documentoAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonEditorDocumentos(persona, tablaDocumentos, "eliminar"));
        tablaDocumentos.getColumnModel().getColumn(documentoAbstractModel.getColumnCount() - 2).setCellEditor(new JButtonEditorDocumentos(persona, tablaDocumentos, "editar"));
        TableCellRenderer renderer1 = tablaDocumentos.getDefaultRenderer(JButton.class);
        tablaDocumentos.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        DocumentoCellRendered.setCellRenderer(tablaDocumentos);
        Utilidades.headerNegrita(tablaDocumentos);

    }

    private void cargarSeguros() {
        segurosAbstractModel = new SeguroPersonaAbstractModel(persona.getSeguros());
        tablaSeguros.setModel(segurosAbstractModel);
        tablaSeguros.getColumnModel().getColumn(segurosAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonEditorSeguros(tablaSeguros, persona));
        TableCellRenderer renderer1 = tablaCelulares.getDefaultRenderer(JButton.class);
        tablaCelulares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.headerNegrita(tablaSeguros);
        SegurosPersonasCellRendered.setCellRenderer(tablaSeguros);
    }

    private void cargarCelulares() {
        celularesAbstractModel = new CelularesAbstractModel(persona.getCelulares());
        tablaCelulares.setModel(celularesAbstractModel);
        tablaCelulares.getColumnModel().getColumn(celularesAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonEditorCelulares(tablaCelulares));
        TableCellRenderer renderer1 = tablaCelulares.getDefaultRenderer(JButton.class);
        tablaCelulares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.headerNegrita(tablaCelulares);
        CelularesCellRendered.setCellRenderer(tablaCelulares);
    }

    private void cargarFamiliares() {
        familiaresAbstractModel = new FamiliaresAbstractModel(persona.getFamiliaresparaEstudiante());
        tablaFamiliares.setModel(familiaresAbstractModel);
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonEditorFamiliares(persona.getFamiliaresparaEstudiante(), tablaFamiliares, "editar"));
        tablaFamiliares.getColumnModel().getColumn(familiaresAbstractModel.getColumnCount() - 2).setCellEditor(new JButtonEditorFamiliares(persona.getFamiliaresparaEstudiante(), tablaFamiliares, "apoderado"));
        TableCellRenderer renderer1 = tablaFamiliares.getDefaultRenderer(JButton.class);
        tablaFamiliares.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.headerNegrita(tablaFamiliares);
        FamiliaresCellRendered.setCellRenderer(tablaFamiliares, persona.getFamiliaresparaEstudiante());
    }

    private void cerrar() {
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        pickerEdad = new DatePicker();
        pickerEdad.getComponentDateTextField().setBorder(new JTextField().getBorder());
        pickerEdad.getComponentDateTextField().setHorizontalAlignment(JTextField.CENTER);
        pickerEdad.getComponentDateTextField().setBackground(new JTextField().getBackground());
        pickerEdad.getSettings().setFormatForDatesCommonEra("dd-MM-yyyy");
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
        tabbedPane1 = new JTabbedPane();
        panelPrincipal.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(6, 6, new Insets(5, 5, 5, 5), 5, 5));
        tabbedPane1.addTab("Alumno", panel1);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Nacimiento:");
        panel1.add(label1, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Nombres:");
        panel1.add(label2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Apellidos:");
        panel1.add(label3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, -1, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Email:");
        panel1.add(label4, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(3, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$(null, -1, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Edad:");
        panel2.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$(null, -1, 14, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("años");
        panel2.add(label6, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$(null, -1, 14, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setText("Género:");
        panel2.add(label7, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbGenero = new JComboBox();
        Font cbbGeneroFont = this.$$$getFont$$$(null, -1, 14, cbbGenero.getFont());
        if (cbbGeneroFont != null) cbbGenero.setFont(cbbGeneroFont);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Masculino");
        defaultComboBoxModel1.addElement("Femenino");
        cbbGenero.setModel(defaultComboBoxModel1);
        panel2.add(cbbGenero, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        labelEdad = new JLabel();
        Font labelEdadFont = this.$$$getFont$$$(null, -1, 14, labelEdad.getFont());
        if (labelEdadFont != null) labelEdad.setFont(labelEdadFont);
        labelEdad.setText("0");
        panel2.add(labelEdad, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$(null, -1, 14, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setText("Código:");
        panel1.add(label8, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1.add(pickerEdad, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(120, 28), null, 0, false));
        txtCodigo = new JTextField();
        Font txtCodigoFont = this.$$$getFont$$$(null, -1, 14, txtCodigo.getFont());
        if (txtCodigoFont != null) txtCodigo.setFont(txtCodigoFont);
        panel1.add(txtCodigo, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtEmail = new JTextField();
        Font txtEmailFont = this.$$$getFont$$$(null, -1, 14, txtEmail.getFont());
        if (txtEmailFont != null) txtEmail.setFont(txtEmailFont);
        panel1.add(txtEmail, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtNombres = new JTextField();
        Font txtNombresFont = this.$$$getFont$$$(null, -1, 14, txtNombres.getFont());
        if (txtNombresFont != null) txtNombres.setFont(txtNombresFont);
        panel1.add(txtNombres, new GridConstraints(1, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtApellidos = new JTextField();
        Font txtApellidosFont = this.$$$getFont$$$(null, -1, 14, txtApellidos.getFont());
        if (txtApellidosFont != null) txtApellidos.setFont(txtApellidosFont);
        panel1.add(txtApellidos, new GridConstraints(2, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel1.add(spacer4, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel1.add(spacer5, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), 5, 5));
        tabbedPane1.addTab("Documentos", panel3);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablaDocumentos = new JTable();
        Font tablaDocumentosFont = this.$$$getFont$$$(null, -1, 14, tablaDocumentos.getFont());
        if (tablaDocumentosFont != null) tablaDocumentos.setFont(tablaDocumentosFont);
        tablaDocumentos.setRowHeight(25);
        scrollPane1.setViewportView(tablaDocumentos);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        Font label9Font = this.$$$getFont$$$(null, -1, 14, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setText("Tipo:");
        panel4.add(label9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel4.add(spacer6, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        cbbTipoDocumento = new JComboBox();
        Font cbbTipoDocumentoFont = this.$$$getFont$$$(null, -1, 14, cbbTipoDocumento.getFont());
        if (cbbTipoDocumentoFont != null) cbbTipoDocumento.setFont(cbbTipoDocumentoFont);
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        cbbTipoDocumento.setModel(defaultComboBoxModel2);
        panel4.add(cbbTipoDocumento, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDni = new JTextField();
        Font txtDniFont = this.$$$getFont$$$(null, -1, 14, txtDni.getFont());
        if (txtDniFont != null) txtDni.setFont(txtDniFont);
        panel4.add(txtDni, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        btnAñadirDocumento = new JButton();
        Font btnAñadirDocumentoFont = this.$$$getFont$$$(null, -1, 14, btnAñadirDocumento.getFont());
        if (btnAñadirDocumentoFont != null) btnAñadirDocumento.setFont(btnAñadirDocumentoFont);
        btnAñadirDocumento.setText("Añadir");
        panel4.add(btnAñadirDocumento, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 2, new Insets(5, 5, 5, 5), 5, 5));
        tabbedPane1.addTab("Seguros", panel5);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel5.add(scrollPane2, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablaSeguros = new JTable();
        Font tablaSegurosFont = this.$$$getFont$$$(null, -1, 14, tablaSeguros.getFont());
        if (tablaSegurosFont != null) tablaSeguros.setFont(tablaSegurosFont);
        tablaSeguros.setRowHeight(25);
        scrollPane2.setViewportView(tablaSeguros);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel6, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        Font label10Font = this.$$$getFont$$$(null, -1, 14, label10.getFont());
        if (label10Font != null) label10.setFont(label10Font);
        label10.setText("Seguro:");
        panel6.add(label10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel6.add(spacer7, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        cbbSeguro = new JComboBox();
        Font cbbSeguroFont = this.$$$getFont$$$(null, -1, 14, cbbSeguro.getFont());
        if (cbbSeguroFont != null) cbbSeguro.setFont(cbbSeguroFont);
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        cbbSeguro.setModel(defaultComboBoxModel3);
        panel6.add(cbbSeguro, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnAñadirSeguro = new JButton();
        Font btnAñadirSeguroFont = this.$$$getFont$$$(null, -1, 14, btnAñadirSeguro.getFont());
        if (btnAñadirSeguroFont != null) btnAñadirSeguro.setFont(btnAñadirSeguroFont);
        btnAñadirSeguro.setText("Añadir seguro");
        panel6.add(btnAñadirSeguro, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), 5, 5));
        tabbedPane1.addTab("Celulares", panel7);
        final JScrollPane scrollPane3 = new JScrollPane();
        panel7.add(scrollPane3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(300, -1), null, 0, false));
        tablaCelulares = new JTable();
        Font tablaCelularesFont = this.$$$getFont$$$(null, -1, 14, tablaCelulares.getFont());
        if (tablaCelularesFont != null) tablaCelulares.setFont(tablaCelularesFont);
        tablaCelulares.setRowHeight(25);
        scrollPane3.setViewportView(tablaCelulares);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel7.add(panel8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnNuevoCelular = new JButton();
        Font btnNuevoCelularFont = this.$$$getFont$$$(null, -1, 14, btnNuevoCelular.getFont());
        if (btnNuevoCelularFont != null) btnNuevoCelular.setFont(btnNuevoCelularFont);
        btnNuevoCelular.setText("Añadir celular");
        panel8.add(btnNuevoCelular, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        panel8.add(spacer8, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), 5, 5));
        tabbedPane1.addTab("Familiares", panel9);
        final JScrollPane scrollPane4 = new JScrollPane();
        panel9.add(scrollPane4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(600, -1), new Dimension(600, -1), null, 0, false));
        tablaFamiliares = new JTable();
        Font tablaFamiliaresFont = this.$$$getFont$$$(null, -1, 14, tablaFamiliares.getFont());
        if (tablaFamiliaresFont != null) tablaFamiliares.setFont(tablaFamiliaresFont);
        tablaFamiliares.setRowHeight(25);
        scrollPane4.setViewportView(tablaFamiliares);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel9.add(panel10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnNuevoFamiliar = new JButton();
        btnNuevoFamiliar.setEnabled(true);
        btnNuevoFamiliar.setFocusable(true);
        Font btnNuevoFamiliarFont = this.$$$getFont$$$(null, -1, 14, btnNuevoFamiliar.getFont());
        if (btnNuevoFamiliarFont != null) btnNuevoFamiliar.setFont(btnNuevoFamiliarFont);
        btnNuevoFamiliar.setText("Nuevo familiar");
        panel10.add(btnNuevoFamiliar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        panel10.add(spacer9, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panel11, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnHecho = new JButton();
        btnHecho.setText("Hecho");
        panel11.add(btnHecho, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 32), null, 0, false));
        final Spacer spacer10 = new Spacer();
        panel11.add(spacer10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btnAñadir = new JButton();
        btnAñadir.setText("Registrar");
        panel11.add(btnAñadir, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 32), null, 0, false));
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
