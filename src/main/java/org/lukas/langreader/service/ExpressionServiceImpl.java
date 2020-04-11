package org.lukas.langreader.service;

import org.lukas.langreader.dao.ExpressionRepository;
import org.lukas.langreader.entity.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpressionServiceImpl implements ExpressionService {
    private final ExpressionRepository expressionRepository;

    @Autowired
    public ExpressionServiceImpl(ExpressionRepository expressionRepository) {
        this.expressionRepository = expressionRepository;
    }

    @Override
    public List<Expression> findAll() {
        return expressionRepository.findAllByOrderByVal();
    }

    @Override
    public Expression findExpressionById(Long id) {
        return expressionRepository.findExpressionById(id);
    }

    @Override
    public Expression findExpressionByVal(String val) {
        return expressionRepository.findExpressionByVal(val);
    }

    @Override
    public boolean existsExpressionByVal(String val) {
        return expressionRepository.existsExpressionByVal(val);
    }

    @Override
    public void save(Expression expression) {
        expressionRepository.save(expression);
    }

    @Override
    public void delete(Expression expression) {
        expressionRepository.delete(expression);
    }
}
