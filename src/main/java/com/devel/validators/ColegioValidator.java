package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Seccion;
import com.devel.utilities.Utilidades;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class ColegioValidator extends ProgramaValidator {

    private static ConstraintViolation<Colegio> isValidProperty(Colegio colegio, String property) {
        Set<ConstraintViolation<Colegio>> violations = PROGRAMA_VALIDATOR.validateProperty(colegio, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Seccion>> loadViolations(Seccion seccion) {
        Set<ConstraintViolation<Seccion>> violations = PROGRAMA_VALIDATOR.validate(seccion);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Colegio>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Colegio> error1= (ConstraintViolation<Colegio>) errores[0];
        String error = "Verfique el campo: "+error1.getMessage();
        Utilidades.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
