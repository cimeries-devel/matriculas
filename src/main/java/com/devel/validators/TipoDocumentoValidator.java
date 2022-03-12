package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Seccion;
import com.devel.models.TipoDocumento;
import com.devel.utilities.Utilities;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class TipoDocumentoValidator extends ProgramaValidator {

    private static ConstraintViolation<TipoDocumento> isValidProperty(TipoDocumento tipoDocumento, String property) {
        Set<ConstraintViolation<TipoDocumento>> violations = PROGRAMA_VALIDATOR.validateProperty(tipoDocumento, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<TipoDocumento>> loadViolations(TipoDocumento tipoDocumento) {
        Set<ConstraintViolation<TipoDocumento>> violations = PROGRAMA_VALIDATOR.validate(tipoDocumento);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<TipoDocumento>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<TipoDocumento> error1= (ConstraintViolation<TipoDocumento>) errores[0];
        String error = "Verfique el campo: "+error1.getPropertyPath();
        Utilities.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
