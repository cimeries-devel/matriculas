package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Relacion;
import com.devel.utilities.Utilidades;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class RelacionValidator extends ProgramaValidator {

    private static ConstraintViolation<Relacion> isValidProperty(Relacion relacion, String property) {
        Set<ConstraintViolation<Relacion>> violations = PROGRAMA_VALIDATOR.validateProperty(relacion, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Relacion>> loadViolations(Relacion relacion) {
        Set<ConstraintViolation<Relacion>> violations = PROGRAMA_VALIDATOR.validate(relacion);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Relacion>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Relacion> error1= (ConstraintViolation<Relacion>) errores[0];
        String error = "Verfique el campo: "+error1.getPropertyPath();
        Utilidades.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
