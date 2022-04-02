package com.devel.views.dialogs;

import com.devel.controllers.Documentos;
import com.devel.controllers.TiposRelaciones;
import com.devel.models.*;
import com.devel.utilities.Utilidades;
import com.devel.validators.*;
import com.devel.views.VPrincipal;
import com.github.lgooddatepicker.components.DatePicker;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import jakarta.validation.ConstraintViolation;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

public class DAñadirFamiliar extends JDialog {
    private DatePicker datePicker1;
    private JLabel lblEdad;
    private JTextField txtNombres;
    private JButton btnHecho;
    private JTextField txtCelular;
    private JCheckBox ckVivenJuntos;
    private JButton btnAñadir;
    private JPanel panelPrincipal;
    private JComboBox cbbTipoDocumento;
    private JButton btnBuscar;
    private JTextField txtApellidos;
    private JTextField txtDescripcionCelular;
    private JTextField txtDireccion;
    private JTextField txtEmail;
    private JTextField txtDni;
    private JComboBox cbbGenero;
    private JComboBox cbbTipoRelación;
    private JButton nuevoButton;
    private Persona persona;
    private Persona familiar;
    private boolean vivenJuntos;

    public DAñadirFamiliar(Persona persona) {
        this.persona = persona;
        familiar = new Persona();
        $$$setupUI$$$();
        iniciarComponentes();
        datePicker1.addDateChangeListener(dateChangeEvent -> {
            if (datePicker1.getDate() != null) {
                int edad = Utilidades.calcularaños(Date.valueOf(datePicker1.getDate()));
                lblEdad.setText(String.valueOf(edad));
            }
        });
        btnAñadir.addActionListener(e -> {
            registrar();
        });
        btnHecho.addActionListener(e -> {
            cerrar();
        });
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtDni.getText().length() == 8) {
                    buscarPersona();
                }
            }
        });
        panelPrincipal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarNuevoTipoRelacion();
            }
        });
    }

    public DAñadirFamiliar(Persona persona, Persona familiar) {
        this.persona = persona;
        this.familiar = familiar;
        $$$setupUI$$$();
        iniciarComponentes();
        paraActualizar();
        datePicker1.addDateChangeListener(dateChangeEvent -> {
            if (datePicker1.getDate() != null) {
                int edad = Utilidades.calcularaños(Date.valueOf(datePicker1.getDate()));
                lblEdad.setText(String.valueOf(edad));
            }
        });
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
        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarNuevoTipoRelacion();
            }
        });
    }

    private void iniciarComponentes() {
        setTitle("Añadir nuevo familiar");
        setContentPane(panelPrincipal);
        pack();
        setModal(true);
        cargarComboBox();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void paraActualizar() {
        setTitle("Editar Familiar");
        btnAñadir.setText("Guardar");
        btnHecho.setText("Cancelar");
        txtDni.setEnabled(false);
        btnBuscar.setEnabled(false);
        cbbTipoDocumento.setEnabled(false);
        txtNombres.setEnabled(false);
        txtApellidos.setEnabled(false);
        cbbGenero.setEnabled(false);
        datePicker1.setEnabled(false);
        guardarCopia();
        cargarFamiliar();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                onCancel();
            }
        });
    }

    private void registrar() {
        Documento documento;
        Celular celular;
        if (familiar.getId() == null) {
            familiar = new Persona();
            documento = new Documento();
            celular = new Celular();
            familiar.getCelulares().add(celular);
            familiar.getDocumentos().add(documento);
        } else {
            documento = familiar.getDocumentos().get(0);
            celular = familiar.getCelulares().get(0);
        }
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        Date cumpleaños = datePicker1.getDate() == null ? null : Date.valueOf(datePicker1.getDate());
        String direccion = txtDireccion.getText().trim();
        int edad = Integer.parseInt(lblEdad.getText().trim());
        String email = txtEmail.getText().trim();
        boolean genero = cbbGenero.getSelectedIndex() == 0;

        familiar.setNombres(nombres);
        familiar.setApellidos(apellidos);
        familiar.setCumpleaños(cumpleaños);
        familiar.setCreacion(new java.util.Date());
        familiar.setActualizacion(new java.util.Date());
        familiar.setDireccion(direccion);
        familiar.setEdad(edad);
        familiar.setEmail(email);
        familiar.setGenero(genero);

        Set<ConstraintViolation<Persona>> errors = PersonaValidator.loadViolations(familiar);
        if (errors.isEmpty()) {
            System.out.println("está en celular");
            String numero = txtCelular.getText().trim();
            String descCelular = txtDescripcionCelular.getText().trim();

            celular.setNumero(numero);
            celular.setDescipcion(descCelular);

            Set<ConstraintViolation<Celular>> errors2 = CelularValidator.loadViolations(celular);
            if (errors2.isEmpty()) {
                System.out.println("está en documento");
                String numeroDocumento = txtDni.getText().trim();
                TipoDocumento tipoDocumento = (TipoDocumento) cbbTipoDocumento.getSelectedItem();

                documento.setTypeDocument(tipoDocumento);
                documento.setNumero(numeroDocumento);
                documento.setPerson(familiar);

                Set<ConstraintViolation<Documento>> errors3 = DocumentoValidator.loadViolations(documento);
                if (errors3.isEmpty()) {
                    System.out.println("está en relacion");
                    if (persona.getRelacionAFamiliar(familiar) == null) {
                        if (!persona.existeDocumento(documento.getNumero())) {
                            Relacion relacion = new Relacion();
                            boolean vivenJuntos = ckVivenJuntos.isSelected();
                            boolean esApoderado = persona.getApoderado() == null;
                            TipoRelacion tipoRelacion = (TipoRelacion) cbbTipoRelación.getSelectedItem();

                            relacion.setTipoRelacion(tipoRelacion);
                            relacion.setVivenJuntos(vivenJuntos);
                            relacion.setPersona(persona);
                            relacion.setPersona1(familiar);
                            relacion.setApoderado(esApoderado);

                            Set<ConstraintViolation<Relacion>> errors4 = RelacionValidator.loadViolations(relacion);
                            if (errors4.isEmpty()) {
                                if (persona.getId() != null) {
                                    celular.guardar();
                                    familiar.guardar();
                                    documento.guardar();
                                    relacion.guardar();
                                    persona.guardar();
                                }
                                persona.getFamiliaresparaEstudiante().add(relacion);
                                familiar.getRelaciones().add(relacion);
                                Utilidades.sendNotification("Éxito", "Familiar registrado", TrayIcon.MessageType.INFO);
                                limpiarControles();
                                familiar = new Persona();
                            } else {
                                RelacionValidator.mostrarErrores(errors4);
                            }
                        } else {
                            Utilidades.sendNotification("Error", "Es la misma persona", TrayIcon.MessageType.ERROR);
                        }
                    } else {
                        Utilidades.sendNotification("Error", "familiar ya registrado", TrayIcon.MessageType.ERROR);
                    }
                } else {
                    DocumentoValidator.mostrarErrores(errors3);
                }
            } else {
                CelularValidator.mostrarErrores(errors2);
            }
        } else {
            PersonaValidator.mostrarErrores(errors);
        }
    }

    private void cargarNuevoTipoRelacion() {
        DAñadirTipoRelacion dAñadirTipoRelacion = new DAñadirTipoRelacion();
        dAñadirTipoRelacion.setVisible(true);
    }

    private void actualizar() {
        String direccion = txtDireccion.getText().trim();
        String email = txtEmail.getText().trim();

        familiar.setActualizacion(new java.util.Date());
        familiar.setDireccion(direccion);
        familiar.setEmail(email);

        Set<ConstraintViolation<Persona>> errors = PersonaValidator.loadViolations(familiar);
        if (errors.isEmpty()) {
            Celular celular = familiar.getCelulares().get(0);
            String numero = txtCelular.getText().trim();
            String descCelular = txtDescripcionCelular.getText().trim();

            celular.setNumero(numero);
            celular.setDescipcion(descCelular);

            Set<ConstraintViolation<Celular>> errors2 = CelularValidator.loadViolations(celular);
            if (errors2.isEmpty()) {
                Relacion relacion = familiar.getRelacion(persona);
                boolean vivenJuntos = ckVivenJuntos.isSelected();
                boolean esApoderado = relacion.isApoderado();
                TipoRelacion tipoRelacion = (TipoRelacion) cbbTipoRelación.getSelectedItem();

                relacion.setTipoRelacion(tipoRelacion);
                relacion.setVivenJuntos(vivenJuntos);
                relacion.setApoderado(esApoderado);

                Set<ConstraintViolation<Relacion>> errors4 = RelacionValidator.loadViolations(relacion);
                if (errors4.isEmpty()) {
                    if (persona.getId() != null) {
                        familiar.guardar();
                        relacion.guardar();
                        celular.guardar();
                        persona.guardar();
                    }
                    Utilidades.sendNotification("Éxito", "Cambios guardados", TrayIcon.MessageType.INFO);
                    cerrar();
                } else {
                    RelacionValidator.mostrarErrores(errors4);
                }
            } else {
                CelularValidator.mostrarErrores(errors2);
            }
        } else {
            PersonaValidator.mostrarErrores(errors);
        }
    }

    private void buscarPersona() {
        Documento documento = Documentos.getByDni(txtDni.getText().trim());
        if (documento != null) {
            familiar = documento.getPerson();
            cargarDatos();
        } else {
            DataAPIDNI dataAPIDNI = DataAPIDNI.getDatosDni(txtDni.getText().trim());
            if (dataAPIDNI != null) {
                cargarDatosDataappi(dataAPIDNI);
            } else {
                Utilidades.sendNotification("Sin datos", "", TrayIcon.MessageType.INFO);
            }
        }
    }

    private void cargarDatosDataappi(DataAPIDNI dataAPIDNI) {
        txtApellidos.setText(dataAPIDNI.getApellido_paterno() + " " + dataAPIDNI.getApellido_materno());
        txtNombres.setText(dataAPIDNI.getNombres());
        txtDireccion.setText(dataAPIDNI.getDireccion_completa() == null ? "--" : dataAPIDNI.getDireccion_completa());
    }

    private void cargarDatos() {
        txtNombres.setText(familiar.getNombres());
        txtApellidos.setText(familiar.getApellidos());
        txtDireccion.setText(familiar.getDireccion());
        txtEmail.setText(familiar.getEmail());
        cbbGenero.setSelectedIndex(familiar.isGenero() ? 0 : 1);
        datePicker1.setDate(Utilidades.dateToLocalDate(familiar.getCumpleaños()));
        lblEdad.setText(String.valueOf(familiar.getEdad()));
        txtDescripcionCelular.setText(familiar.getCelulares().get(0).getDescipcion());
        txtCelular.setText(familiar.getCelulares().get(0).getNumero());
    }

    private void cargarComboBox() {
        cbbTipoDocumento.setModel(new DefaultComboBoxModel<>(VPrincipal.tipoDocumentos));
        cbbTipoDocumento.setRenderer(new TipoDocumento.ListCellRenderer());

        cbbTipoRelación.setModel(new DefaultComboBoxModel<>(VPrincipal.tipoRelaciones));
        cbbTipoRelación.setRenderer(new TipoRelacion.ListCellRenderer());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        datePicker1 = new DatePicker();
        datePicker1.getComponentDateTextField().setBorder(new JTextField().getBorder());
        datePicker1.getComponentDateTextField().setHorizontalAlignment(JTextField.CENTER);
        datePicker1.getComponentDateTextField().setBackground(new JTextField().getBackground());
        datePicker1.getSettings().setFormatForDatesCommonEra("dd-MM-yyyy");
    }

    private void guardarCopia() {
        txtEmail.setName(familiar.getEmail());
        txtDireccion.setName(familiar.getDireccion());
        cbbTipoRelación.setName(String.valueOf(familiar.getTipoRelacion(persona).getId()));
        txtDescripcionCelular.setName(familiar.getCelulares().get(0).getDescipcion());
        txtCelular.setName(familiar.getCelulares().get(0).getNumero());
        vivenJuntos = familiar.getRelacion(persona).isVivenJuntos();
    }

    private void cargarFamiliar() {
        cbbTipoDocumento.setSelectedItem(familiar.getDocumentos().get(0).getTypeDocument());
        txtDni.setText(familiar.getDocumentos().get(0).getNumero());
        txtNombres.setText(familiar.getNombres());
        txtApellidos.setText(familiar.getApellidos());
        txtEmail.setText(familiar.getEmail());
        txtDireccion.setText(familiar.getDireccion());
        cbbTipoRelación.setSelectedItem(familiar.getRelacion(persona).getTipoRelacion());
        txtDescripcionCelular.setText(familiar.getCelulares().get(0).getDescipcion());
        txtCelular.setText(familiar.getCelulares().get(0).getNumero());
        cbbGenero.setSelectedIndex(familiar.isGenero() ? 0 : 1);
        ckVivenJuntos.setSelected(familiar.getRelacion(persona).isVivenJuntos());
        datePicker1.setDate(Utilidades.dateToLocalDate(familiar.getCumpleaños()));
        lblEdad.setText(String.valueOf(familiar.getEdad()));
    }

    private void onCancel() {
        familiar.setEmail(txtEmail.getName());
        familiar.setDireccion(txtDireccion.getName());
        familiar.setTipoRelacionRelacion(persona, TiposRelaciones.get(Integer.valueOf(cbbTipoRelación.getName())));
        familiar.getCelulares().get(0).setDescipcion(txtDescripcionCelular.getName());
        familiar.getCelulares().get(0).setNumero(txtCelular.getName());
        familiar.getRelacion(persona).setVivenJuntos(vivenJuntos);
        cerrar();
    }

    private void cerrar() {
        dispose();
    }

    private void limpiarControles() {
        txtCelular.setText(null);
        txtDni.setText(null);
        txtApellidos.setText(null);
        txtNombres.setText(null);
        lblEdad.setText(null);
        txtDireccion.setText(null);
        txtDescripcionCelular.setText(null);
        ckVivenJuntos.setSelected(false);
        txtEmail.setText(null);
        datePicker1.getComponentDateTextField().setText(null);
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
        panel1.setLayout(new GridLayoutManager(8, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Desc. celular:");
        panel1.add(label1, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Documento:");
        panel1.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Dirección:");
        panel1.add(label3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, -1, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Nacimiento:");
        panel1.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDireccion = new JTextField();
        Font txtDireccionFont = this.$$$getFont$$$(null, -1, 14, txtDireccion.getFont());
        if (txtDireccionFont != null) txtDireccion.setFont(txtDireccionFont);
        panel1.add(txtDireccion, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(80, -1), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 8, -1));
        panel1.add(panel2, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$(null, -1, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Sexo:");
        panel3.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbGenero = new JComboBox();
        Font cbbGeneroFont = this.$$$getFont$$$(null, -1, 14, cbbGenero.getFont());
        if (cbbGeneroFont != null) cbbGenero.setFont(cbbGeneroFont);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Masculino");
        defaultComboBoxModel1.addElement("Femenino");
        cbbGenero.setModel(defaultComboBoxModel1);
        panel3.add(cbbGenero, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        Font datePicker1Font = this.$$$getFont$$$(null, -1, 14, datePicker1.getFont());
        if (datePicker1Font != null) datePicker1.setFont(datePicker1Font);
        panel4.add(datePicker1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 28), null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$(null, -1, 14, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("Edad:");
        panel4.add(label6, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblEdad = new JLabel();
        Font lblEdadFont = this.$$$getFont$$$(null, -1, 14, lblEdad.getFont());
        if (lblEdadFont != null) lblEdad.setFont(lblEdadFont);
        lblEdad.setText("0");
        panel4.add(lblEdad, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtEmail = new JTextField();
        Font txtEmailFont = this.$$$getFont$$$(null, -1, 14, txtEmail.getFont());
        if (txtEmailFont != null) txtEmail.setFont(txtEmailFont);
        panel1.add(txtEmail, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$(null, -1, 14, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setText("Email:");
        panel1.add(label7, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbTipoDocumento = new JComboBox();
        Font cbbTipoDocumentoFont = this.$$$getFont$$$(null, -1, 14, cbbTipoDocumento.getFont());
        if (cbbTipoDocumentoFont != null) cbbTipoDocumento.setFont(cbbTipoDocumentoFont);
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        cbbTipoDocumento.setModel(defaultComboBoxModel2);
        panel5.add(cbbTipoDocumento, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel5.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        txtDni = new JTextField();
        Font txtDniFont = this.$$$getFont$$$(null, -1, 14, txtDni.getFont());
        if (txtDniFont != null) txtDni.setFont(txtDniFont);
        panel5.add(txtDni, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(95, -1), null, 0, false));
        btnBuscar = new JButton();
        btnBuscar.setText("Buscar");
        panel5.add(btnBuscar, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), 5, -1));
        panel1.add(panel6, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDescripcionCelular = new JTextField();
        Font txtDescripcionCelularFont = this.$$$getFont$$$(null, -1, 14, txtDescripcionCelular.getFont());
        if (txtDescripcionCelularFont != null) txtDescripcionCelular.setFont(txtDescripcionCelularFont);
        panel6.add(txtDescripcionCelular, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$(null, -1, 14, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setText("Número:");
        panel6.add(label8, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtCelular = new JTextField();
        Font txtCelularFont = this.$$$getFont$$$(null, -1, 14, txtCelular.getFont());
        if (txtCelularFont != null) txtCelular.setFont(txtCelularFont);
        panel6.add(txtCelular, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(60, -1), null, 0, false));
        txtNombres = new JTextField();
        Font txtNombresFont = this.$$$getFont$$$(null, -1, 14, txtNombres.getFont());
        if (txtNombresFont != null) txtNombres.setFont(txtNombresFont);
        panel1.add(txtNombres, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(80, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        Font label9Font = this.$$$getFont$$$(null, -1, 14, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setText("Nombres:");
        panel1.add(label9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtApellidos = new JTextField();
        Font txtApellidosFont = this.$$$getFont$$$(null, -1, 14, txtApellidos.getFont());
        if (txtApellidosFont != null) txtApellidos.setFont(txtApellidosFont);
        panel1.add(txtApellidos, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(80, -1), null, 0, false));
        final JLabel label10 = new JLabel();
        Font label10Font = this.$$$getFont$$$(null, -1, 14, label10.getFont());
        if (label10Font != null) label10.setFont(label10Font);
        label10.setText("Apellidos:");
        panel1.add(label10, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        Font label11Font = this.$$$getFont$$$(null, -1, 14, label11.getFont());
        if (label11Font != null) label11.setFont(label11Font);
        label11.setText("Relación:");
        panel1.add(label11, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), 5, -1));
        panel1.add(panel7, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ckVivenJuntos = new JCheckBox();
        ckVivenJuntos.setHorizontalTextPosition(2);
        ckVivenJuntos.setText("Viven juntos");
        panel7.add(ckVivenJuntos, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbbTipoRelación = new JComboBox();
        panel7.add(cbbTipoRelación, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nuevoButton = new JButton();
        nuevoButton.setText("Nuevo");
        panel7.add(nuevoButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panel8, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnAñadir = new JButton();
        btnAñadir.setText("Añadir");
        panel8.add(btnAñadir, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 32), null, 0, false));
        btnHecho = new JButton();
        btnHecho.setText("Hecho");
        panel8.add(btnHecho, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 32), null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel8.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
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
