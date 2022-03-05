package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Celular;
import com.devel.models.Colegio;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class ColegioValidator extends ProgramaValidator {

    private static ConstraintViolation<Colegio> isValidProperty(Colegio colegio, String property) {
        Set<ConstraintViolation<Colegio>> violations = PROGRAMA_VALIDATOR.validateProperty(colegio, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
