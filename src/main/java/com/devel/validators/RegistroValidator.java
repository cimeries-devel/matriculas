package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Registro;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class RegistroValidator extends ProgramaValidator {

    private static ConstraintViolation<Registro> isValidProperty(Registro registro, String property) {
        Set<ConstraintViolation<Registro>> violations = PROGRAMA_VALIDATOR.validateProperty(registro, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
