package com.enginizer.rest.handler;

import com.enginizer.rest.service.exception.ResourceNotFound;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ResourceNotFound.class)
    public final ResponseEntity<ExceptionResponse> handle404(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse.Builder(ex.getMessage())
                        .withTimestamp(new Date())
                        .withPath(
                                ServletUriComponentsBuilder.fromCurrentRequest().build().toString())
                        .withLocale(request.getLocale()).build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        return ResponseEntity.badRequest()
                .body(new ExceptionResponse.Builder(messageSource
                        .getMessage(allErrors.get(0), request.getLocale()))
                        .withTimestamp(new Date())
                        .withPath(
                                ServletUriComponentsBuilder.fromCurrentRequest().build().toString())
                        .build());
    }
}
