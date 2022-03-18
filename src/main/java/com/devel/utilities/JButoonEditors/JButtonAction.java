package com.devel.utilities.JButoonEditors;

import com.devel.ForResources;
import com.devel.utilities.Colors;
import com.devel.views.VPrincipal;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class JButtonAction extends JButton {
    Color selected= VPrincipal.propiedades.getTema().equals("oscuro")? Colors.selectedRow2:Colors.selectedRow1;
    Color focus=VPrincipal.propiedades.getTema().equals("oscuro")?Colors.focusRow2:Colors.focusRow1;
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
                setBackground(selected);
            }
        });
    }


}