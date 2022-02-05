package com.devel.custom;
import javax.swing.*;
import java.awt.*;

public class MButton extends JPanel {
    private int a=0;
    private Color colorBorde;
    private int width;
    private int height;

    public MButton(){
        colorBorde=new Color(0x5E000004, true);
        height=20;
        width=20;
    }
    public MButton(Color color){
        colorBorde=color;
        height=20;
        width=20;
    }
    public MButton(Color color,int width,int height){
        colorBorde=color;
        this.width=width;
        this.height=height;
    }
    public MButton(int width,int height){
        colorBorde=new Color(0x5E000004, true);
        this.width=width;
        this.height=height;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(width,height);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        //Draws the rounded opaque panel with borders.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//Paint background
        graphics.setColor(colorBorde);
        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//Paint border
        setOpaque(false);
        if(a==0){
            this.updateUI();
            a++;
        }
    }
    public void cambiarColorBorde(Color color){
        setBackground(Color.white);
        colorBorde=color;
        this.updateUI();
    }
}
