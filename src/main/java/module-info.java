module com.example.mathlogic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires lombok;


    opens com.example.mathlogic to javafx.fxml;
    exports com.example.mathlogic;
}