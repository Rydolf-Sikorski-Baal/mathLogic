package com.example.mathlogic.Parcer;

import com.example.mathlogic.Expression.*;
import com.example.mathlogic.MathOperations.LogicInversion;
import com.example.mathlogic.MathOperations.LogicStraight;
import com.example.mathlogic.MathOperations.UnaryLogicOperation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Parser {
    private static Parser parserInstance = null;

    private Parser() {
    }

    public static Parser getInstance() {
        if (parserInstance == null) parserInstance = new Parser();

        return parserInstance;
    }

    private enum finiteStates {
        START,
        SPACE,
        VARIABLE,
        OPERATOR,
        OPENING_BRACES,
        CLOSING_BRACES,
        FINISH
    }

    public ExpressionTree getExpressionTree(String input) {
        VariableName a = new VariableName("A");

        UnaryOperationNode root = new UnaryOperationNode(new LogicInversion());
        root.setBoolNode(new VariableNode(a));

        VariablesList variablesList = new VariablesList();
        variablesList.getVariableList().add(a);

        return new ExpressionTree(root, new VariablesList());
    }

    private class ExpressionSequence{}
    private ExpressionSequence getExpressionSequenceFromString(String expression){
        ExpressionSequence expressionSequence = new ExpressionSequence();

        return expressionSequence;
    }

    private ExpressionTreeNode getTreeRootFromSequence(){
        String nodeType = "";/* SparseTable  */.getMaxFromSequence(int left, int right);

        ExpressionTreeNode node = null;
        switch(nodeType){
            case("logicInversion") -> {node = new UnaryOperationNode(new LogicInversion()); break;}
            default -> {node = null;}
        }
    }
}
