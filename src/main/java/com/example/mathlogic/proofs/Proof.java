package com.example.mathlogic.proofs;

import com.example.mathlogic.Expression.ExpressionTree;

import java.util.ArrayList;

public record Proof(ArrayList<ExpressionTree> hypotheses, ArrayList<ExpressionTree> axioms, ExpressionTree finalStatement, ArrayList<ExpressionTree> statements) {
    @Override
    public String toString(){
        return getHypothesesString(hypotheses) +
                "|-" +
                finalStatement.toString() +
                '\n' +
                getStatementsString(statements);
    }

    private String getStatementsString(ArrayList<ExpressionTree> statements) {
        StringBuilder result = new StringBuilder();
        for (ExpressionTree currentStatement : statements)
            result.append(currentStatement.toString()).append('\n');
        return result.toString();
    }

    private String getHypothesesString(ArrayList<ExpressionTree> hypotheses){
        StringBuilder result = new StringBuilder();
        for (ExpressionTree currentStatement : hypotheses)
            result.append(currentStatement.toString()).append(',');
        if (result.length() > 0) result.delete(result.length() - 1, result.length() - 1);
        return result.toString();
    }
}
