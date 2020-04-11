package org.lukas.langreader.service;

import org.lukas.langreader.entity.Expression;

public interface ExpressionService {
    Expression findByVal(String val);

    boolean expressionExists(Expression expression);

    void save(Expression expression);

    void deleteById(Long id);

    void delete(Expression expression);
}
