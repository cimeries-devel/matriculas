package com.devel.models;

import com.devel.hibernate.Hibernate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tbl_relacion")
public class Relacion extends Hibernate {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;

    @ManyToOne
    @NotNull
    private Persona persona;

    @Column
    @NotEmpty
    @Size(min = 3,max = 32,message = "Tipo de relación")
    private String tipoRelacion;

    @Column
    @NotNull
    private boolean apoderado;

    @ManyToOne
    @NotNull
    private Persona persona1;

    @Column
    @NotNull
    private boolean vivenJuntos;

    public Integer getId() {
        return id;
    }


    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getTipoRelacion() {
        return tipoRelacion;
    }

    public void setTipoRelacion(String tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

    public boolean isApoderado() {
        return apoderado;
    }

    public void setApoderado(boolean apoderado) {
        this.apoderado = apoderado;
    }

    public Persona getPersona1() {
        return persona1;
    }

    public void setPersona1(Persona persona1) {
        this.persona1 = persona1;
    }

    public boolean isVivenJuntos() {
        return vivenJuntos;
    }

    public void setVivenJuntos(boolean vivenJuntos) {
        this.vivenJuntos = vivenJuntos;
    }
}
