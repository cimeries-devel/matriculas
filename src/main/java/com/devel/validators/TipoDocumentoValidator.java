package com.devel.validators;

import com.devel.hibernate.ProgramaValidator;
import com.devel.models.Colegio;
import com.devel.models.TipoDocumento;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class TipoDocumentoValidator extends ProgramaValidator {

    private static ConstraintViolation<TipoDocumento> isValidProperty(TipoDocumento tipoDocumento, String property) {
        Set<ConstraintViolation<TipoDocumento>> violations = PROGRAMA_VALIDATOR.validateProperty(tipoDocumento, property);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

}
