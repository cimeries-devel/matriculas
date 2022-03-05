package com.devel.models;

import com.devel.hibernate.Hibernate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "tbl_celular")
public class Celular extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @Column
    @NotEmpty
    @Size(min = 3, max = 32)
    private String descripcion;

    @Column
    @NotEmpty
    @Size(min = 9, max = 13)
    private String numero;

    public Integer getId() {
        return id;
    }

    public String getDescipcion() {
        return descripcion;
    }

    public void setDescipcion(String descipcion) {
        this.descripcion = descipcion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
