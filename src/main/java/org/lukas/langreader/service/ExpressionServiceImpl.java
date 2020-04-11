package org.lukas.langreader.service;

import org.lukas.langreader.dao.ExpressionRepository;
import org.lukas.langreader.entity.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public List<Expression> findExpressionByVal(String val) {
        return expressionRepository.findExpressionByVal(val);
    }

    @Override
    public boolean expressionExists(Expression expression) {
        return expressionRepository.expressionExists(expression);
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
