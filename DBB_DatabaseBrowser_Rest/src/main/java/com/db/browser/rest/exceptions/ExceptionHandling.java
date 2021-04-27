package com.db.browser.rest.exceptions;

import com.db.browser.spi.exceptions.DatabaseConnectionDetailsNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandling {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandling.class);

    /**
     * Handle exception for not valid method arguments.
     * @param ex the {@link MethodArgumentNotValidException} exception
     * @return List with mapped non valid fields.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Handle exception for database not found exceptions.
     * @param ex the {@link RuntimeException} exception
     * @return Response http status.
     */
    @ExceptionHandler(DatabaseConnectionDetailsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleDatabaseConnectionNotFound(DatabaseConnectionDetailsNotFoundException ex) {

        log.error(ex.getMessage(), ex.getCause());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle data integrity conflicts.
     * @param ex the {@link DataIntegrityViolationException} exception
     * @return Response http status.
     */
    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleConflict(DataIntegrityViolationException ex) {
        log.error(ex.getMessage(), ex.getCause());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
