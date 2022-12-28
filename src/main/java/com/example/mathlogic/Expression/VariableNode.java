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

    @Override
    public boolean equals(Object obj){
        if (obj.getClass() != this.getClass()) return false;
        return this.variableName.equals(((VariableNode) obj).getVariableName());
    }

    public boolean tryAsSchemeFor(Object obj){

    }
}
