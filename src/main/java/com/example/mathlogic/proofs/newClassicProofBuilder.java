package com.example.mathlogic.proofs;

import com.example.mathlogic.Expression.BinaryOperationNode;
import com.example.mathlogic.Expression.ExpressionTree;
import com.example.mathlogic.Expression.ExpressionTreeNode;
import com.example.mathlogic.Expression.SchemeDecorator.ExpressionAsSchemeDecorator;
import com.example.mathlogic.Expression.SchemeDecorator.ExpressionAsSchemeDecoratorInterface;
import com.example.mathlogic.Expression.VariableName;
import com.example.mathlogic.MathOperations.LogicImplementation;
import com.example.mathlogic.Parcer.Parser;

import java.util.ArrayList;

public class newClassicProofBuilder extends AbstractNewProofBuilder{
    private final ArrayList<ExpressionTree> hypotheses = new ArrayList<>();
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
        Parser parser = Parser.getInstance();
        for (ExpressionTree currentStatement : statements){
            if (isOneOfAxioms(currentStatement)) { //аксиома
                statements.add(currentStatement);

                ExpressionTree firstExpression = parser.getExpressionTree("A -> (B -> A)");
                ExpressionAsSchemeDecoratorInterface decorator = new ExpressionAsSchemeDecorator(firstExpression);
                decorator.changeVariableToExpression(new VariableName("A"), currentStatement);
                decorator.changeVariableToExpression(new VariableName("B"), hypA);
                statements.add(decorator.getExpression());//cSt -> (hypA -> cSt))

                ExpressionTree secondExpression = parser.getExpressionTree("A -> B");
                decorator = new ExpressionAsSchemeDecorator(secondExpression);
                decorator.changeVariableToExpression(new VariableName("A"), hypA);
                decorator.changeVariableToExpression(new VariableName("B"), currentStatement);
                statements.add(decorator.getExpression());//hypA -> cSt
            }
            if (isOneOfHypotheses(currentStatement)) { //одна из гипотез
                statements.add(currentStatement);

                ExpressionTree firstExpression = parser.getExpressionTree("A -> (B -> A)");
                ExpressionAsSchemeDecoratorInterface decorator = new ExpressionAsSchemeDecorator(firstExpression);
                decorator.changeVariableToExpression(new VariableName("A"), currentStatement);
                decorator.changeVariableToExpression(new VariableName("B"), hypA);
                statements.add(decorator.getExpression());//cSt -> (hypA -> cSt))

                ExpressionTree secondExpression = parser.getExpressionTree("A -> B");
                decorator = new ExpressionAsSchemeDecorator(secondExpression);
                decorator.changeVariableToExpression(new VariableName("A"), hypA);
                decorator.changeVariableToExpression(new VariableName("B"), currentStatement);
                statements.add(decorator.getExpression());//hypA -> cSt
            }
            if (hypA.equals(currentStatement) && !selfImplicationAdded) { //hypA
                addSelfImplication(hypA);
                selfImplicationAdded = true;
            }
            //выводится из доказанного
            // нужно найти из чего оно выведено (StI -> cSt)
            ExpressionTreeNode previouslyDeductedLeft = null;
            ExpressionTreeNode previouslyDeductedRight = null;
            for (ExpressionTree prStatement : statements){
                ExpressionTreeNode prLeft  = ((BinaryOperationNode)prStatement.root()).getFirstNode();
                ExpressionTreeNode prRight = ((BinaryOperationNode)prStatement.root()).getSecondNode();

                if (currentStatement.root().equals(prRight)) {
                    previouslyDeductedLeft  = prLeft;
                    previouslyDeductedRight = prRight;
                }
            }
            ExpressionTree left = new ExpressionTree(previouslyDeductedLeft, -);
            ExpressionTree right = new ExpressionTree(previouslyDeductedRight, -);

            ExpressionTree firstExpr = parser.getExpressionTree("(A -> (StI -> cSt)) -> ((A -> StI) -> (A -> cSt))");
            ExpressionAsSchemeDecoratorInterface decorator = new ExpressionAsSchemeDecorator(firstExpr);
            decorator.changeVariableToExpression(new VariableName("A"), hypA);
            decorator.changeVariableToExpression(new VariableName("StI"), left);
            decorator.changeVariableToExpression(new VariableName("cST"), right);
            statements.add(firstExpr);//(hypA -> (StI -> cSt)) -> ((hypA -> StI) -> (hypA -> cSt))

            ExpressionTree secondExpr = parser.getExpressionTree("(A -> StI) -> (A -> cSt)");
            decorator = new ExpressionAsSchemeDecorator(secondExpr);
            decorator.changeVariableToExpression(new VariableName("A"), hypA);
            decorator.changeVariableToExpression(new VariableName("StI"), left);
            decorator.changeVariableToExpression(new VariableName("cST"), right);
            statements.add(secondExpr);//(hypA -> StI) -> (hypA -> cSt)

            ExpressionTree thirdExpr = parser.getExpressionTree("A -> cSt");
            decorator.changeVariableToExpression(new VariableName("A"), hypA);
            decorator.changeVariableToExpression(new VariableName("cST"), right);
            statements.add(thirdExpr);//hypA -> cSt
        }
        return this;
    }

    private void addSelfImplication(ExpressionTree hypA){
        Parser parser = Parser.getInstance();
        VariableName AName = new VariableName("A");

        ExpressionTree firstExpr = parser.getExpressionTree("A -> (A -> A)");
        ExpressionAsSchemeDecoratorInterface decorator = new ExpressionAsSchemeDecorator(firstExpr);
        decorator.changeVariableToExpression(AName, hypA);
        statements.add(firstExpr);//hypA -> (hypA -> hypA)

        ExpressionTree secondExpr = parser.getExpressionTree("A -> ((A -> A) -> A)");
        decorator = new ExpressionAsSchemeDecorator(secondExpr);
        decorator.changeVariableToExpression(AName, hypA);
        statements.add(secondExpr);//hypA -> ((hypA -> hypA) -> hypA)

        ExpressionTree thirdExpr = parser.getExpressionTree("(A -> ((A -> A) -> A)) -> ((A -> (A -> A)) -> (A -> A))");
        decorator = new ExpressionAsSchemeDecorator(thirdExpr);
        decorator.changeVariableToExpression(AName, hypA);
        statements.add(thirdExpr);//( hypA -> ((hypA -> hypA) -> hypA) ) -> ( (hypA -> (hypA -> hypA)) -> (hypA -> hypA) )

        ExpressionTree forthExpr = parser.getExpressionTree("(A -> (A -> A)) -> (A -> A)");
        decorator = new ExpressionAsSchemeDecorator(forthExpr);
        decorator.changeVariableToExpression(AName, hypA);
        statements.add(forthExpr);//(hypA -> (hypA -> hypA)) -> (hypA -> hypA)

        ExpressionTree fifthExpr = parser.getExpressionTree("A -> A");
        decorator = new ExpressionAsSchemeDecorator(fifthExpr);
        decorator.changeVariableToExpression(AName, hypA);
        statements.add(fifthExpr);//hypA -> hypA
    }

    @Override
    public Proof build() {
        return new Proof(this.hypotheses, this.axioms, this.finalStatement, this.statements);
    }
}
