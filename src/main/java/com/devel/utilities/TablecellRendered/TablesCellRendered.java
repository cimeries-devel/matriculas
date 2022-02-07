package com.devel.utilities.TablecellRendered;

import com.devel.utilities.JButoonEditors.JButtonAction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TablesCellRendered extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getColumnClass(column).equals(JButton.class)){
            switch (table.getColumnName(column)){
                case "Apoderado":
                default:
            }
        }else{
            switch (table.getColumnName(column)){
                case "":
                    setHorizontalAlignment(SwingConstants.LEFT);
                    break;
                default:
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
            }
        }
        return this;
    }
}
