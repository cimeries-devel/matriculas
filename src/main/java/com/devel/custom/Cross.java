package com.devel.custom;

import com.devel.ForResources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cross extends JPanel {
    private JLabel L;
    private JLabel B;
    private int size = 22;
    private JTabbedPane jTabbedPane;
    private String title;

    public Cross(final JTabbedPane jTabbedPane, String title) {
        this.jTabbedPane = jTabbedPane;
        this.title = title;
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        L = new JLabel(title + " ");
        Dimension d = new Dimension(22, 22);
        B = new JLabel();
        B.setPreferredSize(d);
        B.setMaximumSize(d);
        B.setMinimumSize(d);
        ImageIcon iconoNormal = getImage("cerrar.png");
        ImageIcon iconoSegundo = getImage("cerrar2.png");
        ImageIcon iconoTercero = getImage("cerrar3.png");
        B.setToolTipText("Cerrar Pestaña " + title);
        B.setIcon(iconoNormal);
        //Listener para cierre de tabs con acceso estatico al `JTabbedPane`
        B.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                jTabbedPane.removeTabAt(jTabbedPane.indexOfTab(title));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                B.setIcon(iconoSegundo);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                B.setIcon(iconoNormal);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                B.setIcon(iconoNormal);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                B.setIcon(iconoTercero);
            }
        });
        add(L, gbc);
        gbc.gridx++;
        gbc.weightx = 0;
        add(B, gbc);
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                if(e.getButton()==3){
//                    pop_up.setVisible(true);
//                }else{
//                    jTabbedPane.setSelectedIndex(jTabbedPane.indexOfTab(title));
//                }
//            }
//        });
    }

    private ImageIcon getImage(String icono) {
        Image IMG = null;
        try {
            IMG = new ImageIcon(ForResources.class.getResource(String.format("Icons/x32/" + icono))).getImage();
            IMG = IMG.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ImageIcon(IMG);
    }
}
