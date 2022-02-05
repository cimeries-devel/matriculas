package com.devel.models;

import com.devel.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "tbl_salon")
public class Salon extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;
    @Column
    private String nombre;
    @ManyToOne()
    private com.devel.models.seccion seccion;
    @ManyToOne()
    private Nivel nivel;

    @ManyToOne
    @JoinColumn(name = "FK_grado")
    private Grado grado;

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public com.devel.models.seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(com.devel.models.seccion seccion) {
        this.seccion = seccion;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    @Override
    public void save(){
        super.save();
    }
}
