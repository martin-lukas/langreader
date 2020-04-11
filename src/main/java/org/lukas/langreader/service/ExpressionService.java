package org.lukas.langreader.service;

import org.lukas.langreader.entity.Expression;

import java.util.List;

public interface ExpressionService {
    List<Expression> findAll();

    List<Expression> findExpressionByVal(String val);

    boolean expressionExists(Expression expression);

    void save(Expression expression);

    void delete(Expression expression);
}
