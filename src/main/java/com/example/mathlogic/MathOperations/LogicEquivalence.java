package com.example.mathlogic.MathOperations;

public class LogicEquivalence extends BinaryLogicOperation {
    @Override
    public boolean doOperation(boolean first, boolean second) {
        return first == second;
    }
}