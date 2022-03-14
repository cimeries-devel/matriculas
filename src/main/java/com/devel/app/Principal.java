package com.devel.app;

import com.devel.hibernate.Hibernate;
import com.devel.utilities.Propiedades;
import com.devel.utilities.Utilidades;
import com.devel.views.VLogin;

public class Principal
{
    public static void main( String[] args ) {
        Hibernate.initialize();
        Propiedades propiedades=new Propiedades();
        Utilidades.tema(propiedades.getTema());
        VLogin vLogin=new VLogin(propiedades);
        vLogin.setVisible(true);


//        Celular celular = new Celular();
//        CelularValidator validator = new CelularValidator();
//
//        ConstraintViolation<Celular> error = validator.isValidNumber(celular);
//        if (error == null) {
//            celular.guardar();
//        } else {
//            System.out.println(error.getPropertyPath());
//        }

//        Set<ConstraintViolation<Celular>> errors = validator.loadViolations(celular);
//        if(errors.isEmpty()){
//            celular.guardar();
//        }else {
//            errors.forEach(e -> System.out.println(e.getMessage()));
//        }
    }
}
