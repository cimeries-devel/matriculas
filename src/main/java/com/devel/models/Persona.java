package com.devel.models;

import com.devel.hibernate.Hibernate;
import com.devel.utilities.Utilidades;
import jakarta.validation.constraints.*;
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
    @Size(min = 2,max = 32,message = "Dirección")
    private String direccion;

    @Column
    @Digits(integer = 3,fraction = 0,message = "Edad")
    private int edad;

    @Column
    @NotNull(message = "Nacimiento")
    private Date cumpleaños;

    @Column
    @NotNull
    private Date creacion;

    @Column
    @Email(message = "Email")
    private String email;

    @Column
    @NotNull
    private boolean genero;

    @Column
    @NotEmpty(message = "Nombres")
    private String nombres;

    @Column
    @NotEmpty(message = "Apellidos")
    private String apellidos;

    @Column
    @NotNull
    private Date actualizacion;

    @Column
    @Size(min = 2,max = 32,message = "Código")
    private String codigo;

    @OneToMany(mappedBy = "persona")
    private List<Documento> documentos = new ArrayList<>();

    @OneToMany(mappedBy = "persona1")
    private List<Relacion> relaciones = new ArrayList<>();

    @OneToMany(mappedBy = "persona")
    private List<Relacion> familiaresparaEstudiante = new ArrayList<>();

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
        this.direccion = direccion.toUpperCase();
    }

    public int getEdad() {
        return Utilidades.calcularaños(getCumpleaños());
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
        this.nombres = nombres.toUpperCase();
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos.toUpperCase();
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
        this.codigo = codigo.toUpperCase();
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
        for(Relacion relacion:getFamiliaresparaEstudiante()){
            if(relacion.isApoderado()){
                return relacion.getPersona1();
            }
        }
        return null;
    }
    public Relacion getRelacion(Persona persona){
        for(Relacion relacion:getRelaciones()){
            if(relacion.getPersona()==persona){
                return relacion;
            }
        }
        return null;
    }
    public Relacion getRelacionAFamiliar(Persona persona1){
        for(Relacion relacion:getFamiliaresparaEstudiante()){
            if(relacion.getPersona1()==persona1){
                return relacion;
            }
        }
        return null;
    }
    public String getTipoRelacion(Persona persona){
        for(Relacion relacion:getRelaciones()){
            if(relacion.getPersona()==persona){
                return relacion.getTipoRelacion();
            }
        }
        return null;
    }

    public void setTipoRelacionRelacion(Persona persona,String tiporelacion){
        for(Relacion relacion:getRelaciones()){
            if(relacion.getPersona1()==persona){
                relacion.setTipoRelacion(tiporelacion.toUpperCase());
            }
        }
    }

    public List<Relacion> getFamiliaresparaEstudiante() {
        return familiaresparaEstudiante;
    }

    public void setFamiliaresparaEstudiante(List<Relacion> familiaresparaEstudiante) {
        this.familiaresparaEstudiante = familiaresparaEstudiante;
    }
    public boolean existeDocumento(String dni){
        for(Documento documento:getDocumentos()){
            if(documento.getNumero().equals(dni)){
                return true;
            }
        }
        return false;
    }
    @Override
    public void guardar() {
        super.guardar();
    }
}
