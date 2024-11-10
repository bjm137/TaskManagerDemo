package com.demo.TaskManager.exception;

import com.demo.TaskManager.model.TaskError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Gére les réponses aux exceptions.
 */
@RestControllerAdvice
public class ExceptionsHandler {

    /**
     * Gére les exceptions de type TaskInvalidException.
     *
     * @param ex l'exception à gérer.
     * @return la réponse à renvoyer.
     */
    @ExceptionHandler(TaskInvalidException.class)
    public ResponseEntity<TaskError> handleTaskInvalidException(TaskInvalidException ex) {
        return new ResponseEntity<>(ex.getError(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Gére les exceptions de type TaskNotFoundException.
     *
     * @param ex l'exception à gérer.
     * @return la réponse à renvoyer.
     */
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<TaskError> handleTaskNotFoundException(TaskNotFoundException ex) {
        return new ResponseEntity<>(ex.getError(), HttpStatus.NOT_FOUND);
    }

    /**
     * Gére les exceptions de type MethodArgumentNotValidException.
     *
     * @param ex l'exception à gérer.
     * @return la réponse à renvoyer.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
