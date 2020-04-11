package org.lukas.langreader.rest;

import org.lukas.langreader.entity.Expression;
import org.lukas.langreader.exception.DuplicateExpressionException;
import org.lukas.langreader.exception.InvalidExpressionException;
import org.lukas.langreader.service.ExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expressions")
public class ExpressionRestController {
    private final ExpressionService expressionService;

    @Autowired
    public ExpressionRestController(ExpressionService expressionService) {
        this.expressionService = expressionService;
    }

    @GetMapping("/known")
    public List<Expression> getKnownExpressions() {
        return expressionService.findAll();
    }

    @PostMapping("/known")
    public void addKnownExpression(@RequestBody Expression newExpression) {
        if (newExpression.getVal() == null || newExpression.getVal().isEmpty()) {
            throw new InvalidExpressionException(
                    "Provided expression '" + newExpression.getVal() + "' is not valid .");

        } else if (expressionService.expressionExists(newExpression)) {
            throw new DuplicateExpressionException(
                    "Duplicate expression. Expression with value '"  + newExpression.getVal()
                            + "' already exists.");
        }

        newExpression.setId(null);
        expressionService.save(newExpression);
    }

    @DeleteMapping("/known")
    public void deleteKnownExpression(@RequestBody Expression expression) {
        if (expression.getVal() == null) {
            throw new InvalidExpressionException(
                    "Provided expression '" + expression.getVal() + "' is not valid .");
        }

        List<Expression> foundExpression = expressionService.findExpressionByVal(expression.getVal());
        if (foundExpression.size() != 0) {
            expressionService.delete(foundExpression.get(0));
        }
    }
}
