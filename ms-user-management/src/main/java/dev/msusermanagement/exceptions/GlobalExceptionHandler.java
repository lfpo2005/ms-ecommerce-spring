package dev.msusermanagement.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.msusermanagement.exceptions.helper.GenericErrorsEnum;
import dev.msusermanagement.exceptions.helper.MessagesEnum;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    ObjectMapper om;

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> noHandlerFoundException(NoHandlerFoundException e) {
        log.error(e.getClass().getSimpleName(), e);

        var errorReason = "Caminho '" + e.getRequestURL() + "' não encontrado para método '" + e.getHttpMethod() + "'";

        var response = new Error(
                MessagesEnum.HTTP_404_NOT_FOUND.getCode(),
                e.getMessage(),
                errorReason,
                MessagesEnum.HTTP_404_NOT_FOUND.getDescription()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Error> missingRequestHeaderException(MissingRequestHeaderException e) {
        log.error(e.getClass().getSimpleName(), e);

        var errorReason = "Request Header '" + e.getHeaderName() + "' não foi encontrado.";

        var response = new Error(
                MessagesEnum.HTTP_400_BAD_REQUEST.getCode(),
                errorReason,
                e.getMessage(),
                MessagesEnum.HTTP_400_BAD_REQUEST.getDescription()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Error> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getClass().getSimpleName(), e);

        var errorReason = "O Método '" + e.getMethod() + "' não é permitido aqui.";

        var response = new Error(
                GenericErrorsEnum.METHOD_NOT_ALLOWED.getCode(),
                errorReason,
                e.getMessage(),
                GenericErrorsEnum.METHOD_NOT_ALLOWED.getDescription()
        );

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @SuppressWarnings("java:S2259")
    public ResponseEntity<Error> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getClass().getSimpleName(), e);

        var response = new Error(
                GenericErrorsEnum.BAD_REQUEST.getCode(),
                GenericErrorsEnum.BAD_REQUEST.getReason(),
                (e.getBindingResult().getFieldError() != null)
                        ? e.getBindingResult().getFieldError().getDefaultMessage()
                        : e.getMessage(),
                GenericErrorsEnum.BAD_REQUEST.getDescription()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    public ResponseEntity<Error> standardException(Exception e) {
        log.error(e.getClass().getSimpleName(), e);
        var response = new Error(
                GenericErrorsEnum.ERROR_GENERIC.getCode(),
                GenericErrorsEnum.ERROR_GENERIC.getReason(),
                e.getMessage(),
                GenericErrorsEnum.ERROR_GENERIC.getDescription()
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {DataIntegrityViolationException.class,
            ConstraintViolationException.class, SQLException.class, TransactionSystemException.class, JpaSystemException.class})
    public ResponseEntity<Object> handleExceptionDataIntegrate(Exception ex) {
        log.error(ex.getClass().getSimpleName(), ex);
        var response = new Error(
                GenericErrorsEnum.BAD_REQUEST.getCode(),
                GenericErrorsEnum.BAD_REQUEST.getReason(),
                ex.getMessage(),
                GenericErrorsEnum.BAD_REQUEST.getDescription()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> NotFoundException(Exception ex) {
        log.error(ex.getClass().getSimpleName(), ex);
        var response = new Error(
                GenericErrorsEnum.NOT_FOUND.getCode(),
                GenericErrorsEnum.NOT_FOUND.getReason(),
                ex.getMessage(),
                GenericErrorsEnum.NOT_FOUND.getDescription()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<?> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex) {
        log.error(ex.getClass().getSimpleName(), ex);

        var message = "Username is Already Taken!";
        var response = new Error(
                GenericErrorsEnum.CONFLICT.getCode(),
                GenericErrorsEnum.CONFLICT.getReason(),
                message,
                GenericErrorsEnum.CONFLICT.getDescription()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<?> handleEmailAlreadyTakenException(EmailAlreadyTakenException ex) {
        log.error(ex.getClass().getSimpleName(), ex);

        var message = "Email is Already Taken!";
        var response = new Error(
                GenericErrorsEnum.CONFLICT.getCode(),
                GenericErrorsEnum.CONFLICT.getReason(),
                message,
                GenericErrorsEnum.CONFLICT.getDescription()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(FullNameAlreadyTakenException.class)
    public ResponseEntity<?> handleFullNameAlreadyTakenException(FullNameAlreadyTakenException ex) {
        log.error(ex.getClass().getSimpleName(), ex);

        var message = "FullName is Already Taken!";
        var response = new Error(
                GenericErrorsEnum.CONFLICT.getCode(),
                GenericErrorsEnum.CONFLICT.getReason(),
                message,
                GenericErrorsEnum.CONFLICT.getDescription()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(InvalidBirthDateFormatException.class)
    public ResponseEntity<?> handleInvalidBirthDateFormatException(InvalidBirthDateFormatException ex) {
        log.error(ex.getClass().getSimpleName(), ex);

        var message = "Invalid BirthDate format! Valid format: dd-MM-yyyy";
        var response = new Error(
                GenericErrorsEnum.CONFLICT.getCode(),
                GenericErrorsEnum.CONFLICT.getReason(),
                message,
                GenericErrorsEnum.CONFLICT.getDescription()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(CpfAlreadyTakenException.class)
    public ResponseEntity<?> handleCpfAlreadyTakenException(CpfAlreadyTakenException ex) {
        log.error(ex.getClass().getSimpleName(), ex);

        var message = "Cpf is duplicated";
        var response = new Error(
                GenericErrorsEnum.CONFLICT.getCode(),
                GenericErrorsEnum.CONFLICT.getReason(),
                message,
                GenericErrorsEnum.CONFLICT.getDescription()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}

