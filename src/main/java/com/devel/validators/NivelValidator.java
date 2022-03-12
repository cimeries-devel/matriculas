package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Nivel;
import com.devel.models.Seccion;
import com.devel.utilities.Utilities;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class NivelValidator extends ProgramaValidator {

    private static ConstraintViolation<Nivel> isValidProperty(Nivel nivel, String property) {
        Set<ConstraintViolation<Nivel>> violations = PROGRAMA_VALIDATOR.validateProperty(nivel, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Seccion>> loadViolations(Seccion seccion) {
        Set<ConstraintViolation<Seccion>> violations = PROGRAMA_VALIDATOR.validate(seccion);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Nivel>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Nivel> error1= (ConstraintViolation<Nivel>) errores[0];
        String error = "Verfique el campo: "+error1.getPropertyPath();
        Utilities.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
