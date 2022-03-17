package com.devel.utilities.JButoonEditors;

import com.devel.ForResources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JButtonAction extends JButton {

    public JButtonAction(String icon, String texto) {
        setIcon(new ImageIcon(ForResources.class.getResource("Icons/"+icon)));
        setText(texto);
        setHorizontalTextPosition(2);
        initialize();
    }
    public JButtonAction(String icon) {
        setIcon(new ImageIcon(ForResources.class.getResource("Icons/"+icon)));
        initialize();
    }
    public JButtonAction(String icon,Color background) {
        setBackground(background);
        setBorder(BorderFactory.createEmptyBorder());
        setIcon(new ImageIcon(ForResources.class.getResource("Icons/"+icon)));
        initialize();
    }
    private void initialize() {
        setOpaque(false);
        setBorderPainted(false);
        setFocusable(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table = (JTable) getParent();
                setBackground(table.getSelectionBackground());
            }
        });
    }

}