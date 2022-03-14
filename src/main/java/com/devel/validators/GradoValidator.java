package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Grado;
import com.devel.utilities.Utilidades;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class GradoValidator extends ProgramaValidator {

    private static ConstraintViolation<Grado> isValidProperty(Grado grado, String property) {
        Set<ConstraintViolation<Grado>> violations = PROGRAMA_VALIDATOR.validateProperty(grado, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Grado>> loadViolations(Grado grado) {
        Set<ConstraintViolation<Grado>> violations = PROGRAMA_VALIDATOR.validate(grado);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Grado>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Grado> error1= (ConstraintViolation<Grado>) errores[0];
        String error = "Verfique el campo: "+error1.getPropertyPath();
        Utilidades.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
