package com.example.mathlogic.proofs;

import com.example.mathlogic.Expression.ExpressionTree;

import java.util.ArrayList;

public record Proof(ArrayList<ExpressionTree> hypotheses, ArrayList<ExpressionTree> axioms, ExpressionTree finalStatement, ArrayList<ExpressionTree> statements) {
    @Override
    public String toString(){
        return "";
    }
}
