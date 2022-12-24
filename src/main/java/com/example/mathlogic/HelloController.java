package com.example.mathlogic;

import com.example.mathlogic.Expression.ExpressionTree;
import com.example.mathlogic.Parcer.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HelloController {
    private final Parser parcer;
    public HelloController(){
        parcer = Parser.getInstance();
    }

    @FXML
    public TextField inputText;
    @FXML
    public Button Check;

    @FXML
    protected void onCheckButtonClick() {
        String input = inputText.getText();

        ExpressionTree currentTree = parcer.getExpressionTree(input);

        /* здесь нужно протестировать выражение и вывести кол-во различных типов комбинаций */
    }
}