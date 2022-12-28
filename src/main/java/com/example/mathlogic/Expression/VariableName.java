package com.example.mathlogic.Expression;

public record VariableName(String name) {
    @Override
    public boolean equals(Object obj){
        if (obj.getClass() != this.getClass()) return false;
        return this.name.equals(((VariableName) obj).name());
    }
}
