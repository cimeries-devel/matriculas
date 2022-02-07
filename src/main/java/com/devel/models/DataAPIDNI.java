package com.devel.models;

import com.devel.hibernate.Hibernate;
import com.devel.utilities.ClientAPI;

import javax.persistence.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Entity(name = "DataApiDni_tbl")
public class DataAPIDNI extends Hibernate {
    @Id
    private String numero;
    @Column
    private String nombre_completo;
    @Column
    private String nombres;
    @Column
    private String apellido_paterno;
    @Column
    private String apellido_materno;
    @Column
    private int codigo_verificacion;
    @Column
    private String departamento;
    @Column
    private String provincia;
    @Column
    private String distrito;
    @Column
    private String direccion;
    @Column
    private String direccion_completa;

    @Transient
    private List<String> domicilio_ubigeo;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public int getCodigo_verificacion() {
        return codigo_verificacion;
    }

    public void setCodigo_verificacion(int codigo_verificacion) {
        this.codigo_verificacion = codigo_verificacion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion_completa() {
        return direccion_completa;
    }

    public void setDireccion_completa(String direccion_completa) {
        this.direccion_completa = direccion_completa;
    }

    public List<String> getDomicilio_ubigeo() {
        return domicilio_ubigeo;
    }

    public void setDomicilio_ubigeo(List<String> domicilio_ubigeo) {
        this.domicilio_ubigeo = domicilio_ubigeo;
    }
    @Override
    public void guardar() {
        session.beginTransaction();
        session.save(this);
        session.getTransaction().commit();
    }

    public static DataAPIDNI getFromDataBase(String id) {
        DataAPIDNI datos = session.find(DataAPIDNI.class, id, LockModeType.NONE);
        return datos;
    }

    public static DataAPIDNI getFromApi(String id){
        ModeloApiDNI modeloApiDNI = ClientAPI.getUserByDni(id,null);
        DataAPIDNI dataAPIDNI=null;
        if(modeloApiDNI!=null){
            if(modeloApiDNI.isSuccess()){
                dataAPIDNI = modeloApiDNI.getDataAPIDNI();
                if(getFromDataBase(modeloApiDNI.getDataAPIDNI().getNumero())==null){
                    dataAPIDNI.guardar();
                }
            }
        }
        return dataAPIDNI;
    }
    public static DataAPIDNI getFromApiPrueba(String id,String token){
        ModeloApiDNI modeloApiDNI =ClientAPI.getUserByDni(id,token);
        DataAPIDNI dataAPIDNI=null;
        if(modeloApiDNI!=null){
            if(modeloApiDNI.isSuccess()){
                dataAPIDNI = modeloApiDNI.getDataAPIDNI();
                if(getFromDataBase(modeloApiDNI.getDataAPIDNI().getNumero())==null){
                    dataAPIDNI.guardar();
                }
            }
        }
        return dataAPIDNI;
    }

    public static DataAPIDNI getDatosDni(String id){
        DataAPIDNI dataAPIDNI=getFromDataBase(id);
        if(dataAPIDNI==null){
            dataAPIDNI=getFromApi(id);
        }
        return dataAPIDNI;
    }

}
