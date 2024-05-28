package com.mediNet.mediNet.exception;

import com.mediNet.mediNet.dto.MediException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handlePatientNotFoundException(
            NotFoundException notFoundException) {
        log.error("Details not found exception occurred:", notFoundException);
        MediException MediException = new MediException(
                notFoundException.getMessage(),
                notFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(MediException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<Object> handlePatientValidationException(
            ValidationException validationException) {
        log.error("Details validation exception occurred:", validationException);
        MediException MediException = new MediException(
                validationException.getMessage(),
                validationException.getCause(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(MediException, HttpStatus.BAD_REQUEST);
    }
}
