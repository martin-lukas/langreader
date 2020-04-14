package org.lukas.langreader.service;

import org.lukas.langreader.entity.Expression;

import java.util.List;

public interface ExpressionService {
    List<Expression> findAll();

    Expression findExpressionById(Long id);

    Expression findExpressionByVal(String val);

    boolean existsByVal(String val);

    void save(Expression expression);

    void delete(Expression expression);
}
