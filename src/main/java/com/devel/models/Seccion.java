package com.devel.models;

import com.devel.hibernate.Hibernate;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tbl_seccion")
public class Seccion extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;

    @Column
    @NotEmpty(message = "Sección")
    private String seccion;

    @OneToMany(mappedBy = "seccion")
    private List<Salon> salons =new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public List<Salon> getSalons() {
        return salons;
    }

    public void setSalons(List<Salon> salons) {
        this.salons = salons;
    }

    public static class ListCellRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Seccion) {
                value = ((Seccion) value).getSeccion();
            }
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            return this;
        }
    }
}
