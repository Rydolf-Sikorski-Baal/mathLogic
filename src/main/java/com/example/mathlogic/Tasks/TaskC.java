package com.example.mathlogic.Tasks;

import com.example.mathlogic.Expression.ExpressionTree;
import com.example.mathlogic.proofs.AbstractNewProofBuilder;
import com.example.mathlogic.proofs.Proof;
import com.example.mathlogic.proofs.ProofBuilderDirector;
import com.example.mathlogic.proofs.newClassicProofBuilder;

public class TaskC {
    private static TaskC TaskCInstance = null;
    private TaskC(){}
    public static TaskC getInstance(){
        if (TaskCInstance == null) TaskCInstance = new TaskC();

        return TaskCInstance;
    }

    public String getNewProof(Proof oldProof){
        ExpressionTree hypA = oldProof.hypotheses().get(oldProof.hypotheses().size() - 1);
        Proof newProof = ProofBuilderDirector.getInstance().buildProof(oldProof, hypA);
        return newProof.toString();
    }
}
