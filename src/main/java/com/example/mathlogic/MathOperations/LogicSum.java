package com.example.mathlogic.MathOperations;

public class LogicSum extends BinaryLogicOperation{
    @Override
    public boolean doOperation(boolean first, boolean second) {
        return first || second;
    }
}