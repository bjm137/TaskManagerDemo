package com.demo.TaskManager;

import com.demo.TaskManager.exception.TaskInvalidException;
import com.demo.TaskManager.model.Task;
import com.demo.TaskManager.util.ValidationUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void assertIsValid_withValidObject_create_doesNotThrowException() {
        Task task = new Task();
        task.setLabel("Acheter du lait");
        task.setDescription("Acheter du lait à l'épicerie");
        task.setCompleted(false);

        assertDoesNotThrow(() -> ValidationUtils.assertIsValid(task, Task.Create.class));
    }

    @Test
    void assertIsValid_withValidObject_update_doesNotThrowException() {
        var task = new Task();
        task.setId(1L);
        task.setLabel("Acheter du lait");
        task.setDescription("Acheter du lait à l'épicerie");
        task.setCompleted(false);

        assertDoesNotThrow(() -> ValidationUtils.assertIsValid(task, Task.Update.class));
    }

    @Test
    void assertIsValid_withInvalidObject_update_throwsTaskInvalidException() {
        var invalidTask = new Task();
        invalidTask.setLabel("Acheter du lait");
        invalidTask.setDescription("Acheter du lait à l'épicerie");
        invalidTask.setCompleted(false);

        TaskInvalidException exception = assertThrows(TaskInvalidException.class,
            () -> ValidationUtils.assertIsValid(invalidTask, Task.Update.class));

        assertTrue(exception.getMessage().contains("L'identifiant ne doit pas être null"));
    }

    @Test
    void assertIsValid_withInvalidObject_create_throwsTaskInvalidException() {
        var invalidTask = new Task();
        invalidTask.setId(1L);
        invalidTask.setLabel("Acheter du lait");
        invalidTask.setDescription("Acheter du lait à l'épicerie");
        invalidTask.setCompleted(false);

        TaskInvalidException exception = assertThrows(TaskInvalidException.class,
            () -> ValidationUtils.assertIsValid(invalidTask, Task.Create.class));

        assertTrue(exception.getMessage().contains("L'identifiant doit être null"));
    }

    @Test
    void assertIsValid_withInvalidObject_throwsTaskInvalidException() {
        var invalidTask = new Task();

        TaskInvalidException exception = assertThrows(TaskInvalidException.class,
            () -> ValidationUtils.assertIsValid(invalidTask, Task.Update.class, Task.Create.class));

        assertTrue(exception.getMessage().contains("Le libellé de la tâche doit être renseigné"));
        assertTrue(exception.getMessage().contains("La description de la tâche doit être renseignée"));
        assertTrue(exception.getMessage().contains("L'état de la tâche doit être renseigné"));
    }

    @Test
    void assertIsValid_withInvalidObject_throwsTaskInvalidException2() {
        var invalidTask = new Task();
        invalidTask.setId(1L);
        invalidTask.setLabel("A");
        invalidTask.setDescription("B");
        invalidTask.setCompleted(null);

        TaskInvalidException exception = assertThrows(TaskInvalidException.class,
            () -> ValidationUtils.assertIsValid(invalidTask, Task.Update.class));

        assertTrue(exception.getMessage().contains("Le libellé doit être compris entre 3 et 30 caractères"));
        assertTrue(exception.getMessage().contains("La description doit être comprise entre 3 et 100 caractères"));
        assertTrue(exception.getMessage().contains("L'état de la tâche doit être renseigné"));
    }

}