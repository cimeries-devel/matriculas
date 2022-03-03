package com.devel.hibernate;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public abstract class ProgramaValidator {
    private static ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();
    protected static Validator PROGRAMA_VALIDATOR = FACTORY.getValidator();
}
