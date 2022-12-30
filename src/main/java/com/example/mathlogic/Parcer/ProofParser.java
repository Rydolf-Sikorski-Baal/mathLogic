package com.example.mathlogic.Parcer;

import com.example.mathlogic.Expression.ExpressionTree;
import com.example.mathlogic.proofs.Proof;

import java.util.ArrayList;

public class ProofParser {
    private static ProofParser instance;
    private ProofParser(){}
    public static ProofParser getInstance(){
        if (instance == null) instance = new ProofParser();
        return instance;
    }

    public Proof parseProofFromString(String input){
        Integer ind = 0;
        ArrayList<ExpressionTree> axioms = constructClassicAxioms();
        ArrayList<ExpressionTree> hypotheses = parseHypotheses(input, ind);
        ExpressionTree finalStatement = parseFinalStatement(input, ind);
        ArrayList<ExpressionTree> statements = parseStatements(input, ind);

        return new Proof(hypotheses, axioms, finalStatement, statements);
    }

    Parser parser = Parser.getInstance();
    private ArrayList<ExpressionTree> constructClassicAxioms() {
        ArrayList<ExpressionTree> axioms = new ArrayList<>();

        axioms.add(parser.getExpressionTree("A -> ( B -> A)"));
        axioms.add(parser.getExpressionTree("(A -> (B -> C)) -> ((A -> B) -> (A -> C))"));
        axioms.add(parser.getExpressionTree("(!A -> !B) -> (B -> A)"));
        axioms.add(parser.getExpressionTree("(A & B) -> (!A -> B)"));
        axioms.add(parser.getExpressionTree("(A & B) -> !(!A & !B))"));

        return axioms;
    }

    private ArrayList<ExpressionTree> parseHypotheses(String input, Integer ind) {

    }

    private ExpressionTree parseFinalStatement(String input, Integer ind) {

    }

    private ArrayList<ExpressionTree> parseStatements(String input, Integer ind) {

    }
}
