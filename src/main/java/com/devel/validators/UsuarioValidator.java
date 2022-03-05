package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Usuario;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class UsuarioValidator extends ProgramaValidator {

    private static ConstraintViolation<Usuario> isValidProperty(Usuario usuario, String property) {
        Set<ConstraintViolation<Usuario>> violations = PROGRAMA_VALIDATOR.validateProperty(usuario, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
