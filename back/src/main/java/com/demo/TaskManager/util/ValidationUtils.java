package com.demo.TaskManager.util;

import com.demo.TaskManager.exception.TaskInvalidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Arrays;
import java.util.Set;


public class ValidationUtils {
    /**
     * Le validateur.
     */
    private static final Validator validator;

    /**
     * Initialisation du validateur.
     */
    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    /**
     * Constructeur privé pour empêcher l'instanciation de la classe.
     */
    private ValidationUtils() {
    }

    /**
     * Vérifie que l'objet est valide.
     * @param obj l'objet à valider.
     * @param groups les groupes de validation.
     * @param <T> le type de l'objet à valider.
     * @throws TaskInvalidException si l'objet n'est pas valide.
     */
    public static <T> void assertIsValid(T obj, Class<?>... groups) throws TaskInvalidException {
        Set<ConstraintViolation<T>> violations = validate(obj, groups);

        if (!violations.isEmpty()) {
            throw new TaskInvalidException(
                Arrays.toString(violations.stream()
                    .map(ConstraintViolation::getMessage).toArray()));
        }
    }

    /**
     * Valide les contraintes de l'objet.
     * @param obj l'objet à valider.
     * @param groups les groupes de validation.
     * @return les contraintes violées ou une liste vide si l'objet est valide.
     * @param <T> le type de l'objet à valider.
     */
    private static <T> Set<ConstraintViolation<T>> validate(final T obj, final Class<?>... groups) {
        return validator.validate(obj, groups);
    }

}
