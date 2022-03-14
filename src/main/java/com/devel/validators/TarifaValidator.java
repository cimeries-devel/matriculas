package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Tarifa;
import com.devel.utilities.Utilidades;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class TarifaValidator extends ProgramaValidator {

    private static ConstraintViolation<Tarifa> isValidProperty(Tarifa tarifa, String property) {
        Set<ConstraintViolation<Tarifa>> violations = PROGRAMA_VALIDATOR.validateProperty(tarifa, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Tarifa>> loadViolations(Tarifa tarifa) {
        Set<ConstraintViolation<Tarifa>> violations = PROGRAMA_VALIDATOR.validate(tarifa);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Tarifa>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Tarifa> error1= (ConstraintViolation<Tarifa>) errores[0];
        String error = "Verfique el campo: "+error1.getPropertyPath();
        Utilidades.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
