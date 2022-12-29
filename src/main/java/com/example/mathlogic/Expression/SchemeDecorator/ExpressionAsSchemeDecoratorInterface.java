package com.example.mathlogic.Expression.SchemeDecorator;

import com.example.mathlogic.Expression.ExpressionTree;
import com.example.mathlogic.Expression.VariableName;

public interface ExpressionAsSchemeDecoratorInterface {
    ExpressionTree getExpression();
    boolean changeVariableToExpression(VariableName variableName, ExpressionTree expression);
}
