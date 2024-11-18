package com.demo.TaskManager.service;

import com.demo.TaskManager.model.Task;
import com.demo.TaskManager.model.TaskSearch;
import com.demo.TaskManager.repository.TaskRepository;
import com.demo.TaskManager.util.ValidationUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.demo.TaskManager.util.ValidationUtils.assertIsValid;

/**
 *  Service de gestion des tâches.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    /**
     * Bean d'intéraction avec la source de données.
     */
    private final TaskRepository taskRepository;

    /**
     * Crée une nouvelle tâche.
     * @param task La tâche à créer.
     * @return La tâche nouvellement créée.
     */
    public Task createTask(@NotNull Task task) {
        log.debug("Création d'une tâche {}", task);
        assertIsValid(task, Task.Create.class);
        return taskRepository.create(task);
    }

    /**
     * Gère la mise à jour d'une tâche.
     * @param task La tâche à mettre à jour.
     * @return La tâche mise à jour.
     */
    public Task updateTask(@NotNull Task task) {
        log.debug("Mise à jour d'une tâche {}", task);
        assertIsValid(task, Task.Update.class);
        return taskRepository.update(task);
    }

    /**
     * Gère la suppression d'une tâche à partir de son id.
     * @param id Identifiant de la tâche à supprimer.
     */
    public void deleteTask(Long id) {
        log.debug("Suppression de la tâche avec l'id : {}", id);
        taskRepository.deleteById(id);
    }

    /**
     * Gère la récupération d'une tâche à partir de son id.
     * @param id identifiant de la tâche à récupérer.
     * @return La tâche récupérée si elle existe sinon null.
     */
    public Task getTask(Long id) {
        log.debug("Tâche recherchée via l'id : {}", id);
        return taskRepository.findById(id);
    }

    /**
     * Permet de récupérer l'ensemble des tâches.
     * @return La liste des toutes les tâches.
     */
    public List<Task> getAllTasks() {
        log.debug("Récuperation de la liste des tâches");
        return taskRepository.findAll();
    }

    /**
     * Permet de récupérer une liste de tâches suivant different critères de recherche.
     * @param taskSearch Les critères de recherches et de pagination.
     * @return Liste des tâches après application des filtres.
     */
    public List<Task> getAllTasks(TaskSearch taskSearch) {
        log.debug("Récupération de tâches par critères {}", taskSearch);
        return taskRepository.findAllBy(taskSearch);
    }

}
