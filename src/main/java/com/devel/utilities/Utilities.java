package com.devel.utilities;

import com.devel.ForResources;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.beans.JavaBean;
import java.util.Vector;

public class Utilities {
    private static TrayIcon mainTrayIcon;
    private static Image trayIconImage = Toolkit.getDefaultToolkit().createImage(ForResources.class.getResource("Icons/x32/fedora.png"));
    private static SystemTray mainTray;
    private static boolean primera=true;

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
    public static Boolean espacioEnblanco(JTextField textField){
        if(textField.getText().trim().length()>0){
            return false;
        }
        return true;
    }
    public static void leerErrores(Vector<String> vector){
        String errores="";
        for(String error:vector){
            errores=errores +error+",";
        }
        Utilities.sendNotification("Error","Revise los campos: "+errores, TrayIcon.MessageType.WARNING);
    }
    public static void sendNotification(String title, String subtitle, TrayIcon.MessageType tipoMensaje) {
        if(isWindows(System.getProperty("os.name"))){
            if(primera){
                instanciar();
            }
            if(mainTray.getTrayIcons().length==0){
                try {
                    mainTray.add(mainTrayIcon);
                    mainTrayIcon.setToolTip("Gestor de notificaciones");
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }
            mainTrayIcon.setImageAutoSize(true);
            try {
                mainTrayIcon.displayMessage(title,  subtitle, tipoMensaje);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static void instanciar(){
        mainTray = SystemTray.getSystemTray();
        mainTrayIcon=new TrayIcon(trayIconImage);
        primera=false;
    }
    public static boolean isWindows(String OS) {
        return (OS.indexOf("Win") >= 0);
    }
}
