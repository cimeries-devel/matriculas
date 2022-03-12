package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Registro;
import com.devel.models.Seccion;
import com.devel.utilities.Utilities;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class RegistroValidator extends ProgramaValidator {

    private static ConstraintViolation<Registro> isValidProperty(Registro registro, String property) {
        Set<ConstraintViolation<Registro>> violations = PROGRAMA_VALIDATOR.validateProperty(registro, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }
    public static Set<ConstraintViolation<Seccion>> loadViolations(Seccion seccion) {
        Set<ConstraintViolation<Seccion>> violations = PROGRAMA_VALIDATOR.validate(seccion);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Registro>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Registro> error1= (ConstraintViolation<Registro>) errores[0];
        String error = "Verfique el campo: "+error1.getPropertyPath();
        Utilities.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
