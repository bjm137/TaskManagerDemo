package com.demo.TaskManager;

import com.demo.TaskManager.model.Task;
import com.demo.TaskManager.model.TaskSearch;
import com.demo.TaskManager.repository.TaskRepository;
import com.demo.TaskManager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    private List<Task> tasks;

    @BeforeEach
    public void setUp() {
        tasks = List.of(
            new Task(1L, "Acheter du lait", "Acheter du lait à l'épicerie", false),
            new Task(2L, "Acheter du pain", "Acheter du pain à la boulangerie", false),
            new Task(3L, "Acheter du beurre", "Acheter du beurre à la crémerie", true)
        );
    }

    @Test
    public void test_CreateTask() {
        var task = new Task(4L, "Faire du sport", "Faire du sport à la salle de sport", false);
        when(taskRepository.create(any(Task.class))).thenReturn(task);
        task.setId(null);

        // Act
        Task createdTask = taskService.createTask(task);

        // Assert
        assertNotNull(createdTask);
        assertEquals("Faire du sport", createdTask.getLabel());
        verify(taskRepository, times(1)).create(any(Task.class));
    }

    @Test
    public void test_UpdateTask() {
        var task = tasks.get(0);
        task.setLabel("Acheter du lait frais");
        // Arrange
        when(taskRepository.update(any(Task.class))).thenReturn(task);

        // Act
        Task updatedTask = taskService.updateTask(task);

        // Assert
        assertNotNull(updatedTask);
        assertEquals("Acheter du lait frais", updatedTask.getLabel());
        verify(taskRepository, times(1)).update(any(Task.class));
    }

    @Test
    public void test_DeleteTask() {
        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    public void test_GetTask() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(tasks.get(0));

        // Act
        Task foundTask = taskService.getTask(1L);

        // Assert
        assertNotNull(foundTask);
        assertEquals("Acheter du lait", foundTask.getLabel());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void test_GetAllTasks() {
        // Arrange
        when(taskRepository.findAll()).thenReturn(tasks);

        // Act
        List<Task> tasks = taskService.getAllTasks();

        // Assert
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        assertEquals(3, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void test_GetAllTasksWithCriteria() {
        // Arrange
        var taskSearch = new TaskSearch();
        taskSearch.setLabel("Acheter du lait");
        when(taskRepository.findAllBy(taskSearch)).thenReturn(tasks.subList(0, 1));

        // Act
        List<Task> tasks = taskService.getAllTasks(taskSearch);

        // Assert
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findAllBy(taskSearch);
    }
}
