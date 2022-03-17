package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Persona;
import com.devel.utilities.Utilidades;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class PersonaValidator extends ProgramaValidator {

    private static ConstraintViolation<Persona> isValidProperty(Persona persona, String property) {
        Set<ConstraintViolation<Persona>> violations = PROGRAMA_VALIDATOR.validateProperty(persona, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Persona>> loadViolations(Persona persona) {
        Set<ConstraintViolation<Persona>> violations = PROGRAMA_VALIDATOR.validate(persona);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Persona>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Persona> error1= (ConstraintViolation<Persona>) errores[0];
        String error = "Verfique el campo: "+error1.getMessage();
        Utilidades.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
