package com.demo.TaskManager;

import com.demo.TaskManager.controler.ControllerTask;
import com.demo.TaskManager.exception.TaskInvalidException;
import com.demo.TaskManager.exception.TaskNotFoundException;
import com.demo.TaskManager.model.Task;
import com.demo.TaskManager.model.TaskSearch;
import com.demo.TaskManager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ControllerTask.class)
public class ControllerTaskTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private Task task;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        task = new Task();
        task.setId(1L);
        task.setLabel("Acheter du lait");
        task.setDescription("Acheter du lait à l'épicerie");
        task.setCompleted(false);
    }

    @Test
    public void testGetAllTasks() throws Exception {
        var tasks = List.of(task);

        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].label").value("Acheter du lait"));

        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    public void testGetSearch() throws Exception {
        var tasks = List.of(task);
        TaskSearch taskSearch = new TaskSearch();

        when(taskService.getAllTasks(any(TaskSearch.class))).thenReturn(tasks);

        mockMvc.perform(post("/tasks/search")
                .content(objectMapper.writeValueAsString(taskSearch))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].label").value("Acheter du lait"));

        verify(taskService, times(1)).getAllTasks(any(TaskSearch.class));
    }

    @Test
    public void testGetTask() throws Exception {
        when(taskService.getTask(task.getId())).thenReturn(task);

        mockMvc.perform(get("/tasks/{id}", task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.label").value("Acheter du lait"));

        verify(taskService, times(1)).getTask(task.getId());
    }

    @Test
    public void testUpdateTask() throws Exception {
        when(taskService.updateTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/tasks")
                .content(objectMapper.writeValueAsString(task))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.label").value("Acheter du lait"));

        verify(taskService, times(1)).updateTask(any(Task.class));
    }

    @Test
    public void testCreateTask() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/tasks/create")
                .content(objectMapper.writeValueAsString(task))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.label").value("Acheter du lait"));

        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(task.getId());

        mockMvc.perform(delete("/tasks/{id}", task.getId()))
            .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(task.getId());
    }

    @Test
    public void testCreateTask_InvalidInputs() throws Exception {
        when(taskService.createTask(any(Task.class))).thenThrow(
            new TaskInvalidException("Le libellé doit être compris entre 3 et 30 caractères"));
        Task invalidTask = new Task();
        invalidTask.setLabel("T"); // Invalid label, too short.

        mockMvc.perform(post("/tasks/create")
                .content(objectMapper.writeValueAsString(invalidTask))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Le libellé doit être compris entre 3 et 30 caractères"));
    }

    @Test
    public void testGetTask_NotFound() throws Exception {
        when(taskService.getTask(anyLong())).thenThrow(new TaskNotFoundException(99L));

        mockMvc.perform(get("/tasks/{id}", 99L))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("Impossible de trouver la tâche avec l'id : 99"));
    }

    @Test
    public void testUpdateTask_NotFound() throws Exception {
        when(taskService.updateTask(any(Task.class))).thenThrow(new TaskNotFoundException(17L));

        mockMvc.perform(put("/tasks")
                .content(objectMapper.writeValueAsString(task))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("Impossible de trouver la tâche avec l'id : 17"));
    }

    @Test
    public void testDeleteTask_NotFound() throws Exception {
        doThrow(new TaskNotFoundException(17L)).when(taskService).deleteTask(anyLong());

        mockMvc.perform(delete("/tasks/{id}", 17L))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("Impossible de trouver la tâche avec l'id : 17"));
    }

}
