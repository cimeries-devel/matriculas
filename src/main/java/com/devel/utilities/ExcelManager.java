package com.devel.utilities;

import com.devel.Principal;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ExcelManager {
    private Workbook Excel;

    public void exportarDatos() {
        JFileChooser seleccion = new JFileChooser();
        seleccion.setDialogTitle("GUARDAR");
        seleccion.setDialogType(JFileChooser.SAVE_DIALOG);
        seleccion.setFont(new Font("Lucida Handwriting", 0, 18));
        int respuesta = seleccion.showSaveDialog(null);
        switch (respuesta) {
            case JFileChooser.APPROVE_OPTION:
                descargarExcel(seleccion.getSelectedFile().getAbsolutePath());
                break;
            default:
                break;
        }
    }

    public static void descargarExcel(String ubicacion){
        if((ubicacion.lastIndexOf('.'))==-1){
            ubicacion=ubicacion+".xlsx";
        }
        InputStream in = Principal.class.getResourceAsStream("plantillaImportarProductos/plantillaProductos.xlsx");
        File excel = new File(ubicacion);
        try {
            Files.copy(in, excel.toPath(), StandardCopyOption.REPLACE_EXISTING);
            IOUtils.closeQuietly(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utilidades.sendNotification("Éxito","Plantilla Descargada", TrayIcon.MessageType.INFO);
    }


}
