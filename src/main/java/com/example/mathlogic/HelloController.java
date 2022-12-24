package com.example.mathlogic;

import com.example.mathlogic.Expression.ExpressionTree;
import com.example.mathlogic.Expression.SettedVariable;
import com.example.mathlogic.Expression.SettedVariablesMap;
import com.example.mathlogic.Expression.VariableName;
import com.example.mathlogic.Parcer.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.HashMap;

public class HelloController {
    private final Parser parser;
    public HelloController(){
        parser = Parser.getInstance();
    }

    @FXML
    public TextField inputText;
    @FXML
    public Button Check;

    @FXML
    protected void onCheckButtonClick() {
        String input = inputText.getText();

        ExpressionTree currentTree = parser.getExpressionTree(input);

        HashMap<VariableName, SettedVariable> map = new HashMap<>();
        for (VariableName variable : currentTree.variables().getVariableList()){
            map.put(variable, new SettedVariable(variable,true));
        }
        boolean result = currentTree.checkExpression(new SettedVariablesMap(map));
    }
}