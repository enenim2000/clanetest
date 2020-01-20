package com.clane.test.validator;

import com.clane.test.validator.impl.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordMatchValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PasswordMatch {
    
    public String message() default "Confirm password must match password";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}