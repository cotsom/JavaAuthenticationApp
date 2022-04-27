module com.example.authorization {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.authorization to javafx.fxml;
    exports com.example.authorization;
}