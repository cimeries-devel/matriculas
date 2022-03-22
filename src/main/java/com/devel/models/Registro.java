package com.devel.models;

import com.devel.hibernate.Hibernate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "tbl_registro")
public class Registro extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;

    @Column
    @NotNull
    private Date creacion;

    @Column
    @NotNull
    private Date actualizacion;

    @ManyToOne
    @JoinColumn(name = "FK_CLASSROOM")
    @NotNull(message = "Salón")
    private Salon salon;

    @ManyToOne
    @JoinColumn(name = "FK_STUDENT")
    @NotNull(message = "Estudiante")
    private Persona estudiante;

    @ManyToOne
    @JoinColumn(name = "FK_RATE")
    @NotNull(message = "Tarifa")
    private Tarifa tarifa;

    public Integer getId() {
        return id;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public Date getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(Date actualizacion) {
        this.actualizacion = actualizacion;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public Persona getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Persona estudiante) {
        this.estudiante = estudiante;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }
}
