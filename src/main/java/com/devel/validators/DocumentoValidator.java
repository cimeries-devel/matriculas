package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Documento;
import com.devel.utilities.Utilidades;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class DocumentoValidator extends ProgramaValidator {

    private static ConstraintViolation<Documento> isValidProperty(Documento documento, String property) {
        Set<ConstraintViolation<Documento>> violations = PROGRAMA_VALIDATOR.validateProperty(documento, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Documento>> loadViolations(Documento documento) {
        Set<ConstraintViolation<Documento>> violations = PROGRAMA_VALIDATOR.validate(documento);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Documento>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Documento> error1= (ConstraintViolation<Documento>) errores[0];
        String error = "Verfique el campo: "+error1.getPropertyPath();
        Utilidades.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
