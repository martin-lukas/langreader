package org.lukas.langreader.service;

import org.lukas.langreader.dao.ExpressionRepository;
import org.lukas.langreader.entity.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpressionServiceImpl implements ExpressionService {
    private final ExpressionRepository expressionRepository;

    @Autowired
    public ExpressionServiceImpl(ExpressionRepository expressionRepository) {
        this.expressionRepository = expressionRepository;
    }


    @Override
    public Expression findByVal(String val) {
        return expressionRepository.findByVal(val);
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
    public void deleteById(Long id) {
        expressionRepository.deleteById(id);
    }

    @Override
    public void delete(Expression expression) {
        expressionRepository.delete(expression);
    }
}
