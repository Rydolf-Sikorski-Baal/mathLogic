package com.example.mathlogic.Tasks;

import com.example.mathlogic.Expression.*;
import com.example.mathlogic.proofs.AbstractNewProofBuilder;
import com.example.mathlogic.proofs.Proof;
import com.example.mathlogic.proofs.newClassicProofBuilder;

public class TaskC {
    private static TaskC TaskCInstance = null;
    private TaskC(){}
    public TaskC getInstance(){
        if (TaskCInstance == null) TaskCInstance = new TaskC();

        return TaskCInstance;
    }

    public String getNewProof(Proof oldProof, ExpressionTree hypA){
        AbstractNewProofBuilder builder = new newClassicProofBuilder();
        Proof newProof = builder
                .setHypotheses(oldProof.hypotheses(), hypA)
                .setAxioms()
                .setFinalStatement(oldProof.finalStatement())
                .rebuildProof(oldProof.statements(), hypA)
                .build();
        return newProof.toString();
    }
}
