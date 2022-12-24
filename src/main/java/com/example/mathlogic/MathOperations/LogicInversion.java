package com.example.mathlogic.MathOperations;

public class LogicInversion extends UnaryLogicOperation{
    @Override
    public boolean doOperation(boolean bool) {
        return !bool;
    }
}
