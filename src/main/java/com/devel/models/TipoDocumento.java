package com.devel.models;

import com.devel.hibernate.Hibernate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.swing.*;
import java.awt.*;

@Entity(name = "tbl_tipoDocumento")
public class TipoDocumento extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;

    @Column
    @NotEmpty
    @Size(min = 3,max = 32,message = "Código")
    private String codigo;

    @Column
    @NotEmpty
    @Size(min = 3,max = 32,message = "Descripción")
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static class ListCellRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof TipoDocumento) {
                value = ((TipoDocumento) value).getCodigo();
            }
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            return this;
        }
    }
}
