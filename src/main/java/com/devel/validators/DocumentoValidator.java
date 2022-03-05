package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.Documento;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class DocumentoValidator extends ProgramaValidator {

    private static ConstraintViolation<Documento> isValidProperty(Documento documento, String property) {
        Set<ConstraintViolation<Documento>> violations = PROGRAMA_VALIDATOR.validateProperty(documento, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
