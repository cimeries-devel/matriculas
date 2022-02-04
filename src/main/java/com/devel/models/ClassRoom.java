package com.devel.models;

import com.devel.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "TBL_CLASSROOM")
public class ClassRoom extends Hibernate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "incremnet", strategy = "incremnet")
    private Integer ID;
    @Column
    private String NAME;
    @ManyToOne()
    private Section section;
    @ManyToOne()
    private Level level;


}
