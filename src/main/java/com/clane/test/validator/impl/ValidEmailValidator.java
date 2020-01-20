package com.clane.test.validator.impl;

import com.clane.test.validator.ValidEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(ValidEmail annotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cvc) {
        return email == null || validate(email);
    }

    private static boolean validate(String email) {
        return VALID_EMAIL_ADDRESS_REGEX .matcher(email).find();
    }
}
