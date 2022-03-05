package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Seccion;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class SeccionValidator extends ProgramaValidator {

    private static ConstraintViolation<Seccion> isValidProperty(Seccion seccion, String property) {
        Set<ConstraintViolation<Seccion>> violations = PROGRAMA_VALIDATOR.validateProperty(seccion, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
