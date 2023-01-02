package com.example.mathlogic.Tasks;

import com.example.mathlogic.Expression.*;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class TaskB {
    private static TaskB taskBInstance = null;
    private TaskB(){}
    public static TaskB getInstance(){
        if (taskBInstance == null) taskBInstance = new TaskB();

        return taskBInstance;
    }

    public String checkExpression(ExpressionTree tree){
        int trueCases = 0;
        int falseCases = 0;

        VariablesList variablesList = tree.variables();
        Map<VariableName, SettedVariable> map = new HashMap<>();

        for (VariableName variableName : variablesList.getVariableList()){
            map.put(variableName, new SettedVariable(variableName, false));
        }

        SettedVariablesMap settedVariablesMap = new SettedVariablesMap(map);

        do{
            if (tree.checkExpression(settedVariablesMap)){
                trueCases++;
            }else{
                falseCases++;
            }
        }while (iterate(variablesList, settedVariablesMap));

        if (trueCases == 0) return "Unsatisfiable";
        if (falseCases == 0) return "Valid";
        return "Satisfiable and invalid, " + trueCases + " true and " + falseCases + " false cases";
    }

    private boolean iterate(VariablesList list, SettedVariablesMap map){
        for (VariableName current_variable : list.getVariableList()){
            if (!(map.variableMap().get(current_variable).value())){
                map.variableMap().put(current_variable, new SettedVariable(current_variable, true));
                return true;
            }
            map.variableMap().put(current_variable, new SettedVariable(current_variable, false));
        }
        return false;
    }
}
