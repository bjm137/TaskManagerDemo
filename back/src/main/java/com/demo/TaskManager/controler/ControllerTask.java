package com.demo.TaskManager.controler;

import com.demo.TaskManager.model.Task;
import com.demo.TaskManager.model.TaskSearch;
import com.demo.TaskManager.service.TaskService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
@Validated
public class ControllerTask {

    /**
     * Service de gestion des tâches.
     */
    private final TaskService taskService;

    /**
     * Récupérer la liste des tâches.
     *
     * @return la liste des tâches.
     */
    @GetMapping
    public List<Task> getAllTasks() {
        log.info("Demande de la liste des tâches");
        return taskService.getAllTasks();
    }

    /**
     * Récupérer la liste des tâches en fonction des critères de recherche.
     *
     * @param search les critères de recherche.
     * @return la liste des tâches.
     */
    @PostMapping("/search")
    public List<Task> getSearch(@RequestBody TaskSearch search) {
        log.info("Recherche de tâches via : {}", search);
        return taskService.getAllTasks(search);
    }

    /**
     * Récupérer une tâche via son id.
     *
     * @param id l'identifiant de la tâche à récupérer.
     * @return la tâche récupérée.
     */
    @GetMapping("/{id}")
    public Task getTask(@PathVariable("id") Long id) {
        log.info("Recherche d'une tâche via l'id : {}", id);
        return taskService.getTask(id);
    }

    /**
     * Mettre à jour une tâche.
     *
     * @param task la tâche à mettre à jour.
     * @return la tâche mise à jour.
     */
    @PutMapping
    public Task updateTask(@RequestBody @NotNull Task task) {
        log.info("Mise à jour de la tâche : {}", task);
        return taskService.updateTask(task);
    }

    /**
     * Créer une nouvelle tâche.
     *
     * @param task la tâche à créer.
     * @return la tâche nouvellement créée.
     */
    @PostMapping("/create")
    public Task createTask(@RequestBody @NotNull Task task) {
        log.info("Création de la tâche : {}", task);
        return taskService.createTask(task);
    }

    /**
     * Supprimer une tâche via son id.
     *
     * @param id l'identifiant de la tâche à supprimer.
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        log.info("Suppression de la tâche avec l'id : {}", id);
        taskService.deleteTask(id);
    }

}
