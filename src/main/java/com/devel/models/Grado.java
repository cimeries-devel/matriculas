package com.devel.models;

import com.devel.hibernate.Hibernate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tbl_grado")
public class Grado extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;

    @Column
    @NotEmpty
    @Size(min =1,max = 15,message = "Grado")
    private String grado;

    @OneToMany(mappedBy = "grado")
    private List<Salon> salon =new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "fk_nivel")
    private Nivel nivel;

    public Integer getId() {
        return id;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public List<Salon> getSalon() {
        return salon;
    }

    public void setSalon(List<Salon> salon) {
        this.salon = salon;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public static class ListCellRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Grado) {
                value = ((Grado) value).getGrado();
            }
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            return this;
        }
    }
}
