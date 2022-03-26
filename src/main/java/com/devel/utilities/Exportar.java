package com.devel.utilities;
import java.io.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Exportar {
    private static File file;
    private static String[] nombreColumnas;
    private static WritableWorkbook Excel;
    private static DataOutputStream ruta;
    private static List<Object[]> datos;
    private static JFileChooser chooser;
    private static WritableSheet hoja;
    public static void exportar(String[] nombreColumnas, List<Object[]> datos){
        Exportar.nombreColumnas=nombreColumnas;
        Exportar.datos=datos;
        if(pedirNombre()){
            hoja = Excel.createSheet(chooser.getSelectedFile().getName(), 0);

        }
    }

//    public static boolean export() {
//        try {
//            ruta = new DataOutputStream(new FileOutputStream(file));
//            Excel = Workbook.createWorkbook(ruta);
//
//            for (int index = 0; index < tabla.size(); index++) {
//                JTable table = (JTable) tabla.get(index);
//
//                for (int i = 0; i < table.getColumnCount(); i++) {
//                    for (int j = 0; j < table.getRowCount(); j++) {
//                        Object object = table.getValueAt(j, i);
//                        s.addCell(new Label(i, j, String.valueOf(object)));
//                    }
//                }
//            }
//        } catch (IOException | WriteException e) {
//            e.printStackTrace();
//        }
//        escribirExcel();
//        cerrarExccel();
//        return true;
//    }

    public static boolean  pedirNombre(){
        chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos excel", "xml","xls","xlsm","xla","xlr","xlw","xlt","xlsx","xlsb","xltx","xltm");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            instanciarFile(chooser.getSelectedFile().toString().concat(".xls"));
            return true;
        }
        return false;
    }

    public static void exportaar(){
//        if (tablaAlumnos.getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xml","xls","xlsm","xla","xlr","xlw","xlt","xlsx","xlsb","xltx","xltm");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Guardar archivo");
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
//                List tb = new ArrayList();
//                List nom = new ArrayList();
//                tb.add(tablaAlumnos);
//                nom.add("Compras por factura");
//                String file = chooser.getSelectedFile().toString().concat(".xls");
//                try {
//                    com.devel.utilities.Exporter e = new com.devel.utilities.Exporter(new File(file), tb, nom);
//                    if (e.export()) {
//                        JOptionPane.showMessageDialog(null, "Los datos fueron exportados a excel en el directorio seleccionado", "Mensaje de Informacion", JOptionPane.INFORMATION_MESSAGE);
//                    }
//                } catch (IOException e) {
//                    JOptionPane.showMessageDialog(null, "Hubo un error " + e.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }else{
//            JOptionPane.showMessageDialog(this, "No hay datos para exportar","Mensaje de error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void cerrarExccel() {
        try {
            Excel.close();
        } catch (WriteException | IOException ex) {
            ex.printStackTrace();
        }
    }
    private static void escribirExcel(){
        try {
            Excel.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void instanciarFile(String nombreArchivo){
        file=new File(nombreArchivo);
    }
}
