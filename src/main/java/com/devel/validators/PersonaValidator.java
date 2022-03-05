package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Persona;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class PersonaValidator extends ProgramaValidator {

    private static ConstraintViolation<Persona> isValidProperty(Persona persona, String property) {
        Set<ConstraintViolation<Persona>> violations = PROGRAMA_VALIDATOR.validateProperty(persona, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
