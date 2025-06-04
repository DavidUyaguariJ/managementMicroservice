package com.udla.management.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ManagementExceptionHandler {

    @ExceptionHandler(ManagementException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(final ManagementException ex, final WebRequest request) {
        return handleException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(final Exception ex, final WebRequest request) {
        HttpStatus status = determineHttpStatus(ex);
        return handleException(ex, request, status);
    }

    private ResponseEntity<Map<String, Object>> handleException(final Exception ex, final WebRequest request, final HttpStatus  status) {
        logRequestAndException(request, ex);
        Map<String, Object> responseBody = buildResponseBody(ex, status);
        return new ResponseEntity<>(responseBody, status);
    }

    private void logRequestAndException(final WebRequest request, final Exception ex) {
        log.warn(request.getDescription(Boolean.TRUE));
        log.error(ex.getMessage());
    }

    private Map<String, Object> buildResponseBody(final Exception ex, final HttpStatus status) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("time", LocalDateTime.now());
        responseBody.put("message", determineMessage(ex));
        responseBody.put("status", status.value());
        return responseBody;
    }

    private HttpStatus determineHttpStatus(final Exception ex) {
        if (ex instanceof ManagementException || ex instanceof NoResourceFoundException || ex instanceof HttpRequestMethodNotSupportedException) {
            return HttpStatus.NOT_FOUND;
        } else if (ex instanceof ConstraintViolationException || ex instanceof MethodArgumentNotValidException
            || ex instanceof HttpMessageNotReadableException) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private String determineMessage(final Exception ex) {
        if (ex instanceof ManagementException || ex instanceof NoResourceFoundException || ex instanceof HttpRequestMethodNotSupportedException) {
            return ex.getMessage();
        } else {
            return "Existe un error inesperado, si persiste contacte a soporte";
        }
    }
}
