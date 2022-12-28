package com.example.mathlogic.Expression;

public record ExpressionTree(ExpressionTreeNode root, VariablesList variables) {
    public boolean checkExpression(SettedVariablesMap variablesMap){
        return root.getResult(variablesMap);
    }

    @Override
    public boolean equals(Object obj){
        if (obj.getClass() != this.getClass()) return false;
        return this.root.equals(((ExpressionTree) obj).root);
    }

    public boolean tryAsSchemeFor(Object obj){
        if (obj.getClass() != this.getClass()) return false;
        return root.tryAsSchemeFor(((ExpressionTree) obj).root);
    }
}
