package com.clane.test.validator.impl;

import com.clane.test.service.ValidationService;
import com.clane.test.validator.IsUnique;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsUniqueValidator implements ConstraintValidator<IsUnique, Object> {

    private String tableName;
    private String columnName;
    private boolean required;
    private boolean shouldBeUnique;

    private final ValidationService validationService;

    @Autowired
    public IsUniqueValidator(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public void initialize(IsUnique annotation) {
        tableName = annotation.entity().getSimpleName();
        columnName = annotation.fieldName();
        required = annotation.required();
        shouldBeUnique = annotation.shouldBeUnique();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext cvc) {
        return (required || !StringUtils.isBlank(value.toString())) && shouldBeUnique && validationService.isUnique(tableName, columnName, value);
    }
}
