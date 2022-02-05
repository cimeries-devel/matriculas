package com.devel.utilities.JButoonEditors;

import com.devel.ForResources;

import javax.swing.*;

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
    private void initialize() {
        setBorderPainted(false);
        setFocusable(false);
    }
}