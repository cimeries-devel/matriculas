package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Ubigeo;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class UbigeoValidator extends ProgramaValidator {

    private static ConstraintViolation<Ubigeo> isValidProperty(Ubigeo ubigeo, String property) {
        Set<ConstraintViolation<Ubigeo>> violations = PROGRAMA_VALIDATOR.validateProperty(ubigeo, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
