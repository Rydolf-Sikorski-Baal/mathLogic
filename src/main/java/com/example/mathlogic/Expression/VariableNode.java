package com.example.mathlogic.Expression;

import lombok.Getter;

public class VariableNode extends ExpressionTreeNode{
    @Getter
    private final VariableName variableName;

    public VariableNode(VariableName variableName) {
        this.variableName = variableName;
    }

    @Override
    public boolean getResult(SettedVariablesMap variablesMap) {
        return variablesMap.variableMap().get(variableName).value();
    }
}
