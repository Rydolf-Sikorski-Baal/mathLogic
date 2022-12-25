package com.example.mathlogic.Parcer;

import com.example.mathlogic.Expression.*;
import com.example.mathlogic.MathOperations.LogicImplementation;
import com.example.mathlogic.MathOperations.LogicInversion;
import com.example.mathlogic.MathOperations.LogicMultiplication;
import com.example.mathlogic.MathOperations.LogicSum;
import com.example.mathlogic.SparseTable;

import java.util.ArrayList;
import java.util.Objects;

public class Parser {
    private static Parser parserInstance = null;
    private Parser() {}
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

    VariablesList variablesList;
    public ExpressionTree getExpressionTree(String input) {
        getExpressionSequenceFromString(input.toCharArray());
        getVariablesListFromExpressionSequence();
        ExpressionTreeNode root = getTreeRootFromSequence(0, input.length());

        return new ExpressionTree(root, variablesList);
    }

    private void getVariablesListFromExpressionSequence(){
        variablesList = new VariablesList();

        for (String current : expressionSequence){
            if (Objects.equals(current, "_inv")) continue;
            if (Objects.equals(current, "_or")) continue;
            if (Objects.equals(current, "_and")) continue;
            if (Objects.equals(current, "_impl")) continue;
            if (Objects.equals(current, "_opBr")) continue;
            if (Objects.equals(current, "_clBr")) continue;

            variablesList.getVariableList().add(new VariableName(current));
        }
    }

    private boolean isEmpty(char ch){return (ch == ' ') || (ch == '\n');}
    String variableSymbolRegexp = "([])";
    private boolean isCorrectVariableSymbol(char ch){return ch == 'a';}
    String operatorSymbolRegexp = "[!|&->]";
    private boolean isCorrectOperatorSymbol(char ch){return ch == '|';}
    private void getExpressionSequenceFromString(char[] expression){
        expressionSequence = new ArrayList<>();

        int ind = 0;

        finiteStates state = finiteStates.ANY_TYPE;
        while(state != finiteStates.FINISH)
            switch (state) {
                case ANY_TYPE -> {
                    if (expression[ind] == '(') state = finiteStates.OPENING_BRACES;
                    if (expression[ind] == ')') state = finiteStates.CLOSING_BRACES;
                    if (isEmpty(expression[ind])) state = finiteStates.SPACE;
                    if (isCorrectVariableSymbol(expression[ind])) state = finiteStates.VARIABLE;
                    if (isCorrectOperatorSymbol(expression[ind])) state = finiteStates.OPERATOR;
                    if (expression[ind] == '\0') state = finiteStates.FINISH;
                }
                case SPACE -> {
                    while (isEmpty(expression[ind])) ind++;
                    state = finiteStates.ANY_TYPE;
                }
                case VARIABLE -> {
                    StringBuilder name = new StringBuilder();
                    while (isCorrectVariableSymbol(expression[ind])) {
                        name.append(expression[ind]);
                        ind++;
                    }
                    expressionSequence.add(name.toString());
                    state = finiteStates.ANY_TYPE;
                }
                case OPERATOR -> {
                    if (expression[ind] == '!') {
                        expressionSequence.add("_inv");
                        ind++;
                        break;
                    }
                    if (expression[ind] == '|') {
                        expressionSequence.add("_or");
                        ind++;
                        break;
                    }
                    if (expression[ind] == '&') {
                        expressionSequence.add("_and");
                        ind++;
                        break;
                    }
                    if (expression[ind] == '-') {
                        expressionSequence.add("_impl");
                        ind += 2;
                        break;
                    }
                    state = finiteStates.ANY_TYPE;
                }
                case OPENING_BRACES -> {
                    expressionSequence.add("_opBr");
                    ind++;
                    state = finiteStates.ANY_TYPE;
                }
                case CLOSING_BRACES -> {
                    expressionSequence.add("_clBr");
                    ind++;
                    state = finiteStates.ANY_TYPE;
                }
            }
    }

    private ArrayList<String> expressionSequence;
    private SparseTable sparseTable;

    private Integer getPriority(String current){
        if (Objects.equals(current, "_inv")) return 1;
        if (Objects.equals(current, "_or")) return 2;
        if (Objects.equals(current, "_and")) return 2;
        if (Objects.equals(current, "_impl")) return 3;

        return -1000*1000*1000;
    }
    private void getSparseTableFromSequence(){
        ArrayList<Integer> list = new ArrayList<>();

        int current_delta = 0;
        for (String current_element : expressionSequence){
            if (Objects.equals(current_element, "_opBr")) current_delta -= 10;
            if (Objects.equals(current_element, "_clBr")) current_delta += 10;

            list.add(getPriority(current_element) + current_delta);
        }

        sparseTable = new SparseTable(list);
    }
    private ExpressionTreeNode getTreeRootFromSequence(int left, int right){
        getSparseTableFromSequence();
        int nodeTypeInd = sparseTable.getMaxIndexFromSequence(left, right);
        String nodeTypeName = expressionSequence.get(nodeTypeInd);

        ExpressionTreeNode node;
        switch(nodeTypeName){
            case "_inv"     -> {
                node = new UnaryOperationNode(new LogicInversion());
                ((UnaryOperationNode)node)
                        .setBoolNode(getTreeRootFromSequence(nodeTypeInd + 1, expressionSequence.size()));
            }
            case "_or"      -> {
                node = new BinaryOperationNode(new LogicSum());
                ((BinaryOperationNode)node)
                        .setFirstNode(getTreeRootFromSequence(0, nodeTypeInd - 1));
                ((BinaryOperationNode)node)
                        .setSecondNode(getTreeRootFromSequence(nodeTypeInd + 1, expressionSequence.size()));
            }
            case "_and"     -> {
                node = new BinaryOperationNode(new LogicMultiplication());
                ((BinaryOperationNode)node)
                        .setFirstNode(getTreeRootFromSequence(0, nodeTypeInd - 1));
                ((BinaryOperationNode)node)
                        .setSecondNode(getTreeRootFromSequence(nodeTypeInd + 1, expressionSequence.size()));
            }
            case "_impl"    -> {
                node = new BinaryOperationNode(new LogicImplementation());
                ((BinaryOperationNode)node)
                        .setFirstNode(getTreeRootFromSequence(0, nodeTypeInd - 1));
                ((BinaryOperationNode)node)
                        .setSecondNode(getTreeRootFromSequence(nodeTypeInd + 1, expressionSequence.size()));
            }
            default         -> node = new VariableNode(new VariableName(nodeTypeName));
        }

        return node;
    }
}
