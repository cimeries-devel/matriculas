package com.devel.views.dialogs;

import com.devel.utilities.Colors;
import com.devel.utilities.JButoonEditors.JButtonEditorTipoDocumento;
import com.devel.utilities.JButoonEditors.JTableButtonRenderer;
import com.devel.utilities.TablecellRendered.TipoDocumentoCellRendered;
import com.devel.utilities.Utilidades;
import com.devel.utilities.modelosTablas.TipoDocumentoAbstractModel;
import com.devel.views.VPrincipal;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;

public class DGestionTipoDocumento extends JDialog {
    private JTable tablaTipoDocumentos;
    private JButton btnHecho;
    private JButton btnAñadir;
    private JPanel panelPrincipal;
    private TipoDocumentoAbstractModel tipoDocumentoAbstractModel;

    public DGestionTipoDocumento() {
        iniciarComponentes();
        btnAñadir.addActionListener(e -> {
            cargarNuevoTipoDeDocumento();
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
        setTitle("Tipo de documentos");
        setContentPane(panelPrincipal);
        cargarTabla();
        pack();
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnAñadir);
    }

    private void cargarTabla() {
        tipoDocumentoAbstractModel = new TipoDocumentoAbstractModel(VPrincipal.tipoDocumentos);
        tablaTipoDocumentos.setModel(tipoDocumentoAbstractModel);
        tablaTipoDocumentos.getColumnModel().getColumn(tipoDocumentoAbstractModel.getColumnCount() - 1).setCellEditor(new JButtonEditorTipoDocumento(tablaTipoDocumentos));
        TableCellRenderer renderer1 = tablaTipoDocumentos.getDefaultRenderer(JButton.class);
        tablaTipoDocumentos.setDefaultRenderer(JButton.class, new JTableButtonRenderer(renderer1));
        Utilidades.headerNegrita(tablaTipoDocumentos);
        TipoDocumentoCellRendered.setCellRenderer(tablaTipoDocumentos);
    }

    private void cargarNuevoTipoDeDocumento() {
        DAñadirTipoDocumento documento = new DAñadirTipoDocumento();
        documento.setVisible(true);
        Utilidades.actualizarTabla(tablaTipoDocumentos);
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
        tablaTipoDocumentos = new JTable();
        tablaTipoDocumentos.setRowHeight(25);
        scrollPane1.setViewportView(tablaTipoDocumentos);
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
