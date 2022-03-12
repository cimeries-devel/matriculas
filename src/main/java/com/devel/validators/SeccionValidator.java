package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Seccion;
import com.devel.models.Seguro;
import com.devel.utilities.Utilities;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class SeccionValidator extends ProgramaValidator {

    private static ConstraintViolation<Seccion> isValidProperty(Seccion seccion, String property) {
        Set<ConstraintViolation<Seccion>> violations = PROGRAMA_VALIDATOR.validateProperty(seccion, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Seccion>> loadViolations(Seccion seccion) {
        Set<ConstraintViolation<Seccion>> violations = PROGRAMA_VALIDATOR.validate(seccion);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Seccion>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Seccion> error1= (ConstraintViolation<Seccion>) errores[0];
        String error = "Verfique el campo: "+error1.getPropertyPath();
        Utilities.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
