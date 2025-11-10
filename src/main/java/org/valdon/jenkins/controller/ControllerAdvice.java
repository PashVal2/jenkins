package org.valdon.jenkins.controller;

import org.valdon.jenkins.exception.ExceptionBody;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.valdon.jenkins.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody MethodArgumentNotValidHandler(
        MethodArgumentNotValidException ex
    ) {
        ExceptionBody exBody = new ExceptionBody("Validation Failed");
        List<FieldError> fieldErrors = ex.getFieldErrors();
        exBody.setErrors(fieldErrors.stream()
                .collect(Collectors.toMap(FieldError::getField,
                        FieldError::getDefaultMessage))
        );
        return exBody;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody ResourceNotFoundHandler(ResourceNotFoundException ex) {
        return new ExceptionBody(ex.getMessage());
    }

}
