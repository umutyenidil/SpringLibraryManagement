package com.umutyenidil.librarymanagement.handler;

import com.umutyenidil.librarymanagement.language.LanguageDuplicatonException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .badRequest()
                .body(
                        ErrorResponse.builder()
                                .message(messageSource.getMessage("error.body.missing", null, LocaleContextHolder.getLocale()))
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        var errors = new HashMap<String, String>();

        exception.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .messages(errors)
                                .build()
                );
    }

    @ExceptionHandler(LanguageDuplicatonException.class)
    public ResponseEntity<ErrorResponse> handleLanguageDuplicationException(LanguageDuplicatonException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.builder()
                                .message(messageSource.getMessage("error.language.duplicate", null, LocaleContextHolder.getLocale()))
                                .build()
                );
    }
}
