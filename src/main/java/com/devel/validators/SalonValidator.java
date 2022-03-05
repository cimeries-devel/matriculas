package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Salon;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class SalonValidator extends ProgramaValidator {

    private static ConstraintViolation<Salon> isValidProperty(Salon salon, String property) {
        Set<ConstraintViolation<Salon>> violations = PROGRAMA_VALIDATOR.validateProperty(salon, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
