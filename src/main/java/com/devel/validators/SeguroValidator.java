package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Celular;
import com.devel.models.Seguro;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class SeguroValidator extends ProgramaValidator {

    public static final String ID = "id";
    public static final String DESCRIPTION = "descripcion";
    public static final String NUMBER = "numero";

    private static ConstraintViolation<Seguro> isValidProperty(Seguro seguro, String property) {
        Set<ConstraintViolation<Seguro>> violations = PROGRAMA_VALIDATOR.validateProperty(seguro, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    public static Set<ConstraintViolation<Seguro>> loadViolations(Seguro seguro) {
        Set<ConstraintViolation<Seguro>> violations = PROGRAMA_VALIDATOR.validate(seguro);
        return violations;
    }

    public ConstraintViolation<Seguro> isValidDescription(Seguro seguro) {
        return isValidProperty(seguro, DESCRIPTION);
    }

    public ConstraintViolation<Seguro> isValidNumber(Seguro seguro) {
        return isValidProperty(seguro, NUMBER);
    }
}