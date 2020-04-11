package org.lukas.langreader.dao;

import org.lukas.langreader.entity.Expression;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ExpressionRepositoryCustomImpl implements ExpressionRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public ExpressionRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean expressionExists(Expression expression) {
        int resultSize = entityManager
                .createQuery("from Expression e where e.val = :expressionVal")
                .setParameter("expressionVal", expression.getVal())
                .getResultList()
                .size();
        return resultSize > 0;
    }

    @Override
    public Expression findByVal(String val) {
        Query query = entityManager.createQuery("from Expression e where e.val = :value")
                .setParameter("value", val);
        List<Expression> expressions = query.getResultList();
        if (expressions.size() > 1) {
            return null;
        } else {
            return expressions.get(0);
        }
    }
}
