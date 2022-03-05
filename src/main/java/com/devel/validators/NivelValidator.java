package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Nivel;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class NivelValidator extends ProgramaValidator {

    private static ConstraintViolation<Nivel> isValidProperty(Nivel nivel, String property) {
        Set<ConstraintViolation<Nivel>> violations = PROGRAMA_VALIDATOR.validateProperty(nivel, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
