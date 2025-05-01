package com.umutyenidil.librarymanagement.handler;

import com.umutyenidil.librarymanagement.author.AuthorNotFoundException;
import com.umutyenidil.librarymanagement.category.Category;
import com.umutyenidil.librarymanagement.category.CategoryDuplicationException;
import com.umutyenidil.librarymanagement.category.CategoryNotFoundException;
import com.umutyenidil.librarymanagement.genre.GenreDuplicationException;
import com.umutyenidil.librarymanagement.genre.GenreNotFoundException;
import com.umutyenidil.librarymanagement.language.LanguageDuplicatonException;
import com.umutyenidil.librarymanagement.language.LanguageNotFoundException;
import com.umutyenidil.librarymanagement.publisher.Publisher;
import com.umutyenidil.librarymanagement.publisher.PublisherDuplicationException;
import com.umutyenidil.librarymanagement.publisher.PublisherNotFoundException;
import com.umutyenidil.librarymanagement.translator.TranslatorNotFoundException;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.UUID;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .badRequest()
                .body(
                        ErrorResponse.builder()
                                .message(messageSource.getMessage("error.body.missing", null, LocaleContextHolder.getLocale()))
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
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
    public ResponseEntity<ErrorResponse> handleLanguageDuplicatonException(LanguageDuplicatonException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.builder()
                                .message(messageSource.getMessage("error.language.duplicate", null, LocaleContextHolder.getLocale()))
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message(messageSource.getMessage("error.common.parameter.invalid", null, LocaleContextHolder.getLocale()))
                        .build());
    }

    @ExceptionHandler(LanguageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLanguageNotFoundException(LanguageNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(messageSource.getMessage("error.language.notfound", null, LocaleContextHolder.getLocale()))
                        .build());
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthorNotFoundException(AuthorNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(messageSource.getMessage("error.author.notfound", null, LocaleContextHolder.getLocale()))
                        .build());
    }

    @ExceptionHandler(CategoryDuplicationException.class)
    public ResponseEntity<ErrorResponse> handleCategoryDuplicationException(CategoryDuplicationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message(messageSource.getMessage("error.category.duplicate", null, LocaleContextHolder.getLocale()))
                        .build());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryDuplicationException(CategoryNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(messageSource.getMessage("error.category.notfound", null, LocaleContextHolder.getLocale()))
                        .build());
    }


    @ExceptionHandler(GenreDuplicationException.class)
    public ResponseEntity<ErrorResponse> handleGenreDuplicationException(GenreDuplicationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message(messageSource.getMessage("error.genre.duplicate", null, LocaleContextHolder.getLocale()))
                        .build());
    }

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGenreNotFoundException(GenreNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(messageSource.getMessage("error.genre.notfound", null, LocaleContextHolder.getLocale()))
                        .build());
    }

    @ExceptionHandler(PublisherDuplicationException.class)
    public ResponseEntity<ErrorResponse> handlePublisherDuplicationException(PublisherDuplicationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message(messageSource.getMessage("error.publisher.duplicate", null, LocaleContextHolder.getLocale()))
                        .build());
    }

    @ExceptionHandler(PublisherNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePublisherNotFoundException(PublisherNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(messageSource.getMessage("error.publisher.notfound", null, LocaleContextHolder.getLocale()))
                        .build());
    }

    @ExceptionHandler(TranslatorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTranslatorNotFoundException(TranslatorNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(messageSource.getMessage("error.translator.notfound", null, LocaleContextHolder.getLocale()))
                        .build());
    }
}
