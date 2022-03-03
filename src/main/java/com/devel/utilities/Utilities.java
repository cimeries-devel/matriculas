package com.devel.utilities;

import com.devel.ForResources;
import com.devel.models.Registro;
import com.devel.utilities.TablecellRendered.TablesCellRendered;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.*;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.beans.JavaBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class Utilities {
    private static TrayIcon mainTrayIcon;
    private static DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
    private static DefaultTableCellRenderer izquierda = new DefaultTableCellRenderer();
    private static Image trayIconImage = Toolkit.getDefaultToolkit().createImage(ForResources.class.getResource("Icons/x32/fedora.png"));
    private static SystemTray mainTray;
    private static boolean primera=true;
    public static DateFormat formatoParaAños=new SimpleDateFormat("yyyy-MM-dd");

    public static void cambiarThemaIntellij(){
        LafManager.install(new IntelliJTheme());
    }

    public static void tema(String tema){
        if(tema.equals("oscuro")){
            LafManager.install(new DarculaTheme());
        }else{
            LafManager.install(new IntelliJTheme());
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

    public static void alinearCentro(TableColumn columna){
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        columna.setCellRenderer(centro);
    }
    public static void alinearIzquierda(TableColumn columna){
        izquierda.setHorizontalAlignment(SwingConstants.LEFT);
        columna.setCellRenderer(izquierda);
    }
    public static void definirTamaño(TableColumn column, Integer Width){
        column.setMinWidth(Width);
        column.setMaxWidth(Width);
    }

    public static void headerNegrita(JTable table){
        ((DefaultTableCellRenderer) (table.getTableHeader()).getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer header = new DefaultTableCellRenderer();
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        header.setForeground(Color.white);
        header.setBackground(new Color(0xFF3A3434, true));
        header.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setHeaderRenderer(header);
        }
        table.getTableHeader().setReorderingAllowed(false);
    }
    public static Vector invertirVector(Vector vector){
        Object ventaAUX;
        for(int i=0;i<vector.size()/2;i++){
            ventaAUX=vector.get(i);
            vector.set(i, vector.get(vector.size() - i-1));
            vector.set((vector.size()-i-1), ventaAUX);
        }
        return vector;
    }
    public static void cellsRendered(JTable table){
        TablesCellRendered tablesCellRendered=new TablesCellRendered();
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(tablesCellRendered);
        }
    }
    public static Integer calcularaños(Date fecha){
        Calendar hoy=Calendar.getInstance();
        Calendar nacimiento=Calendar.getInstance();
        nacimiento.setTime(fecha);
        int años= hoy.get(Calendar.YEAR)-nacimiento.get(Calendar.YEAR);
        int meses= hoy.get(Calendar.MONTH)-nacimiento.get(Calendar.MONTH);
        int dias= hoy.get(Calendar.DAY_OF_MONTH)-nacimiento.get(Calendar.DAY_OF_MONTH);
        JOptionPane.showMessageDialog(null,""+años);
        switch (meses){
            case 0:
                if(dias<0){
                    años-=1;
                }
                break;
            default:
                if(meses<0){
                    años-=1;
                }
        }
        return años<0?0:años;
    }
}
