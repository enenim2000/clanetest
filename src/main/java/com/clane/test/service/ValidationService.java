package com.clane.test.service;

import com.clane.test.repository.ValidationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ValidationService {

    private final ValidationRepository validationRepository;

    /**
     *
     * @param validationRepository singleton instance of ValidationRepository object
     */
    public ValidationService(ValidationRepository validationRepository) {
        this.validationRepository = validationRepository;
    }

    /**
     * <p>Note: This method checks if an identifier exist in a given table.</p>
     * <p>Note: If the identifier exist then it returns true or else it returns false.</p>
     *
     * @param tableName table or class entity to search from
     * @param id the identifier to validate if it exist against the id column of the table
     * @return a boolean [true or false]
     */
    public boolean isValidId(String tableName, long id) {
        return validationRepository.IsValidId(tableName, id);
    }

    /**
     * <p>Note: This method checks if a given value already exist for a given {columnName} in a given {tableName}.</p>
     *
     * @param tableName table or class entity to search from
     * @param columnName the column name of the table
     * @param value the value to search for within {columnName} of the {tableName}
     * @return a boolean [true or false]
     */
    public boolean isUnique(String tableName, String columnName, Object value) {
        return validationRepository.isUnique(tableName, columnName, value);
    }
}