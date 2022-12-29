package com.example.mathlogic.Expression.SchemeDecorator;

import com.example.mathlogic.Expression.*;

public class ExpressionAsSchemeDecorator extends AbstractExpressionAsSchemeDecorator{
    public ExpressionAsSchemeDecorator(ExpressionTree expression) {
        super(expression);
    }

    @Override
    public boolean changeVariableToExpression(VariableName variableName, ExpressionTree expression) {
        for (VariableName currentVariableName : super.getExpression().variables().getVariableList())
            if (currentVariableName.equals(variableName))
                return changeCheckedVariableToExpression(variableName, expression, this.getExpression().root());
        return false;
    }

    private boolean changeCheckedVariableToExpression(VariableName variableName, ExpressionTree expression, ExpressionTreeNode node){
        if (node.getClass() == VariableNode.class){
            if (((VariableNode) node).getVariableName().equals(variableName)) node = expression.root();
            return true;
        }

        if(node.getClass() == UnaryOperationNode.class){
            return changeCheckedVariableToExpression(variableName, expression, ((UnaryOperationNode) node).getBoolNode());
        }

        if (node.getClass() == BinaryOperationNode.class){
            return
                    changeCheckedVariableToExpression(variableName, expression, ((BinaryOperationNode) node).getFirstNode())
                    &&
                    changeCheckedVariableToExpression(variableName, expression, ((BinaryOperationNode) node).getSecondNode());
        }

        return false;
    }
}
