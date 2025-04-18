package com.devel.models;

import com.devel.hibernate.Hibernate;
import com.sun.istack.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Date creacion;

    @Column
    @Email(message = "Email")
    private String email;

    @Column
    @NotEmpty
    @Size(min = 2,max = 32,message = "Nombres")
    private String nombres;

    @Column
    private String imagen;

    @Column
    @NotEmpty
    @Size(min = 2,max = 32,message = "Apellidos")
    private String apellidos;

    @Column
    @NotEmpty
    @Size(min = 7,max = 32,message = "Contraseña")
    private String contraseña;

    @Column
    @NotNull
    private Date actualizacion;

    @Column
    @NotEmpty
    @Size(min = 5,max = 32,message = "Usuario")
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
        this.nombres = nombres.toUpperCase();
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
        this.apellidos = apellidos.toUpperCase();
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
