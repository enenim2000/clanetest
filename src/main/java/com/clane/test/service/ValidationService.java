package com.clane.test.service;

import com.clane.test.repository.ValidationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ValidationService {

    private final ValidationRepository validationRepository;

    public ValidationService(ValidationRepository validationRepository) {
        this.validationRepository = validationRepository;
    }

    public boolean isValidId(String tableName, long id) {
        return validationRepository.IsValidId(tableName, id);
    }

    public boolean isUnique(String tableName, String columnName, Object value) {
        return validationRepository.isUnique(tableName, columnName, value);
    }
}