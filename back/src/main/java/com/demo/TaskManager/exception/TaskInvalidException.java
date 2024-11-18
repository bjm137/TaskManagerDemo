package com.demo.TaskManager.exception;

import com.demo.TaskManager.model.TaskError;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskInvalidException extends RuntimeException {

    @Getter
    private TaskError error;

    public TaskInvalidException(String message) {
        super(message);
        error = new TaskError(message);
    }
}
