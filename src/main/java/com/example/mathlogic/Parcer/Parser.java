package com.example.mathlogic.Parcer;

import com.example.mathlogic.Expression.*;
import com.example.mathlogic.MathOperations.LogicInversion;
import com.example.mathlogic.MathOperations.LogicStraight;
import com.example.mathlogic.MathOperations.UnaryLogicOperation;

import java.util.*;

public class Parser {
    private static Parser parserInstance = null;

    private Parser() {
    }

    public static Parser getInstance() {
        if (parserInstance == null) parserInstance = new Parser();

        return parserInstance;
    }

    private enum finiteStates {
        ANY_TYPE,
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

    private boolean isEmpty(char ch){return (ch == ' ') || (ch == '\n');}
    private boolean isCorrectVariableSymbol(char ch){return ch == 'a';}
    private boolean isCorrectOperatorSymbol(char ch){return ch == '!';}
    private List<String> getExpressionSequenceFromString(char[] expression){
        List<String> expressionSequence = new ArrayList<>();

        int ind = 0;

        finiteStates state = finiteStates.ANY_TYPE;
        while(state != finiteStates.FINISH)
            switch(state){
                case ANY_TYPE:
                    if (expression[ind] == '(')                     state = finiteStates.OPENING_BRACES;
                    if (expression[ind] == ')')                     state = finiteStates.CLOSING_BRACES;
                    if (isEmpty(expression[ind]))                   state = finiteStates.SPACE;
                    if (isCorrectVariableSymbol(expression[ind]))   state = finiteStates.VARIABLE;
                    if (isCorrectOperatorSymbol(expression[ind]))   state = finiteStates.OPERATOR;
                    if (expression[ind] == '\0')                    state = finiteStates.FINISH;
                    break;
                case SPACE:
                    while (isEmpty(expression[ind])) ind++;

                    state = finiteStates.ANY_TYPE;

                    break;
                case VARIABLE:
                    StringBuilder name = new StringBuilder();
                    while (isCorrectVariableSymbol(expression[ind])){
                        name.append(expression[ind]);
                        ind++;
                    }

                    expressionSequence.add(name.toString());

                    state = finiteStates.ANY_TYPE;
                    break;
                case OPERATOR:
                    if (expression[ind] == '!'){expressionSequence.add("_inv");  ind++;   break;}
                    if (expression[ind] == '|'){expressionSequence.add("_or");   ind++;   break;}
                    if (expression[ind] == '&'){expressionSequence.add("_and");  ind++;   break;}
                    if (expression[ind] == '-'){expressionSequence.add("_impl"); ind +=2; break;}

                    state = finiteStates.ANY_TYPE;
                    break;
                case OPENING_BRACES:
                    expressionSequence.add("_opBr");

                    state = finiteStates.ANY_TYPE;
                    break;
                case CLOSING_BRACES:
                    expressionSequence.add("_clBr");

                    state = finiteStates.ANY_TYPE;
                    break;
            }

        return expressionSequence;
    }

    private ExpressionTreeNode getTreeRootFromSequence(){
        String nodeType = "";/* SparseTable  */.getMaxIndexFromSequence(int left, int right);

        ExpressionTreeNode node = null;
        switch(nodeType){
            /* различные варианты узлов */
            case("logicInversion") -> {node = new UnaryOperationNode(new LogicInversion()); break;}
            default -> {node = null;}
        }

        /* вызов этой функции рекурсивно если полученная вершина соответствует операции
        (один или два вызова в зависимости от типа операции) */
    }
}
