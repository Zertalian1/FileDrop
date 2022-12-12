package ru.ccfit.filedrop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ccfit.filedrop.enumeration.ErrorCode;
import ru.ccfit.filedrop.exception.BasicFileDropException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(BasicFileDropException.class)
    public ResponseEntity<?> handleFileDropException(BasicFileDropException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleError(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

}
