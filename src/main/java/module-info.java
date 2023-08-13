module com.example.todo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens com.example.todo to javafx.fxml;
    exports com.example.todo.model;
    opens com.example.todo.model to javafx.fxml;
    exports com.example.todo;
}