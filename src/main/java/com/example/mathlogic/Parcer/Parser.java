package com.example.mathlogic.Parcer;

import com.example.mathlogic.Expression.*;
import com.example.mathlogic.MathOperations.*;
import com.example.mathlogic.SparseTable;

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
    String variableSymbolRegexp = "([])";
    private boolean isCorrectVariableSymbol(char ch){return ch == 'a';}
    String operatorSymbolRegexp = "[!|&->]";
    private boolean isCorrectOperatorSymbol(char ch){return ch == '|';}
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

                    ind++;

                    state = finiteStates.ANY_TYPE;
                    break;
                case CLOSING_BRACES:
                    expressionSequence.add("_clBr");

                    ind++;

                    state = finiteStates.ANY_TYPE;
                    break;
            }

        return expressionSequence;
    }

    private ExpressionTreeNode getTreeRootFromSequence(int left, int right, ArrayList<String> expressionSequence){
        SparseTable sparseTable = new SparseTable();
        int nodeTypeInd = sparseTable.getMaxIndexFromSequence(left, right, expressionSequence);
        String nodeTypeName = expressionSequence.get(nodeTypeInd);

        ExpressionTreeNode node = null;
        switch(nodeTypeName){
            case "_inv"     -> {
                node = new UnaryOperationNode(new LogicInversion());
                ((UnaryOperationNode)node)
                        .setBoolNode(getTreeRootFromSequence(nodeTypeInd + 1, expressionSequence.size(), expressionSequence));
                break;
            }
            case "_or"      -> {
                node = new BinaryOperationNode(new LogicSum());
                ((BinaryOperationNode)node)
                        .setFirstNode(getTreeRootFromSequence(0, nodeTypeInd - 1, expressionSequence));
                ((BinaryOperationNode)node)
                        .setSecondNode(getTreeRootFromSequence(nodeTypeInd + 1, expressionSequence.size(), expressionSequence));
                break;
            }
            case "_and"     -> {
                node = new BinaryOperationNode(new LogicMultiplication());
                ((BinaryOperationNode)node)
                        .setFirstNode(getTreeRootFromSequence(0, nodeTypeInd - 1, expressionSequence));
                ((BinaryOperationNode)node)
                        .setSecondNode(getTreeRootFromSequence(nodeTypeInd + 1, expressionSequence.size(), expressionSequence));
                break;
            }
            case "_impl"    -> {
                node = new BinaryOperationNode(new LogicImplementation());
                ((BinaryOperationNode)node)
                        .setFirstNode(getTreeRootFromSequence(0, nodeTypeInd - 1, expressionSequence));
                ((BinaryOperationNode)node)
                        .setSecondNode(getTreeRootFromSequence(nodeTypeInd + 1, expressionSequence.size(), expressionSequence));
                break;
            }
            case "_opBr"    -> {}
            case "clBr"     -> {}
            default         -> {node = new VariableNode(new VariableName(nodeTypeName));}
        }

        return node;
    }
}
