package com.devel.models;

import com.devel.hibernate.Hibernate;
import com.sun.istack.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "tbl_usuario")
public class Usuario extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;

    @Column
    @NotEmpty
    private Date creacion;

    @Column
    @Nullable
    @Size(min = 3,max = 32)
    private String email;

    @Column
    @NotEmpty
    @Size(min = 2,max = 32)
    private String nombres;

    @Column
    private String imagen;

    @Column
    @NotEmpty
    @Size(min = 2,max = 32)
    private String apellidos;

    @Column
    @NotEmpty
    @Size(min = 7,max = 32)
    private String contraseña;

    @Column
    @NotEmpty
    private Date actualizacion;

    @Column
    @NotEmpty
    @Size(min = 7,max = 32)
    private String usuario;

    @OneToMany
    private List<Celular> celulars = new ArrayList<>();

    public Integer getId() {
        return id;
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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Date getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(Date actualizacion) {
        this.actualizacion = actualizacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Celular> getPhones() {
        return celulars;
    }

    public void setPhones(List<Celular> celulars) {
        this.celulars = celulars;
    }
}
