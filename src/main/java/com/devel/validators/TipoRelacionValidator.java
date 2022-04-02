package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.TipoDocumento;
import com.devel.models.TipoRelacion;
import com.devel.utilities.Utilidades;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class TipoRelacionValidator extends ProgramaValidator {

    private static ConstraintViolation<TipoRelacion> isValidProperty(TipoRelacion tipoDocumento, String property) {
        Set<ConstraintViolation<TipoRelacion>> violations = PROGRAMA_VALIDATOR.validateProperty(tipoDocumento, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<TipoRelacion>> loadViolations(TipoRelacion tipoDocumento) {
        Set<ConstraintViolation<TipoRelacion>> violations = PROGRAMA_VALIDATOR.validate(tipoDocumento);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<TipoRelacion>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<TipoRelacion> error1= (ConstraintViolation<TipoRelacion>) errores[0];
        String error = "Verfique el campo: "+error1.getMessage();
        Utilidades.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
