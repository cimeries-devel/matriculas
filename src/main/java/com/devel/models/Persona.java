package com.devel.models;

import com.devel.hibernate.Hibernate;
import com.devel.utilities.Utilities;
import com.sun.istack.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.swing.*;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity(name = "tbl_persona")
public class Persona extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;

    @Column
    @NotEmpty
    @Size(min = 3,max = 32)
    private String direccion;

    @Column
    @NotEmpty
    private int edad;

    @Column
    @NotEmpty
    private Date cumpleaños;

    @Column
    @NotEmpty
    private Date creacion;

    @Column
    @Nullable
    @Size(min = 11,max = 32)
    private String email;

    @Column
    @NotEmpty
    private boolean genero;

    @Column
    @NotEmpty
    @Size(min = 2,max = 32)
    private String nombres;

    @Column
    @NotEmpty
    @Size(min = 2,max = 32)
    private String apellidos;

    @Column
    @NotEmpty
    private Date actualizacion;

    @Column
    @NotEmpty
    @Size(min = 2,max = 32)
    private String codigo;

    @OneToMany(mappedBy = "persona")
    private List<Documento> documentos = new ArrayList<>();

    @OneToMany(mappedBy = "persona")
    private List<Relacion> relaciones = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante")
    private List<Registro> registros = new ArrayList<>();

    @OneToMany
    private List<Celular> celulares = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "persona_secure",
            joinColumns = {@JoinColumn(name = "fk_person")},
            inverseJoinColumns = {@JoinColumn(name = "fk_secure")}
    )
    private List<Seguro> seguros = new ArrayList<>();

    public List<Seguro> getSecures() {
        return seguros;
    }

    public List<Celular> getCelulares() {
        return celulares;
    }

    public void setCelulares(List<Celular> celulars) {
        this.celulares = celulares;
    }

    public List<Seguro> getSeguros() {
        return seguros;
    }

    public void setSeguros(List<Seguro> seguros) {
        this.seguros = seguros;
    }

    public void setSecures(List<Seguro> seguros) {
        this.seguros = seguros;
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
        return Utilities.calcularaños(getCumpleaños());
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

    public List<Relacion> getRelaciones() {
        return relaciones;
    }

    public void setRelaciones(List<Relacion> familiares) {
        this.relaciones = familiares;
    }

    public boolean isGenero() {
        return genero;
    }

    public void setGenero(boolean genero) {
        this.genero = genero;
    }

    public List<Registro> getRegistros() {
        return registros;
    }
    public Registro ultimaMatricula(){
        Registro ultimoRegistro=getRegistros().get(0);
        for (Registro registro:getRegistros()){
            if (ultimoRegistro.getCreacion().before(registro.getCreacion())){
                ultimoRegistro=registro;
            }
        }
        return ultimoRegistro;
    }
    public void setRegistros(List<Registro> registros) {
        this.registros = registros;
    }

    public Persona getApoderado(){
        for(Relacion relacion:getRelaciones()){
            if(relacion.isApoderado()){
                return relacion.getPersona1();
            }
        }
        return null;
    }
    public Relacion getRelacion(Persona persona1){
        for(Relacion relacion:getRelaciones()){
            if(relacion.getPersona1()==persona1){
                return relacion;
            }
        }
        return null;
    }
    public String getRelacionDeApoderado(){
        for(Relacion relacion:getRelaciones()){
            if(relacion.isApoderado()){
                return relacion.getTipoRelacion();
            }
        }
        return null;
    }

    public void setRelacionDeApoderado(String Tiporelacion){
        for(Relacion relacion:getRelaciones()){
            if(relacion.isApoderado()){
                relacion.setTipoRelacion(relacion.getTipoRelacion());
                relacion.guardar();
            }
        }
    }


}
