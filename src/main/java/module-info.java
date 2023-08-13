module com.example.todo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.todo to javafx.fxml;
    exports com.example.todo.model;
    opens com.example.todo.model to javafx.fxml;
    exports com.example.todo;
}