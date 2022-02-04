package com.devel.utilities;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class Utilities {

    public static void cambiarWindows(){
        try{
            for (UIManager.LookAndFeelInfo info :  javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (info.getName().equals("Windows")) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            defaults.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
