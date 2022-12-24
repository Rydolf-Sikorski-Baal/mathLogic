package com.example.mathlogic.Expression;

import com.example.mathlogic.MathOperations.BinaryLogicOperation;
import com.example.mathlogic.MathOperations.UnaryLogicOperation;
import lombok.Getter;
import lombok.Setter;

public class UnaryOperationNode extends ExpressionTreeNode{
    @Setter
    @Getter
    private ExpressionTreeNode boolNode;
    private final UnaryLogicOperation nodeOperation;

    public UnaryOperationNode(UnaryLogicOperation nodeOperation) {
        this.nodeOperation = nodeOperation;
    }

    public boolean doNodeOperation(boolean bool){ return nodeOperation.doOperation(bool);}
}
