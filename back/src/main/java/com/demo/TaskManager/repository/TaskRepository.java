package com.demo.TaskManager.repository;

import com.demo.TaskManager.model.Task;
import com.demo.TaskManager.model.TaskSearch;

import java.util.List;

public interface TaskRepository {
    /**
     * Créer une tâche dans la source de données.
     * @param task La tâche à créer.
     * @return La tâche créée.
     */
    Task create(Task task);

    /**
     * Mettre à jour une tâche dans la source de données.
     * @param task La tâche à mettre à jour.
     * @return La tâche mise à jour.
     */
    Task update(Task task);

    /**
     * Supprimer une tâche de la source de données.
     * @param id L'identifiant de la tâche à supprimer.
     */
    void deleteById(Long id);

    /**
     * Trouver une tâche par son identifiant.
     * @param id L'identifiant de la tâche à trouver.
     * @return La tâche trouvée.
     */
    Task findById(Long id);

    /**
     * Trouver toutes les tâches.
     * @return La liste de toutes les tâches.
     */
    List<Task> findAll();

    /**
     * Trouver toutes les tâches correspondant à une recherche.
     * @param taskSearch La recherche à effectuer.
     * @return La liste des tâches correspondant à la recherche.
     */
    List<Task> findAllBy(TaskSearch taskSearch);
}
