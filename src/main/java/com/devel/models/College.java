package com.devel.models;

import com.devel.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "TBL_COLLEGE")
public class College extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer ID;
    @Column
    private String ADDRESS;
    @Column
    private String COLLEGENAME;
    @Column
    private Date CREATED;
    @Column
    private String EMAIL;
    @Column
    private String NUMBER;
    @Column
    private String RUC;
    @Column
    private Date UPDATED;
    @Column
    private String URBANIZATION;
    @Column
    private String WEBSITE;
    @ManyToOne
    @JoinColumn(name = "FK_UBIGEO")
    private Ubigeo ubigeo;
    @OneToMany
    private List<Phone> phones = new ArrayList<>();

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getCOLLEGENAME() {
        return COLLEGENAME;
    }

    public void setCOLLEGENAME(String COLLEGENAME) {
        this.COLLEGENAME = COLLEGENAME;
    }

    public Date getCREATED() {
        return CREATED;
    }

    public void setCREATED(Date CREATED) {
        this.CREATED = CREATED;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getRUC() {
        return RUC;
    }

    public void setRUC(String RUC) {
        this.RUC = RUC;
    }

    public Date getUPDATED() {
        return UPDATED;
    }

    public void setUPDATED(Date UPDATED) {
        this.UPDATED = UPDATED;
    }

    public String getURBANIZATION() {
        return URBANIZATION;
    }

    public void setURBANIZATION(String URBANIZATION) {
        this.URBANIZATION = URBANIZATION;
    }

    public String getWEBSITE() {
        return WEBSITE;
    }

    public void setWEBSITE(String WEBSITE) {
        this.WEBSITE = WEBSITE;
    }

    public Ubigeo getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(Ubigeo ubigeo) {
        this.ubigeo = ubigeo;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

}
