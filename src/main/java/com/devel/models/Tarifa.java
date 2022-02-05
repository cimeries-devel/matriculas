package com.devel.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import com.devel.hibernate.Hibernate;

@Entity(name = "tbl_tarifa")
public class Tarifa extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;
    @Column
    private boolean precioDefecto;
    @Column
    private String descripcion;
    @Column
    private Date creacion;
    @Column
    private Double precio;

    public Integer getId() {
        return id;
    }

    public boolean isPrecioDefecto() {
        return precioDefecto;
    }

    public void setPrecioDefecto(boolean precioDefecto) {
        this.precioDefecto = precioDefecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
}
