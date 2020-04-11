package org.lukas.langreader.dao;

import org.lukas.langreader.entity.Expression;

public interface ExpressionRepositoryCustom {
    boolean expressionExists(Expression expression);

    Expression findByVal(String val);
}
