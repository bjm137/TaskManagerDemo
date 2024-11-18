package com.demo.TaskManager.exception;

import com.demo.TaskManager.model.TaskError;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {

    @Getter
    private TaskError error;

    public TaskNotFoundException(Long id) {
        super("Impossible de trouver la tâche avec l'id : " + id);
        error = new TaskError("Impossible de trouver la tâche avec l'id : " + id);
    }
}
