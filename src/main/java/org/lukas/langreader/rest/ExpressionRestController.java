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

    @GetMapping("/known/{expressionId}")
    public Expression getKnownExpression(@PathVariable Long expressionId) {
        return expressionService.findExpressionById(expressionId);
    }

    @PostMapping("/known")
    public void addKnownExpression(@RequestBody Expression newExpression) {
        String expressionVal = newExpression.getVal();
        if (expressionVal == null || expressionVal.isEmpty()) {
            throw new InvalidExpressionException(
                    "Provided expression '" + expressionVal + "' is not valid .");

        } else if (expressionService.existsExpressionByVal(expressionVal)) {
            throw new DuplicateExpressionException(
                    "Duplicate expression. Expression with value '"  + newExpression.getVal()
                            + "' already exists.");
        }

        newExpression.setId(null);
        expressionService.save(newExpression);
    }

    @DeleteMapping("/known")
    public void deleteKnownExpression(@RequestBody Expression expression) {
        String expressionVal = expression.getVal();
        if (expressionVal == null) {
            throw new InvalidExpressionException(
                    "Provided expression '" + expressionVal + "' is not valid .");
        }

        Expression foundExpression = expressionService.findExpressionByVal(expressionVal);
        if (foundExpression != null) {
            expressionService.delete(foundExpression);
        }
    }
}
