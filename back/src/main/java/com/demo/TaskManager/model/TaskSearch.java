package com.demo.TaskManager.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Représente un objet de recherche de tâches.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TaskSearch extends Task {

    /**
     * L'offset de la recherche (Page).
     */
    private int offset;

    /**
     * Le nombre de tâches à récupérer par appel.
     */
    private int limit;
}
