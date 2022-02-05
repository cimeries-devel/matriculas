package com.devel.models;

import com.devel.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "tbl_persona")
public class Persona extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;
    @Column
    private String direccion;
    @Column
    private int edad;
    @Column
    private Date cumpleaños;
    @Column
    private Date creacion;
    @Column
    private String email;
    @Column
    private String nombres;
    @Column
    private String apellidos;
    @Column
    private boolean vivenJuntos;
    @Column
    private Date actualizacion;
    @Column
    private String codigo;
    @OneToMany(mappedBy = "persona")
    private List<Documento> documentos = new ArrayList<>();
    @ManyToOne
    private Persona persona;
    @Column
    private String tipoRelacion;
    @OneToMany(mappedBy = "persona")
    private List<Persona> personas = new ArrayList<>();
    @OneToMany
    private List<Phone> phones = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "persona_secure",
            joinColumns = {@JoinColumn(name = "fk_person")},
            inverseJoinColumns = {@JoinColumn(name = "fk_secure")}
    )
    private List<Seguro> seguros = new ArrayList<>();

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Documento> getDocuments() {
        return documentos;
    }

    public void setDocuments(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public List<Seguro> getSecures() {
        return seguros;
    }

    public Persona getPerson() {
        return persona;
    }

    public void setPerson(Persona persona) {
        this.persona = persona;
    }

    public void setSecures(List<Seguro> seguros) {
        this.seguros = seguros;
    }

    public String getTipoRelacion() {
        return tipoRelacion;
    }

    public void setTipoRelacion(String tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

    public Integer getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getCumpleaños() {
        return cumpleaños;
    }

    public void setCumpleaños(Date cumpleaños) {
        this.cumpleaños = cumpleaños;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean isVivenJuntos() {
        return vivenJuntos;
    }

    public void setVivenJuntos(boolean vivenJuntos) {
        this.vivenJuntos = vivenJuntos;
    }

    public Date getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(Date actualizacion) {
        this.actualizacion = actualizacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }
}
