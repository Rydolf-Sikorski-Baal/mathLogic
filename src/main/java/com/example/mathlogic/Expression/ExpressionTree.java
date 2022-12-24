package com.example.mathlogic.Expression;

public record ExpressionTree(ExpressionTreeNode root, VariablesList variables) {
    public boolean checkExpression(SettedVariablesMap variablesMap){
        return root.getResult(variablesMap);
    }
}
