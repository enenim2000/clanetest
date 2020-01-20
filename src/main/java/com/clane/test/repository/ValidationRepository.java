package com.clane.test.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.MessageFormat;

@Repository
@Slf4j
public class ValidationRepository {

    private final EntityManager entityManager;

    @Autowired
    public ValidationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean IsValidId(String tableName, long id) {
        String sqlQuery = MessageFormat.format("select e from {0} e where e.id = {1}", tableName, id);
        log.info(sqlQuery);
        return entityManager.createQuery(sqlQuery).getResultList().isEmpty();
    }

    public boolean isUnique(String tableName, String columnName, Object value) {
        String sqlQuery = MessageFormat.format("select e from  {0} e where LOWER(e.{1}) = LOWER(:value)", tableName, columnName);
        log.info(sqlQuery);
        
        return entityManager.createQuery(sqlQuery)
                .setParameter("value", value)
                .getResultList()
                .isEmpty();
    }
}