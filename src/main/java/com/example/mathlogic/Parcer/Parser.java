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

    private class ExpressionSequence{
        public void addElement(){}
    }
    private boolean isEmpty(char ch){return (ch == ' ') || (ch == '\n');}
    private boolean isCorrectVariableSymbol(char ch){return ch == 'a';}
    private ExpressionSequence getExpressionSequenceFromString(char[] expression){
        ExpressionSequence expressionSequence = new ExpressionSequence();

        int ind = 0;

        finiteStates state = finiteStates.START;
        while(state != finiteStates.FINISH)
            switch(state){
                case ANY_TYPE:
                    /* необходимо определить тип первого символа (конец строки -> finish) */
                    switch(expression[ind]){
                        case '(':
                            state = finiteStates.OPENING_BRACES;
                            break;
                        case ')':
                            state = finiteStates.CLOSING_BRACES;
                            break;
                        case '-':
                        case '|':
                        case '&':
                        case '!':
                            state = finiteStates.OPERATOR;
                            break;
                    }
                    state = finiteStates.FINISH;
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

                    /* добавить переменную в множество переменных */

                    state = finiteStates.ANY_TYPE;
                    break;
                case OPERATOR:
                    if (expression[ind] == '!'){/* добавить элемент в выражение */ ind++;   break;}
                    if (expression[ind] == '|'){/* добавить элемент в выражение */ ind++;   break;}
                    if (expression[ind] == '&'){/* добавить элемент в выражение */ ind++;   break;}
                    if (expression[ind] == '-'){/* добавить элемент в выражение */ ind +=2; break;}

                    state = finiteStates.ANY_TYPE;
                    break;
                case OPENING_BRACES:
                    /* просто занести в выражение и сменить тип */

                    state = finiteStates.ANY_TYPE;
                    break;
                case CLOSING_BRACES:
                    /* просто занести в выражение и сменить тип */

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
