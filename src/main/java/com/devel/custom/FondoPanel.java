package com.devel.custom;

import com.devel.Principal;

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
        imagen = new ImageIcon(Principal.class.getResource(fondo)).getImage();
        g.drawImage(imagen,0,0,getWidth(),getHeight(),this);
        setOpaque(false);
        super.paint(g);
    }
}
