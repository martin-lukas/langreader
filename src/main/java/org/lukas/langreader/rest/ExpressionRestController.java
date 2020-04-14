package org.lukas.langreader.rest;

import org.lukas.langreader.entity.Expression;
import org.lukas.langreader.service.ExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<ExpressionResponse> addKnownExpression(
            @RequestBody Expression newExpression) {

        ExpressionResponse response = validatePostRequest(newExpression);

        if (response.getStatus() == 200) {
            newExpression.setId(null);
            expressionService.save(newExpression);
        }

        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

//    @DeleteMapping("/known")
//    public void deleteKnownExpression(@RequestBody Expression expression) {
//        String expressionVal = expression.getVal();
//        if (expressionVal == null) {
//            throw new InvalidExpressionException(
//                    "Provided expression '" + expressionVal + "' is not valid .");
//        }
//
//        Expression foundExpression = expressionService.findExpressionByVal(expressionVal);
//        if (foundExpression != null) {
//            expressionService.delete(foundExpression);
//        }
//    }

    private ExpressionResponse validatePostRequest(Expression expression) {
        ExpressionResponse response = new ExpressionResponse();
        HttpStatus status;
        String message;

        String expressionVal = expression.getVal();
        if (expressionVal == null || expressionVal.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            message = "Provided expression '" + expressionVal + "' is not valid.";
        } else if (expressionService.existsByVal(expressionVal)) {
            status = HttpStatus.CONFLICT;
            message = "Duplicate expression. Expression with value '"
                    + expressionVal + "' already exists.";
        } else if (expression.getType() == null) {
            status = HttpStatus.BAD_REQUEST;
            message = "Type of expression missing. " +
                    "Choose type from [KNOWN, STUDIED, IGNORED].";
        } else {
            status = HttpStatus.OK;
            message = "Expression saved.";
        }

        response.setStatus(status.value());
        response.setMessage(message);
        response.setTimeStamp(System.currentTimeMillis());
        return response;
    }
}
