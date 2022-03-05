package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Tarifa;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class TarifaValidator extends ProgramaValidator {

    private static ConstraintViolation<Tarifa> isValidProperty(Tarifa tarifa, String property) {
        Set<ConstraintViolation<Tarifa>> violations = PROGRAMA_VALIDATOR.validateProperty(tarifa, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
