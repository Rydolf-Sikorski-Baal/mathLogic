package com.example.mathlogic;

import com.example.mathlogic.Expression.ExpressionTree;
import com.example.mathlogic.Expression.SettedVariable;
import com.example.mathlogic.Expression.SettedVariablesMap;
import com.example.mathlogic.Expression.VariableName;
import com.example.mathlogic.Parcer.Parser;
import com.example.mathlogic.Parcer.ProofParser;
import com.example.mathlogic.Tasks.TaskB;
import com.example.mathlogic.Tasks.TaskC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.HashMap;

public class HelloController {
    private final Parser parser;

    public HelloController(){
        parser = Parser.getInstance();
    }

    @FXML
    public TextField resultText;
    @FXML
    public TextField inputText;
    @FXML
    public Button Check;

    @FXML
    protected void onCheckButtonClick() {
        String input = inputText.getText();

        ExpressionTree currentTree = parser.getExpressionTree(input);

        TaskB taskB = TaskB.getInstance();
        Pair<Integer, Integer> result = taskB.checkExpression(currentTree);

        resultText.setText(result.toString());
    }

    public void onRebuildButtonClick(ActionEvent actionEvent) {
        String input = inputText.getText();

        ProofParser parser = ProofParser.getInstance();

        TaskC taskC = TaskC.getInstance();
        String result = taskC.getNewProof(parser.parseProofFromString(input));

        resultText.setText(result);
    }
}