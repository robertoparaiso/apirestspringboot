package com.backend.exceptions;

import com.backend.entity.dto.DefaultError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handlerExceptionFull(Exception e) {
        DefaultError error = new DefaultError(HttpStatus.EXPECTATION_FAILED.value(), "Erro não tratado.",
                e.toString());
        return new ResponseEntity(error, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity RequestParameterException(Exception e) {
        DefaultError error = new DefaultError(HttpStatus.UNAUTHORIZED.value(), "Parametro obrigatorio.",
                e.toString());

        return new ResponseEntity(error, HttpStatus.UNAUTHORIZED);
    }
}
