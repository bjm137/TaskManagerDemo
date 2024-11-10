package com.demo.TaskManager.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente une tâche.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Comparable<Task> {

    /**
     * L'identifiant unique de la tâche.
     */
    @Null(groups = Create.class, message = "L'identifiant doit être null")
    @NotNull(groups = Update.class, message = "L'identifiant ne doit pas être null")
    private Long id;

    /**
     * Le libellé de la tâche.
     */
    @NotNull(message = "Le libellé de la tâche doit être renseigné")
    @Size(min = 3, max = 30, groups = {Create.class, Update.class},
        message = "Le libellé doit être compris entre 3 et 30 caractères")
    protected String label;

    /**
     * La description de la tâche.
     */
    @NotNull(message = "La description de la tâche doit être renseignée")
    @Size(min = 3, max = 100, groups = {Create.class, Update.class},
        message = "La description doit être comprise entre 3 et 100 caractères")
    protected String description;

    /**
     * L'état de la tâche.
     */
    @NotNull(message = "L'état de la tâche doit être renseigné")
    protected Boolean completed;

    @Override
    public int compareTo(Task other) {
        return this.id.compareTo(other.id);
    }

    /**
     * Groupe de validation pour la création d'une tâche.
     */
    public interface Create extends Default {
    }

    /**
     * Groupe de validation pour la mise à jour d'une tâche.
     */
    public interface Update extends Default {
    }
}
