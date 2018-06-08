package com.feng.accounts.config;

import com.feng.accounts.support.utils.ResourceNotFoundException;
import com.feng.accounts.support.utils.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(HttpServletRequest request, ValidationException ex) {
        String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        return new ErrorResponse(message, request.getServletPath());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException ex) {
        String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        return new ErrorResponse(message, request.getServletPath());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        return new ErrorResponse(
                messageSource.getMessage("error.access.denied", null, LocaleContextHolder.getLocale()),
                request.getServletPath());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(HttpServletRequest request, RuntimeException ex) {
        log.warn("Internal server error:", ex);
        return new ErrorResponse(
                messageSource.getMessage("internal.server.error", null, LocaleContextHolder.getLocale()),
                request.getServletPath());
    }

}
