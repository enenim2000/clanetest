package com.clane.test.validator.impl;

import com.clane.test.validator.ValidPassword;
import lombok.extern.slf4j.Slf4j;
import org.passay.*;
import org.passay.dictionary.ArrayWordList;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Slf4j
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private DictionaryRule dictionaryRule;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        String[] array = new String[] {"123456789", "password", "computer", "password123"};
        Arrays.sort(array);
        dictionaryRule = new DictionaryRule(
                new WordListDictionary(new ArrayWordList(
                        array)));
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(

                // at least 8 characters
                new LengthRule(8, 30),

                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),

                // no whitespace
                new WhitespaceRule(),

                // no common passwords
                dictionaryRule
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        return result.isValid();
    }
}