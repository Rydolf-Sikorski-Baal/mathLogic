package com.example.mathlogic.Parcer;

import com.example.mathlogic.Expression.*;
import com.example.mathlogic.MathOperations.LogicStraight;

public class Parser{
    private static Parser parserInstance = null;
    private Parser(){}

    public static Parser getInstance(){
        if (parserInstance == null) parserInstance = new Parser();

        return parserInstance;
    }

    public ExpressionTree getExpressionTree(String input) {
        return new ExpressionTree(new UnaryOperationNode(new LogicStraight()), new VariablesList());
    }
}
