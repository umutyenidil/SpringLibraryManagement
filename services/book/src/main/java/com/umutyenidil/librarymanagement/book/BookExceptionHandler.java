package com.umutyenidil.librarymanagement.book;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class BookExceptionHandler {

    private final MessageSource messageSource;

//    @ExceptionHandler(BookNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException ex) {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(
//                        ErrorResponse
//                                .message(messageSource.getMessage(ex.getMessageCode(), null, LocaleContextHolder.getLocale()))
//                                .build()
//                );
//    }
//
//    @ExceptionHandler(BookAuthorRequiredException.class)
//    public ResponseEntity<ErrorResponse> handleAuthorRequiredException(BookAuthorRequiredException ex) {
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(
//                        ErrorResponse.builder()
//                                .message(messageSource.getMessage(ex.getMessageCode(), null, LocaleContextHolder.getLocale()))
//                                .build()
//                );
//    }
//
//    @ExceptionHandler(BookTranslatorNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleBookTranslatorNotFoundException(BookTranslatorNotFoundException ex) {
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(
//                        ErrorResponse.builder()
//                                .message(messageSource.getMessage(ex.getMessageCode(), null, LocaleContextHolder.getLocale()))
//                                .build()
//                );
//    }
//
//    @ExceptionHandler(BookTranslatorRequiredException.class)
//    public ResponseEntity<ErrorResponse> handleBookTranslatorRequiredException(BookTranslatorRequiredException ex) {
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(
//                        ErrorResponse.builder()
//                                .message(messageSource.getMessage(ex.getMessageCode(), null, LocaleContextHolder.getLocale()))
//                                .build()
//                );
//    }
}
