package com.devel.models;

import com.devel.hibernate.Hibernate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "tbl_nivel")
public class Nivel extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;

    @Column
    @NotNull
    private Date creacion;

    @Column
    @NotEmpty
    @Size(min = 2,max = 32,message = "Descripción")
    private String descripcion;

    @Column
    @NotNull(message = "Hora fin")
    private Date horaFin;

    @Column
    @NotNull(message = "Hora inicio")
    private Date horaInicio;

    @OneToMany(mappedBy = "nivel")
    private List<Salon> salons =new ArrayList<>();

    @OneToMany(mappedBy = "nivel")
    private List<Grado> grados=new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public List<Salon> getSalons() {
        return salons;
    }

    public void setSalons(List<Salon> salons) {
        this.salons = salons;
    }

    public List<Grado> getGrados() {
        return grados;
    }
    public List<Grado> getGradosConTodos(){
        List<Grado> gradosConTodos=new ArrayList<>(grados);
        Grado grado=new Grado();
        grado.setGrado("Todos");
        gradosConTodos.add(0,grado);
        return gradosConTodos;
    }

    public void setGrados(List<Grado> grados) {
        this.grados = grados;
    }

    public static class ListCellRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Nivel) {
                value = ((Nivel) value).getDescripcion();
            }
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            return this;
        }
    }

}
