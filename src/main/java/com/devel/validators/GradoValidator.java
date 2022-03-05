package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Grado;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class GradoValidator extends ProgramaValidator {

    private static ConstraintViolation<Grado> isValidProperty(Grado grado, String property) {
        Set<ConstraintViolation<Grado>> violations = PROGRAMA_VALIDATOR.validateProperty(grado, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
