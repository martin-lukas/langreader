package org.lukas.langreader.rest;

import org.lukas.langreader.dao.ExpressionRepository;
import org.lukas.langreader.entity.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO: in the future, add GET APIs for statistics maybe
 */
@RestController
@RequestMapping("/expressions")
public class ExpressionRestController {
    private final ExpressionRepository expressionRepository;

    private final String INVALID_EXPR_MSG = "Provided expression '%s' is not valid.";
    private final String MISSING_TYPE_MSG = "Type of expression missing. " +
            "Choose type from [KNOWN, STUDIED, IGNORED].";
    private final String NOT_IN_DB_MSG = "Provided expression '%s' isn't in the DB yet.";

    @Autowired
    public ExpressionRestController(ExpressionRepository expressionRepository) {
        this.expressionRepository = expressionRepository;
    }

    @PostMapping("/enrich")
    public ResponseEntity<ExpressionResponse> enrichExpressions(
            @RequestBody List<Expression> expressions) {

        return null;
    }

    @PostMapping
    public ResponseEntity<ExpressionResponse> addExpression(
            @RequestBody Expression newExpression) {

        ExpressionResponse response = validateAddExpressionRequest(newExpression);

        if (response.getStatus() == 200) {
            newExpression.setId(null);
            expressionRepository.save(newExpression);
        }

        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @PutMapping
    public ResponseEntity<ExpressionResponse> updateExpression(
            @RequestBody Expression expression) {

        ExpressionResponse response = validateUpdateExpressionRequest(expression);

        if (response.getStatus() == 200) {
            Expression foundExpression = expressionRepository.findExpressionByVal(expression.getVal());
            response.setMessage(response.getMessage());
            // already checked that it's in the DB
            expression.setId(foundExpression.getId());
            expressionRepository.save(expression);
        }

        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @DeleteMapping
    public ResponseEntity<ExpressionResponse> deleteExpression(
            @RequestBody Expression expression) {

        ExpressionResponse response = validateDeleteExpressionRequest(expression);

        if (response.getStatus() == 200) {
            Expression foundExpression = expressionRepository.findExpressionByVal(expression.getVal());
            // already checked that it's in the DB
            expressionRepository.delete(foundExpression);
        }

        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    private ExpressionResponse validateAddExpressionRequest(Expression expression) {
        ExpressionResponse response = new ExpressionResponse();
        HttpStatus status;
        String message;

        String expressionVal = expression.getVal();
        if (expressionVal == null || expressionVal.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(INVALID_EXPR_MSG, expressionVal);
        } else if (expression.getType() == null) {
            status = HttpStatus.BAD_REQUEST;
            message = MISSING_TYPE_MSG;
        } else if (expressionRepository.existsByVal(expressionVal)) {
            status = HttpStatus.CONFLICT;
            message = "Duplicate expression. Expression '" + expressionVal + "' already exists.";
        } else {
            status = HttpStatus.OK;
            message = "Expression saved.";
        }

        response.setStatus(status.value());
        response.setMessage(message);
        response.setTimeStamp(System.currentTimeMillis());
        return response;
    }

    private ExpressionResponse validateUpdateExpressionRequest(Expression expression) {
        ExpressionResponse response = new ExpressionResponse();
        HttpStatus status;
        String message;

        String expressionVal = expression.getVal();
        if (expressionVal == null || expressionVal.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(INVALID_EXPR_MSG, expressionVal);
        } else if (expression.getType() == null) {
            status = HttpStatus.BAD_REQUEST;
            message = MISSING_TYPE_MSG;
        } else if (!expressionRepository.existsByVal(expressionVal)) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(NOT_IN_DB_MSG, expressionVal);
        }  else {
            status = HttpStatus.OK;
            message = "Expression updated.";
        }

        response.setStatus(status.value());
        response.setMessage(message);
        response.setTimeStamp(System.currentTimeMillis());
        return response;
    }

    private ExpressionResponse validateDeleteExpressionRequest(Expression expression) {
        ExpressionResponse response = new ExpressionResponse();
        HttpStatus status;
        String message;

        String expressionVal = expression.getVal();
        if (expressionVal == null || expressionVal.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(INVALID_EXPR_MSG, expressionVal);
        } else if (!expressionRepository.existsByVal(expressionVal)) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(NOT_IN_DB_MSG, expressionVal);
        } else {
            status = HttpStatus.OK;
            message = "Expression deleted.";
        }

        response.setStatus(status.value());
        response.setMessage(message);
        response.setTimeStamp(System.currentTimeMillis());
        return response;
    }
}
