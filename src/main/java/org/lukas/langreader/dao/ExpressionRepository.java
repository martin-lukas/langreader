package org.lukas.langreader.dao;

import org.lukas.langreader.entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpressionRepository
        extends JpaRepository<Expression, Long>, ExpressionRepositoryCustom {
    List<Expression> findAllByOrderByVal();

    List<Expression> findExpressionByVal(String val);
}
