package com.clane.test.validator.impl;

import com.clane.test.service.ValidationService;
import com.clane.test.validator.IsValidId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsValidIdValidator implements ConstraintValidator<IsValidId, Long> {

    private String entity;
    private boolean ignoreZero;

    private final ValidationService validationService;

    @Autowired
    public IsValidIdValidator(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public void initialize(IsValidId annotation) {
        entity = annotation.entity().getSimpleName();
        ignoreZero = annotation.ignoreZero();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext cvc) {
        if (!ignoreZero && value == 0) {
            return false;
        } else if (ignoreZero) {
            return true;
        }
        return !validationService.isValidId(entity, value);
    }
}
