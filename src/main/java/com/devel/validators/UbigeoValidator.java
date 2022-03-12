package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Seccion;
import com.devel.models.Ubigeo;
import com.devel.utilities.Utilities;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class UbigeoValidator extends ProgramaValidator {

    private static ConstraintViolation<Ubigeo> isValidProperty(Ubigeo ubigeo, String property) {
        Set<ConstraintViolation<Ubigeo>> violations = PROGRAMA_VALIDATOR.validateProperty(ubigeo, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Ubigeo>> loadViolations(Ubigeo ubigeo) {
        Set<ConstraintViolation<Ubigeo>> violations = PROGRAMA_VALIDATOR.validate(ubigeo);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Ubigeo>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Ubigeo> error1= (ConstraintViolation<Ubigeo>) errores[0];
        String error = "Verfique el campo: "+error1.getPropertyPath();
        Utilities.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
