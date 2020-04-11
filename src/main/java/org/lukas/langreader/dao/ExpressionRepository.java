package org.lukas.langreader.dao;

import org.lukas.langreader.entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpressionRepository
        extends JpaRepository<Expression, Long> {
    List<Expression> findAllByOrderByVal();

    Expression findExpressionById(Long id);

    Expression findExpressionByVal(String val);

    boolean existsExpressionByVal(String val);
}
