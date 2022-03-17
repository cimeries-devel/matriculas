package com.devel.models;

import com.devel.hibernate.Hibernate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;


@Entity(name = "tbl_documento")
public class Documento extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer id;

//    @Column(unique = true)
    @NotEmpty
    @Size(min = 8,max = 15)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "fk_persona")
    @NotNull
    private Persona persona;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "fk_tipoDcoumento")
    private TipoDocumento tipoDocumento;

    public Integer getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Persona getPerson() {
        return persona;
    }

    public void setPerson(Persona persona) {
        this.persona = persona;
    }

    public TipoDocumento getTypeDocument() {
        return tipoDocumento;
    }

    public void setTypeDocument(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

}
