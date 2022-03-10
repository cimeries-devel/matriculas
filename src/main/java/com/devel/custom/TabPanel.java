package com.devel.custom;

import javax.swing.*;

public class TabPanel extends JPanel {
    private String title;
    private Icon icon;
    private JButton option;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public JButton getOption() {
        return option;
    }

    public void setOption(JButton option) {
        this.option = option;
    }
}
