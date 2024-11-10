package com.demo.TaskManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente une erreur.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskError {
    /**
     * Le message d'erreur.
     */
    private String error;
}
