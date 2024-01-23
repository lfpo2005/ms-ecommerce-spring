package dev.luisoliveira.esquadrias.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.luisoliveira.esquadrias.exceptions.helper.GenericErrorsEnum;
import dev.luisoliveira.esquadrias.exceptions.helper.MessagesEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

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

    @ExceptionHandler(Exception.class)
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
}
