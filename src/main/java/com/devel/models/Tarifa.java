package com.devel.models;

import jakarta.validation.constraints.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import com.devel.hibernate.Hibernate;

@Entity(name = "tbl_tarifa")
public class Tarifa extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;

    @Column
    @NotNull
    private boolean defecto;

    @Column
    @NotEmpty
    @Size(min = 1,max = 32,message = "Descripción")
    private String descripcion;

    @Column
    @NotNull
    private Date creacion;

    @Column
    @Digits(integer = 10,fraction = 2,message = "Precio")
    @DecimalMin("0.1")
    private Double precio;

    public Integer getId() {
        return id;
    }

    public boolean isDefecto() {
        return defecto;
    }

    public void setDefecto(boolean defecto) {
        this.defecto = defecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public static class ListCellRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Tarifa) {
                value = ((Tarifa) value).getDescripcion();
            }
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            return this;
        }
    }
}
