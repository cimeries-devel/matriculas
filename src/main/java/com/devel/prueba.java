package com.devel;

import javax.swing.*;
import java.awt.*;

public class prueba extends JFrame
{
    public prueba()
    {
        JPanel panel = new JPanel();
        panel.setLayout( new OverlayLayout(panel) );
        add(panel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("1", new JTextField("one"));
        tabbedPane.add("2", new JTextField("two"));
        tabbedPane.setAlignmentX(1.0f);
        tabbedPane.setAlignmentY(0.0f);

        panel.setLayout( new OverlayLayout(panel) );
        JCheckBox checkBox = new JCheckBox("Check Me");
        checkBox.setOpaque( false );
        checkBox.setAlignmentX(1.0f);
        checkBox.setAlignmentY(0.0f);
        panel.add( checkBox );
        panel.add(tabbedPane);
        add(new JTextField(10), BorderLayout.NORTH);
    }

    public static void main(String args[])
    {
        prueba frame = new prueba();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }
}
