package com.example.mathlogic.proofs;

import com.example.mathlogic.Expression.BinaryOperationNode;
import com.example.mathlogic.Expression.ExpressionTree;
import com.example.mathlogic.Expression.ExpressionTreeNode;
import com.example.mathlogic.MathOperations.LogicImplementation;
import com.example.mathlogic.Parcer.Parser;

import java.util.ArrayList;

public class newClassicProofBuilder extends AbstractNewProofBuilder{
    private ArrayList<ExpressionTree> hypotheses = new ArrayList<>();
    private ArrayList<ExpressionTree> axioms;
    private ExpressionTree finalStatement;

    private ArrayList<ExpressionTree> statements;

    @Override
    public AbstractNewProofBuilder setHypotheses(ArrayList<ExpressionTree> hypotheses, ExpressionTree excludedHypothesis) {
        for (ExpressionTree hypothesis : hypotheses)
            if (hypothesis != excludedHypothesis)
                this.hypotheses.add(hypothesis);
        return this;
    }

    @Override
    public AbstractNewProofBuilder setAxioms() {
        Parser parser = Parser.getInstance();
        axioms.add(parser.getExpressionTree("A -> ( B -> A)"));
        axioms.add(parser.getExpressionTree("(A -> (B -> C)) -> ((A -> B) -> (A -> C))"));
        axioms.add(parser.getExpressionTree("(!A -> !B) -> (B -> A)"));
        axioms.add(parser.getExpressionTree("(A & B) -> (!A -> B)"));
        axioms.add(parser.getExpressionTree("(A & B) -> !(!A & !B))"));
        return null;
    }

    @Override
    public AbstractNewProofBuilder setFinalStatement(ExpressionTree finalStatement) {
        this.finalStatement = finalStatement;
        return this;
    }

    private boolean isOneOfAxioms(ExpressionTree statement){
        for (ExpressionTree axiom : axioms)
            if (axiom.tryAsSchemeFor(statement)) return true;
        return false;
    }
    private boolean isOneOfHypotheses(ExpressionTree statement){
           for (ExpressionTree hypothesis : hypotheses)
               if (hypothesis.equals(statement)) return true;
           return false;
    }
    @Override
    public AbstractNewProofBuilder rebuildProof(ArrayList<ExpressionTree> statements, ExpressionTree hypA) {
        boolean selfImplicationAdded = false;
        for (ExpressionTree currentStatement : statements){
            if (isOneOfAxioms(currentStatement)) { //аксиома
                statements.add(currentStatement);
                statements.add();//cSt -> (hypA -> cSt))
                statements.add();//hypA -> cSt
            }
            if (isOneOfAxioms(currentStatement)) { //одна из гипотез
                statements.add(currentStatement);
                statements.add();//cSt -> (hypA -> cSt))
                statements.add();//hypA -> cSt
            }
            if (hypA.equals(currentStatement) && !selfImplicationAdded) { //hypA
                statements.add();//hypA -> (hypA -> hypA)
                statements.add();//hypA -> ((hypA -> hypA) -> hypA)
                statements.add();//( hypA -> ((hypA -> hypA) -> hypA) ) -> ( (hypA -> (hypA -> hypA)) -> (hypA -> hypA) )
                statements.add();//(hypA -> (hypA -> hypA)) -> (hypA -> hypA)
                statements.add();//hypA -> hypA

                selfImplicationAdded = true;
            }
            //выводится из доказанного
            // нужно найти из чего оно выведено (StI -> cSt)
            ExpressionTreeNode previouslyDeductedLeft = null;
            ExpressionTreeNode previouslyDeductedRight = null;
            for (ExpressionTree prStatement : statements){
                ExpressionTreeNode prLeft  = ((BinaryOperationNode)currentStatement.root()).getFirstNode();
                ExpressionTreeNode prRight = ((BinaryOperationNode)currentStatement.root()).getSecondNode();

                if (currentStatement.root().equals(prRight)) {
                    previouslyDeductedLeft  = prLeft;
                    previouslyDeductedRight = prRight;
                }
            }
            statements.add();//(hypA -> (StI -> cSt)) -> ((hypA -> StI) -> (hypA -> cSt))
            statements.add();//(hypA -> StI) -> (hypA -> cSt)
            statements.add();//hypA -> cSt
        }
        return this;
    }

    @Override
    public Proof build() {
        return new Proof(this.hypotheses, this.axioms, this.finalStatement, this.statements);
    }
}
