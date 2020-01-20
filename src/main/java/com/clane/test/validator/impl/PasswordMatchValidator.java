package com.clane.test.validator.impl;

import com.clane.test.dto.request.SignUpRequest;
import com.clane.test.validator.PasswordMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, SignUpRequest> {

    @Override
    public void initialize(PasswordMatch annotation) {

    }

    @Override
    public boolean isValid(SignUpRequest request, ConstraintValidatorContext cvc) {
        return request != null && request.getPassword().equals(request.getConfirmPassword());
    }
}
