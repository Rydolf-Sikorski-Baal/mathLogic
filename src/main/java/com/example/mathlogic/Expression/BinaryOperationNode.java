package com.example.mathlogic.Expression;

import com.example.mathlogic.MathOperations.BinaryLogicOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class BinaryOperationNode extends ExpressionTreeNode{
    @Getter
    private final BinaryLogicOperation nodeOperation;
    @Getter @Setter
    private ExpressionTreeNode firstNode;
    @Getter @Setter
    private ExpressionTreeNode secondNode;

    public BinaryOperationNode(BinaryLogicOperation nodeOperation) {
        this.nodeOperation = nodeOperation;
    }

    public boolean doNodeOperation(boolean first, boolean second){ return nodeOperation.doOperation(first, second);}

    @Override
    public boolean getResult(SettedVariablesMap variablesMap) {
        return doNodeOperation(firstNode.getResult(variablesMap), secondNode.getResult(variablesMap));
    }

    @Override
    public boolean equals(Object obj){
        if (obj.getClass() != this.getClass()) return false;
        return (nodeOperation.equals(((BinaryOperationNode) obj).getNodeOperation())) &&
                 (firstNode.equals(((BinaryOperationNode) obj).getFirstNode())) &&
                 (secondNode.equals(((BinaryOperationNode) obj).getSecondNode()));
    }

    public boolean tryAsSchemeFor(Object obj, Map<VariableName, ExpressionTreeNode> map){
        if (obj.getClass() != this.getClass()) return false;
        return (nodeOperation.equals(((BinaryOperationNode) obj).getNodeOperation())) &&
                (firstNode.tryAsSchemeFor(((BinaryOperationNode) obj).getFirstNode(), map)) &&
                (secondNode.tryAsSchemeFor(((BinaryOperationNode) obj).getSecondNode(), map));
    }
}
