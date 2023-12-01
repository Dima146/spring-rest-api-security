package com.company.restsecurity.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEntityException(DuplicateEntityException exc) {
        LOGGER.error("Duplicate entity", exc);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.toString());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchEntityException(NoSuchEntityException exc) {
        LOGGER.error("Resource not found", exc);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.toString());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgumentsException(MethodArgumentNotValidException exc) {
        LOGGER.info("Validation on the arguments fails: {}", exc.getMessage());

        List<String> invalidArguments = new ArrayList<>();
        invalidArguments = exc.getBindingResult()
                              .getFieldErrors()
                              .stream()
                              .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                              .collect(Collectors.toList());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setMessage(invalidArguments.toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestParameterException(HttpMessageNotReadableException exc) {
        LOGGER.info("Required request body with parameters is missing: {}", exc.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setMessage("Required request body is missing");
        errorResponse.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exc) {
        LOGGER.warn("Request method not supported: {}", exc.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.METHOD_NOT_ALLOWED.toString());
        errorResponse.setMessage(exc.getMethod() +  " method is not supported for this url. Supported methods are " +
                                        exc.getSupportedHttpMethods());
        errorResponse.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exc) {
        LOGGER.info("Authentication request is rejected: {}", exc.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.toString());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException exc) {
        LOGGER.warn("No handler found for {} + {}", exc.getHttpMethod(), exc.getRequestURL());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.toString());
        errorResponse.setMessage("No mapping for " + exc.getHttpMethod() + " " + exc.getRequestURL());
        errorResponse.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exc) {
        LOGGER.info("Method argument is not the expected type: {}", exc.getRequiredType());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setMessage(exc.getName() + " should be of type " +
                Objects.requireNonNull(exc.getRequiredType()).getSimpleName());
        errorResponse.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exc) {
        LOGGER.error("Error occurred: ", exc);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorResponse.setMessage("The server encountered an error and could not complete your request");
        errorResponse.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}