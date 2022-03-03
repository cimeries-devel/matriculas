package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Celular;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class CelularValidator extends ProgramaValidator {

    public static final String ID = "id";
    public static final String DESCRIPTION = "descripcion";
    public static final String NUMBER = "numero";

    private static ConstraintViolation<Celular> isValidProperty(Celular celular, String property) {
        Set<ConstraintViolation<Celular>> violations = PROGRAMA_VALIDATOR.validateProperty(celular, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Celular>> loadViolations(Celular celular) {
        Set<ConstraintViolation<Celular>> violations = PROGRAMA_VALIDATOR.validate(celular);
        return violations;
    }

    public ConstraintViolation<Celular> isValidDescription(Celular celular) {
        return isValidProperty(celular, DESCRIPTION);
    }

    public ConstraintViolation<Celular> isValidNumber(Celular celular) {
        return isValidProperty(celular, NUMBER);
    }
}