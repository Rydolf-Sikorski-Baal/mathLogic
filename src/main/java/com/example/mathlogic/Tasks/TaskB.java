package com.example.mathlogic.Tasks;

import com.example.mathlogic.Expression.*;

import java.util.HashMap;
import java.util.Map;

public class TaskB {
    private static TaskB taskBInstance = null;
    private TaskB(){}
    public TaskB getInstance(){
        if (taskBInstance == null) taskBInstance = new TaskB();

        return taskBInstance;
    }

    public void checkExpression(ExpressionTree tree){
        VariablesList variablesList = tree.variables();
        Map<VariableName, SettedVariable> map = new HashMap<>();

        for (VariableName variableName : variablesList.getVariableList()){
            map.put(variableName, new SettedVariable(variableName, false));
        }

        SettedVariablesMap settedVariablesMap = new SettedVariablesMap(map);

        while (iterate(variablesList, settedVariablesMap)) tree.checkExpression(settedVariablesMap);
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
