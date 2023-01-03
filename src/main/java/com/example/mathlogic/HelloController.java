package com.example.mathlogic;

import com.example.mathlogic.Expression.ExpressionTree;
import com.example.mathlogic.Parcer.Parser;
import com.example.mathlogic.Parcer.ProofParser;
import com.example.mathlogic.Tasks.TaskB;
import com.example.mathlogic.Tasks.TaskC;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class HelloController {
    private final Parser parser;

    public HelloController(){
        parser = Parser.getInstance();
    }

    @FXML
    public TextArea resultText;
    @FXML
    public TextArea inputText;
    @FXML
    public Button Check;

    @FXML
    protected void onCheckButtonClick() {
        String input = inputText.getText();

        ExpressionTree currentTree = parser.getExpressionTree(input);

        TaskB taskB = TaskB.getInstance();
        String result = taskB.checkExpression(currentTree);

        resultText.setText(result);
    }

    public void onRebuildButtonClick() {
        this.resultText.getScene().getWindow().setHeight(1000);
        this.resultText.getScene().getWindow().setWidth(1000);

        String input = inputText.getText();

        ProofParser parser = ProofParser.getInstance();

        TaskC taskC = TaskC.getInstance();
        String result = taskC.getNewProof(parser.parseProofFromString(input));

        resultText.setText(result);
    }
}