package com.devel.models;

import com.devel.hibernate.Hibernate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "tbl_ubigeo")
public class Ubigeo extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;

    @Column
    @NotEmpty
    @Size(min = 3,max = 32)
    private String codigo;

    @Column
    @NotEmpty
    private Date creacion;

    @Column
    @NotEmpty
    @Size(min = 3,max = 32)
    private String departamento;

    @Column
    @NotEmpty
    @Size(min = 3,max = 32)
    private String distrito;

    @Column
    @NotEmpty
    @Size(min = 3,max = 32)
    private String provincia;

    @Column
    @NotEmpty
    private Date actualizacion;

    @OneToMany(mappedBy = "ubigeo")
    private List<Colegio> colegios =new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Date getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(Date actualizacion) {
        this.actualizacion = actualizacion;
    }

    public List<Colegio> getColegios() {
        return colegios;
    }

    public void setColegios(List<Colegio> colegios) {
        this.colegios = colegios;
    }
}
