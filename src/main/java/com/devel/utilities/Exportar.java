package com.devel.utilities;
import java.awt.*;
import java.io.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import jxl.Cell;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.CellFormat;
import jxl.write.*;
import jxl.write.Label;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class Exportar {
    private static File file;
    private static List<String> nombreColumnas;
    private static WritableWorkbook Excel;
    private static DataOutputStream outputStream;
    private static List<Object[]> datos;
    private static JFileChooser chooser;
    private static WritableSheet hoja;
    private static String titulo;

    public static void exportar(String titulo,List<String> nombreColumnas, List<Object[]> datos){
        if(!datos.isEmpty()){
            Exportar.nombreColumnas=nombreColumnas;
            Exportar.titulo=titulo;
            Exportar.datos=datos;
            try {
                WritableCellFormat cellFormat=new WritableCellFormat();
                WritableFont font=new WritableFont(WritableFont.ARIAL);
                font.setBoldStyle(WritableFont.BOLD);

                CellView cellView=new CellView();
                cellView.setAutosize(true);

                cellFormat.setAlignment(Alignment.getAlignment(2));
                cellFormat.setFont(font);

                if(pedirNombre()){
                    hoja = Excel.createSheet(titulo, 0);
                    for(int i=0;i< nombreColumnas.size();i++){
                        Label cabecera=new Label(i+3,3,nombreColumnas.get(i));
                        cabecera.setCellFormat(cellFormat);
                        hoja.addCell(cabecera);
                    }
                    for (int i = 0; i <datos.size(); i++) {
                        Object[] object=datos.get(i);
                        Label numero=new Label(2,i+4,String.valueOf(i+1));
                        hoja.addCell(numero);
                        hoja.setColumnView(2,cellView);
                        for (int j = 0; j < object.length; j++) {
                            Label contenido=new Label(j+3, i+4, String.valueOf(object[j]));
                            hoja.addCell(contenido);
                            hoja.setColumnView(j+3,cellView);
                        }
                    }
                    escribirExcel();
                    cerrarExccel();
                }
            }catch (WriteException e){
                e.printStackTrace();
            }
        }else{
            Utilidades.sendNotification("Error","No hay datos", TrayIcon.MessageType.ERROR);
        }
    }

    private static boolean  pedirNombre(){
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

    private static void cerrarExccel() {
        try {
            Excel.close();
            outputStream.close();
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
        try {
            file=new File(nombreArchivo);
            outputStream = new DataOutputStream(new FileOutputStream(file));
            Excel = Workbook.createWorkbook(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
