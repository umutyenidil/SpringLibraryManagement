package com.umutyenidil.librarymanagement.common.handler;

import com.umutyenidil.librarymanagement.author.AuthorNotFoundException;
import com.umutyenidil.librarymanagement.book.BookCopyNotFoundException;
import com.umutyenidil.librarymanagement.common.dto.response.ErrorResponse;
import com.umutyenidil.librarymanagement.common.dto.response.ValidationResponse;
import com.umutyenidil.librarymanagement.common.exception.ResourceDuplicationException;
import com.umutyenidil.librarymanagement.common.exception.ResourceNotFoundException;
import com.umutyenidil.librarymanagement.common.util.MessageUtil;
import com.umutyenidil.librarymanagement.loan.BookCopyNotAvailableException;
import com.umutyenidil.librarymanagement.loan.PatronHasUnpaidPenaltyException;
import com.umutyenidil.librarymanagement.publisher.PublisherDuplicationException;
import com.umutyenidil.librarymanagement.publisher.PublisherNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final MessageUtil messageUtil;

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of(messageUtil.getMessage(("error.common.auth.unauthorized"))));
    }

    @ExceptionHandler(ResourceDuplicationException.class)
    public ResponseEntity<ErrorResponse> handleResourceDuplicationException(ResourceDuplicationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.of(messageUtil.getMessage((ex.getMessageCode()))));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(messageUtil.getMessage((ex.getMessageCode()))));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(messageUtil.getMessage("error.body.missing")));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var errors = new HashMap<String, String>();

        exception.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ValidationResponse.of(errors, messageUtil.getMessage(("error.common.validation"))));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message(messageUtil.getMessage("error.common.parameter.invalid"))
                        .build());
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthorNotFoundException(AuthorNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(messageUtil.getMessage("error.author.notfound"))
                        .build());
    }

    @ExceptionHandler(PublisherDuplicationException.class)
    public ResponseEntity<ErrorResponse> handlePublisherDuplicationException(PublisherDuplicationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message(messageUtil.getMessage("error.publisher.duplicate"))
                        .build());
    }

    @ExceptionHandler(PublisherNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePublisherNotFoundException(PublisherNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(messageUtil.getMessage("error.publisher.notfound"))
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(messageUtil.getMessage("error.common.internalserver")));
    }

    @ExceptionHandler(PatronHasUnpaidPenaltyException.class)
    public ResponseEntity<ErrorResponse> handlePatronHasUnpaidPenaltyException(PatronHasUnpaidPenaltyException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        ErrorResponse.builder()
                                .message("Kullanıcının ödenmemiş cezası bulunuyor")
                                .build()
                );
    }

    @ExceptionHandler(BookCopyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookCopyNotFoundException(BookCopyNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.builder()
                        .message("Kitap örneği bulunamadı")
                        .build());
    }

    @ExceptionHandler(BookCopyNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleBookCopyNotAvailableException(BookCopyNotAvailableException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message("Kitap örneği baskasi tarafindan odunc alinmis")
                        .build());
    }
}
