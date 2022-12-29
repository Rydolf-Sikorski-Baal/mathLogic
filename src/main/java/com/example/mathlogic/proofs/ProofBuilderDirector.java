package com.example.mathlogic.proofs;

import com.example.mathlogic.Expression.ExpressionTree;

public class ProofBuilderDirector {
    private static ProofBuilderDirector instance = null;
    private ProofBuilderDirector(){}
    public static ProofBuilderDirector getInstance(){
        if (instance == null) instance = new ProofBuilderDirector();
        return instance;
    }

    public Proof buildProof(Proof oldProof, ExpressionTree hypA){
        AbstractNewProofBuilder builder = new newClassicProofBuilder();
        return builder
                .setHypotheses(oldProof.hypotheses(), hypA)
                .setAxioms()
                .setFinalStatement(oldProof.finalStatement())
                .rebuildProof(oldProof.statements(), hypA)
                .build();
    }
}
