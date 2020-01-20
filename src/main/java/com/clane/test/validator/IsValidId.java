package com.clane.test.validator;

import com.clane.test.validator.impl.IsValidIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsValidIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface IsValidId {

    Class<?> entity();
    
    boolean ignoreZero() default false;
    
    public String message() default "Invalid Id";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
