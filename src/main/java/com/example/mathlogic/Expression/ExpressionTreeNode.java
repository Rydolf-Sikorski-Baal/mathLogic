package com.example.mathlogic.Expression;

public abstract class ExpressionTreeNode {
    public abstract boolean getResult(SettedVariablesMap variablesMap);

    public abstract boolean tryAsSchemeFor(Object obj);
}
