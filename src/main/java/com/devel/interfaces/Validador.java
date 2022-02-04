package com.devel.interfaces;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public interface Validador {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validador = factory.getValidator();
}
