package com.devel.models;

import com.devel.hibernate.Hibernate;
import com.devel.utilities.Utilities;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.swing.*;
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
    private boolean genero;
    @Column
    private String nombres;
    @Column
    private String apellidos;
    @Column
    private Date actualizacion;
    @Column
    private String codigo;
    @OneToMany(mappedBy = "persona")
    private List<Documento> documentos = new ArrayList<>();
    @OneToMany(mappedBy = "persona")
    private List<Relacion> relaciones = new ArrayList<>();
    @OneToMany(mappedBy = "estudiante")
    private List<Registro> registros = new ArrayList<>();
    @OneToMany
    private List<Celular> celulars = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "persona_secure",
            joinColumns = {@JoinColumn(name = "fk_person")},
            inverseJoinColumns = {@JoinColumn(name = "fk_secure")}
    )
    private List<Seguro> seguros = new ArrayList<>();

    public List<Celular> getPhones() {
        return celulars;
    }

    public void setPhones(List<Celular> celulars) {
        this.celulars = celulars;
    }

    public List<Seguro> getSecures() {
        return seguros;
    }


    public List<Celular> getCelulars() {
        return celulars;
    }

    public void setCelulars(List<Celular> celulars) {
        this.celulars = celulars;
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
        return calcularaños(getCumpleaños());
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
    public String getRelacionDeApoderado(){
        for(Relacion relacion:getRelaciones()){
            if(relacion.isApoderado()){
                return relacion.getTipoRelacion();
            }
        }
        return null;
    }

    public static Integer calcularaños(Date fecha){
        Calendar hoy=Calendar.getInstance();
        Calendar nacimiento=Calendar.getInstance();
        nacimiento.setTime(fecha);
        int años= hoy.get(Calendar.YEAR)-nacimiento.get(Calendar.YEAR);
        int meses= hoy.get(Calendar.MONTH)-nacimiento.get(Calendar.MONTH);
        int dias= hoy.get(Calendar.DAY_OF_MONTH)-nacimiento.get(Calendar.DAY_OF_MONTH);
        JOptionPane.showMessageDialog(null,""+años);
        switch (meses){
            case 0:
                if(dias<0){
                    años-=1;
                }
                break;
            default:
                if(meses<0){
                    años-=1;
                }
        }
        return años<0?0:años;
    }
}
