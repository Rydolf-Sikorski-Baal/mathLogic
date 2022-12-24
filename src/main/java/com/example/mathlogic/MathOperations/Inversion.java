package com.example.mathlogic.MathOperations;

public class Inversion extends UnaryLogicOperation{
    @Override
    public boolean doOperation(boolean bool) {
        return !bool;
    }
}
