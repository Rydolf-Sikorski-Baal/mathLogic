package com.example.mathlogic.Expression;

import com.example.mathlogic.MathOperations.UnaryLogicOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class UnaryOperationNode extends ExpressionTreeNode{
    @Setter
    @Getter
    private ExpressionTreeNode boolNode;
    private final UnaryLogicOperation nodeOperation;

    public UnaryOperationNode(UnaryLogicOperation nodeOperation) {
        this.nodeOperation = nodeOperation;
    }

    public boolean doNodeOperation(boolean bool){ return nodeOperation.doOperation(bool);}

    @Override
    public boolean getResult(SettedVariablesMap variablesMap) {
        return doNodeOperation(boolNode.getResult(variablesMap));
    }

    @Override
    public boolean equals(Object obj){
        if (obj.getClass() != this.getClass()) return false;
        return nodeOperation.equals(((UnaryOperationNode) obj).nodeOperation)
                && (boolNode.equals(((UnaryOperationNode) obj).getBoolNode()));
    }

    public boolean tryAsSchemeFor(Object obj, Map<VariableName, ExpressionTreeNode> map){
        if (obj.getClass() != this.getClass()) return false;
        return nodeOperation.equals(((UnaryOperationNode) obj).nodeOperation)
                && (boolNode.tryAsSchemeFor(((UnaryOperationNode) obj).getBoolNode(), map));
    }

    @Override
    public String toString(){
        return this.nodeOperation.toString() + '(' + this.boolNode.toString() + ')';
    }
}
