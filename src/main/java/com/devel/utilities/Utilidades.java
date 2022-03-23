package com.devel.utilities;

import com.devel.Principal;
import com.devel.models.Relacion;
import com.devel.models.Tarifa;
import com.devel.utilities.TablecellRendered.TablesCellRendered;
import com.devel.views.VPrincipal;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Utilidades {
    private static TrayIcon mainTrayIcon;
    private static DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
    private static DefaultTableCellRenderer izquierda = new DefaultTableCellRenderer();
    private static Image trayIconImage = Toolkit.getDefaultToolkit().createImage(Principal.class.getResource("Icons/x32/fedora.png"));
    private static SystemTray mainTray;
    private static boolean primera=true;
    public static DateFormat formatoParaAños=new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat formatoHora=new SimpleDateFormat("HH:mm");
    public static DateFormat año=new SimpleDateFormat("yyyy");
    public static  NumberFormat sol = NumberFormat.getCurrencyInstance();
    public static void tema(String tema){
        try {
            if(tema.equals("oscuro")){
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }else{
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            }

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    }
    public static Boolean espacioEnblanco(JTextField textField){
        if(textField.getText().trim().length()>0){
            return false;
        }
        return true;
    }
    public static boolean precioEsValido(KeyEvent e, String precio){
        int caracter = e.getKeyChar();
        if(caracter==46){
            if(precio.lastIndexOf('.')==-1){
                if(precio.length()>0){
                    return true;
                }
                return false;
            }else{
                return false;
            }
        }else{
            if(caracter >= 48 && caracter <= 57){
                return true;
            }else{
                return false;
            }
        }
    }

    public static void buttonSelectedOrEntered(JButton boton){
        if(VPrincipal.tema.equals("oscuro")){
            boton.setBackground(Colors.buttonSelected2);
        }else{
            boton.setBackground(Colors.buttonSelected1);
        }
    }

    public static void buttonExited(JButton boton){
        if(VPrincipal.tema.equals("oscuro")){
            boton.setBackground(Colors.buttonExited2);
        }else{
            boton.setBackground(Colors.buttonExited1);
        }
    }
    public static Date convertLocalTimeToDate(LocalTime time) {
        Instant instant = time.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
        return toDate(instant);
    }
    private static Date toDate(Instant instant) {
        BigInteger millis = BigInteger.valueOf(instant.getEpochSecond()).multiply(
                BigInteger.valueOf(1000));
        millis = millis.add(BigInteger.valueOf(instant.getNano()).divide(
                BigInteger.valueOf(1_000_000)));
        return new Date(millis.longValue());
    }
    public static Date localDateToDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
    public static LocalDate dateToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
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
    public static void actualizarTabla(JTable tabla){
        tabla.setVisible(false);
        tabla.setVisible(true);
        tabla.getParent().requestFocus();
    }
    public static void headerNegrita(JTable table){
        ((DefaultTableCellRenderer) (table.getTableHeader()).getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer header = new DefaultTableCellRenderer();
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        header.setForeground(Color.white);
        header.setBackground(new Color(0xFF000000, true));
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
    public static void cellsRendered(JTable table, List<Relacion> vector,boolean a){
        TablesCellRendered tablesCellRendered=new TablesCellRendered(vector,a);
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(tablesCellRendered);
        }

    }
    public static void cellsRendered(JTable table, Vector<Tarifa> vector){
        TablesCellRendered tablesCellRendered=new TablesCellRendered(vector);
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
