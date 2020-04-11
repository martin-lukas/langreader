package org.lukas.langreader.rest;

import org.lukas.langreader.entity.Expression;
import org.lukas.langreader.exception.DuplicateExpressionException;
import org.lukas.langreader.exception.InvalidExpressionException;
import org.lukas.langreader.service.ExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expressions")
public class ExpressionRestController {
    private final ExpressionService expressionService;

    @Autowired
    public ExpressionRestController(ExpressionService expressionService) {
        this.expressionService = expressionService;
    }

    @PostMapping("/known")
    public void addKnownExpression(@RequestBody Expression newExpression) {
        if (expressionService.expressionExists(newExpression)) {
            throw new DuplicateExpressionException(
                    "Duplicate expression. Expression with value '"  + newExpression.getVal()
                            + "' already exists.");
        } else if (newExpression.getVal().isEmpty()) {
            throw new InvalidExpressionException(
                    "Provided expression '" + newExpression.getVal() + "' is not valid .");
        }

        newExpression.setId(null);
        expressionService.save(newExpression);
    }

    @DeleteMapping("/known")
    public void deleteKnownExpression(@RequestBody Expression expression) {
        Expression foundExpression = expressionService.findByVal(expression.getVal());
        if (foundExpression != null) {
            expressionService.delete(foundExpression);
        }
    }
}
