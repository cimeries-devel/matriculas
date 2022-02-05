package com.devel.custom;

import com.devel.ForResources;
import com.devel.app.Principal;

import javax.swing.*;
import java.awt.*;

public class FondoPanel extends JPanel {
    private String fondo;
    public FondoPanel(String fondo){
        this.fondo=fondo;
    }
    private Image imagen;
    @Override
    public void paint(Graphics g){
        imagen = new ImageIcon(ForResources.class.getResource(fondo)).getImage();
        g.drawImage(imagen,0,0,getWidth(),getHeight(),this);
        setOpaque(false);
        super.paint(g);
    }
}
