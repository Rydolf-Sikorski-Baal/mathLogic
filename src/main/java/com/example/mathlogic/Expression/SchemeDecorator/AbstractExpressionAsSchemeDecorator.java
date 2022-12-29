package com.example.mathlogic.Expression.SchemeDecorator;

import com.example.mathlogic.Expression.ExpressionTree;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class AbstractExpressionAsSchemeDecorator implements ExpressionAsSchemeDecoratorInterface {
    @Getter
    private ExpressionTree expression;
}
