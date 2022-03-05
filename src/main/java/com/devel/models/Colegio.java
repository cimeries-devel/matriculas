package com.devel.models;

import com.devel.hibernate.Hibernate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "tbl_colegio")
public class Colegio extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;

    @Column
    @NotEmpty
    @Size(min = 3, max = 32)
    private String direccion;

    @Column
    @NotEmpty
    @Size(min = 3, max = 32)
    private String nombreColegio;

    @Column
    @NotEmpty
    private Date creacion;

    @Column
    @NotEmpty
    @Size(min =12, max = 32)
    private String email;

    @Column
    @NotEmpty
    @Size(min = 11, max = 11)
    private String ruc;

    @Column
    @NotEmpty
    private Date actualizacion;

    @Column
    @NotEmpty
    @Size(min = 3, max = 32)
    private String urbanizacion;

    @Column
    private String website;

    @ManyToOne
    @JoinColumn(name = "fk_ubigeo")
    @NotEmpty
    private Ubigeo ubigeo;

    @OneToMany
    private List<Celular> celulars = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreColegio() {
        return nombreColegio;
    }

    public void setNombreColegio(String nombreColegio) {
        this.nombreColegio = nombreColegio;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Date getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(Date actualizacion) {
        this.actualizacion = actualizacion;
    }

    public String getUrbanizacion() {
        return urbanizacion;
    }

    public void setUrbanizacion(String urbanizacion) {
        this.urbanizacion = urbanizacion;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Ubigeo getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(Ubigeo ubigeo) {
        this.ubigeo = ubigeo;
    }

    public List<Celular> getPhones() {
        return celulars;
    }

    public void setPhones(List<Celular> celulars) {
        this.celulars = celulars;
    }

}
