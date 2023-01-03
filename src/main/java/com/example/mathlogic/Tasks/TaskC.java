package com.example.mathlogic.Tasks;

import com.example.mathlogic.Expression.ExpressionTree;
import com.example.mathlogic.Parcer.ProofParser;
import com.example.mathlogic.proofs.Proof;
import com.example.mathlogic.proofs.ProofBuilderDirector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TaskC {
    private static TaskC TaskCInstance = null;
    private TaskC(){}
    public static TaskC getInstance(){
        if (TaskCInstance == null) TaskCInstance = new TaskC();

        return TaskCInstance;
    }

    public static void main(String[] args) throws IOException{
        TaskC taskC = TaskC.getInstance();

        String input = read(System.in);

        Proof proof = ProofParser.getInstance().parseProofFromString(input);

        String result = taskC.getNewProof(proof);

        System.out.print(result);
    }

    public static String read(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder builder = new StringBuilder();

        String line;
        while((line = reader.readLine()) != null){
            if (line.isEmpty()) break;
            if (line.charAt(0) != '\n') builder.append(line).append('\n');
        }

        return builder.toString();
    }

    public String getNewProof(Proof oldProof){
        ExpressionTree hypA = oldProof.hypotheses().get(oldProof.hypotheses().size() - 1);
        Proof newProof = ProofBuilderDirector.getInstance().buildProof(oldProof, hypA);
        return newProof.toString();
    }
}
