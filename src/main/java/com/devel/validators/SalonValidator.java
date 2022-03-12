package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Salon;
import com.devel.models.Seccion;
import com.devel.utilities.Utilities;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class SalonValidator extends ProgramaValidator {

    private static ConstraintViolation<Salon> isValidProperty(Salon salon, String property) {
        Set<ConstraintViolation<Salon>> violations = PROGRAMA_VALIDATOR.validateProperty(salon, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Salon>> loadViolations(Salon salon) {
        Set<ConstraintViolation<Salon>> violations = PROGRAMA_VALIDATOR.validate(salon);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Salon>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Salon> error1= (ConstraintViolation<Salon>) errores[0];
        String error = "Verfique el campo: "+error1.getPropertyPath();
        Utilities.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
