package com.demo.TaskManager.repository;

import com.demo.TaskManager.exception.TaskInvalidException;
import com.demo.TaskManager.exception.TaskNotFoundException;
import com.demo.TaskManager.model.Task;
import com.demo.TaskManager.model.TaskSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static java.lang.Math.min;

@Slf4j
@Repository("TaskRepository")
public class TaskRepositoryImpl implements TaskRepository {

    List<Task> tasks;

    public TaskRepositoryImpl() {
        try {
            tasks = loadTasks();
        }
        catch (IOException e) {
            log.error("Erreur lors du chargement des tâches", e);
            tasks = new ArrayList<>();
        }
    }

    @Override
    public Task create(@NotNull(message = "La tâche est null") Task task) {
        if (tasks.contains(task)) {
            throw new TaskInvalidException("La tâche existe déjà");
        }

        task.setId((long) tasks.size() + 1);
        tasks.add(task);
        return task;
    }

    @Override
    public Task update(@NotNull(message = "La tâche est null") Task task) {
        if (tasks.stream().noneMatch(task1 -> task1.getId().equals(task.getId()))) {
            log.error("La tâche n'existe pas");
            throw new TaskNotFoundException(task.getId());
        }

        tasks.set(task.getId().intValue() - 1, task);
        return task;

    }

    @Override
    public void deleteById(Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

    @Override
    public Task findById(Long id) {
        var task = tasks.stream()
            .filter(t -> t.getId().equals(id))
            .findFirst();
        return task.orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public List<Task> findAllBy(TaskSearch taskSearch) {
        if (taskSearch == null) {
            return null;
        }

        var filteredTask = new TreeSet<>(tasks).stream()
            .filter(task -> taskSearch.getLabel() == null || task.getLabel().contains(taskSearch.getLabel()))
            .filter(task -> taskSearch.getDescription() == null || task.getDescription().contains(taskSearch.getDescription()))
            .filter(task -> taskSearch.getCompleted() == null || task.getCompleted().equals(taskSearch.getCompleted()))
            .toList();

        if (taskSearch.getOffset() >= 0 && taskSearch.getLimit() > 0) {
            int fromIndex = taskSearch.getOffset() * taskSearch.getLimit();
            if (fromIndex >= filteredTask.size()) {
                log.error("Index de départ invalide");
                throw new TaskInvalidException("Index de départ invalide : " + taskSearch);
            }
            int toIndex = min(fromIndex + taskSearch.getLimit(), filteredTask.size());
            return filteredTask.subList(fromIndex, toIndex);
        }

        return filteredTask;
    }

    /**
     * Charge les tâches à partir d'un fichier JSON.
     *
     * @return La liste des tâches.
     * @throws IOException En cas d'erreur lors de la lecture du fichier.
     */
    private List<Task> loadTasks() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonData = Files.readAllBytes(Paths.get("src/main/resources/static/samples-tasks.json"));
        return objectMapper.readValue(jsonData, new TypeReference<List<Task>>() {
        });
    }
}
