package com.example.mathlogic.Expression;

import com.example.mathlogic.MathOperations.BinaryLogicOperation;
import lombok.Getter;
import lombok.Setter;

public class BinaryOperationNode extends ExpressionTreeNode{
    private final BinaryLogicOperation nodeOperation;
    @Setter @Getter
    private ExpressionTreeNode firstNode, secondNode;

    public BinaryOperationNode(BinaryLogicOperation nodeOperation) {
        this.nodeOperation = nodeOperation;
    }

    public boolean doNodeOperation(boolean first, boolean second){ return nodeOperation.doOperation(first, second);}
}
