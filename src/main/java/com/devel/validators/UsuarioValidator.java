package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Usuario;
import com.devel.utilities.Utilidades;
import jakarta.validation.ConstraintViolation;

import java.awt.*;
import java.util.Set;

public class UsuarioValidator extends ProgramaValidator {

    private static ConstraintViolation<Usuario> isValidProperty(Usuario usuario, String property) {
        Set<ConstraintViolation<Usuario>> violations = PROGRAMA_VALIDATOR.validateProperty(usuario, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Usuario>> loadViolations(Usuario usuario) {
        Set<ConstraintViolation<Usuario>> violations = PROGRAMA_VALIDATOR.validate(usuario);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Usuario>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Usuario> error1= (ConstraintViolation<Usuario>) errores[0];
        String error = "Verfique el campo: "+error1.getPropertyPath();
        Utilidades.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
