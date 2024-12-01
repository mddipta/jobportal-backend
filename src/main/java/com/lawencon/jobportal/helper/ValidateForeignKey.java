package com.lawencon.jobportal.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;

@Component
public class ValidateForeignKey {
    private final EntityManager entityManager;

    @Autowired
    public ValidateForeignKey(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> boolean isParentIdReferenced(Object parentId, Class<T> childClass,
            String foreignKeyName) {

        if (entityManager == null) {
            throw new IllegalStateException("EntityManager has not been properly initialized");
        }

        if (parentId == null || childClass == null || foreignKeyName == null
                || foreignKeyName.isEmpty()) {
            throw new IllegalArgumentException("Invalid arguments provided");
        }

        String query = String.format("SELECT COUNT(c) FROM %s c WHERE c.%s = :parentId",
                childClass.getSimpleName(), foreignKeyName);

        Long count = entityManager.createQuery(query, Long.class).setParameter("parentId", parentId)
                .getSingleResult();

        return count > 0;
    }
}
