package com.example.mathlogic.Expression;

import com.example.mathlogic.MathOperations.BinaryLogicOperation;

public class BinaryOperationNode extends ExpressionTreeNode{
    private final BinaryLogicOperation nodeOperation;

    public BinaryOperationNode(BinaryLogicOperation nodeOperation) {
        this.nodeOperation = nodeOperation;
    }

    public boolean doNodeOperation(boolean first, boolean second){ return nodeOperation.doOperation(first, second);}
}
