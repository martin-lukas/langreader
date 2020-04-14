package org.lukas.langreader.dao;

import org.lukas.langreader.entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressionRepository
        extends JpaRepository<Expression, Long> {

    Expression findByVal(String val);

    boolean existsByVal(String val);
}
