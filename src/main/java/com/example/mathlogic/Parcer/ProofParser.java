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

    Integer ind;
    public Proof parseProofFromString(String input){
        ind = 0;
        ArrayList<ExpressionTree> axioms = constructClassicAxioms();
        ArrayList<ExpressionTree> hypotheses = parseHypotheses(input);
        ExpressionTree finalStatement = parseFinalStatement(input);
        ArrayList<ExpressionTree> statements = parseStatements(input);

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

    private ArrayList<ExpressionTree> parseHypotheses(String input) {
        ArrayList<ExpressionTree> hypotheses = new ArrayList<>();

        StringBuilder currentHypothesisString = new StringBuilder();
        while ((input.charAt(ind) != '|') && (input.charAt(ind + 1) != '-')){
            if (input.charAt(ind) == ',') {
                hypotheses.add(parser.getExpressionTree(currentHypothesisString.toString()));
                currentHypothesisString = new StringBuilder();
            }

            currentHypothesisString.append(input.charAt(ind));
            ind++;
        }

        ind += 2;

        return hypotheses;
    }

    private ExpressionTree parseFinalStatement(String input) {
        StringBuilder builder = new StringBuilder();

        while (input.charAt(ind) != '\n'){
            builder.append(input.charAt(ind));
            ind++;
        }
        ind++;

        return parser.getExpressionTree(builder.toString());
    }

    private ArrayList<ExpressionTree> parseStatements(String input) {
        ArrayList<ExpressionTree> statements = new ArrayList<>();

        while (ind < input.length()){
            StringBuilder currentStatementString = new StringBuilder();
            while ((input.charAt(ind) != '\n') && (input.charAt(ind) != '\0')) {
                currentStatementString.append(input.charAt(ind));
                ind++;
            }
            statements.add(parser.getExpressionTree(currentStatementString.toString()));
            ind++;
        }

        return statements;
    }
}
