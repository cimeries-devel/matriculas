package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Relacion;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class RelacionValidator extends ProgramaValidator {

    private static ConstraintViolation<Relacion> isValidProperty(Relacion relacion, String property) {
        Set<ConstraintViolation<Relacion>> violations = PROGRAMA_VALIDATOR.validateProperty(relacion, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
