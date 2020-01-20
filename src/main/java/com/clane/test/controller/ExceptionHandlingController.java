package com.clane.test.controller;

import com.clane.test.dto.response.ErrorResponse;
import com.clane.test.exception.AppException;
import com.clane.test.util.ErrorModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@RestController
@ControllerAdvice
public class ExceptionHandlingController {

    private final MessageSource messageSource;

    @Autowired
    public ExceptionHandlingController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> exception(AppException ex) {
        HttpStatus httpStatus = StringUtils.isEmpty(ex.getStatus()) ? HttpStatus.EXPECTATION_FAILED : ex.getStatus();
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .errorCode(httpStatus.value())
                        .errorMessage(ex.getMessage())
                        .build(), httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse methodArgumentNotValidException(final MethodArgumentNotValidException ex) {

        Locale currentLocale = LocaleContextHolder.getLocale();

        String notApplicableMessage = messageSource.getMessage("not_applicable", null, currentLocale);

        List<ErrorModel> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorModel(err.getField(), err.getRejectedValue(),
                        messageSource.getMessage(!StringUtils.isEmpty(err.getDefaultMessage()) ? err.getDefaultMessage() : "message_not_found",
                                null, currentLocale)))
                .distinct()
                .collect(Collectors.toList());

        /* Added to fix class level validator exception **/
        if (errorMessages.isEmpty() && ex.getBindingResult().getAllErrors().size() > 0) {
            for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
                String message = messageSource.getMessage(!StringUtils.isEmpty(
                        objectError.getDefaultMessage()) ? objectError.getDefaultMessage()
                        : "message_not_found", null, currentLocale);
                errorMessages.add(new ErrorModel("not_applicable", notApplicableMessage, message));
            }
        }

        String inputErrorMessage = messageSource.getMessage("input_validation_error", null, currentLocale);

        return ErrorResponse.builder()
                .errors(errorMessages)
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(inputErrorMessage)
                .build();
    }
}