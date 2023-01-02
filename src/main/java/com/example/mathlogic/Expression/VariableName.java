package com.example.mathlogic.Expression;

public record VariableName(String name) {
    @Override
    public boolean equals(Object obj){
        if (obj.getClass() != this.getClass()) return false;
        return this.name.equals(((VariableName) obj).name());
    }

    @Override
    public String toString(){
        if (this.name.charAt(0) == '_') return this.name.substring(1);
        return this.name;
    }
}
