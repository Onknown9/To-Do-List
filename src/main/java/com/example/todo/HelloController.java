package com.example.todo;

import com.example.todo.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;


public class HelloController {
    @FXML
    private ListView<Task> tasks_list;
    @FXML
    private Button submit_button;
    @FXML
    private Button delete_button;
    @FXML
    private TextField name_text;
    @FXML
    private TextField desc_text;
    @FXML
    private DatePicker deadline_date;
    @FXML
    public void initialize() {
        // Populate the ListView with task names
        Task taskNames = new Task();
        tasks_list.setItems(taskNames.returnList());

        // Set a listener for when a task is selected
        tasks_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Update the text fields with task information
                name_text.setText(newValue.getName());
                desc_text.setText(newValue.getDescription());
                deadline_date.setValue(newValue.getDeadline());
            }
        });
    }
    @FXML
    private void submitClick(ActionEvent event) {
        String taskName = name_text.getText();
        String taskDescription = desc_text.getText();
        LocalDate deadline = deadline_date.getValue();
        Task newTask = new Task(taskName,taskDescription,deadline);
        newTask.addToList(newTask);
    }
    @FXML
    private void deleteClick(ActionEvent event) {
        // Handle the "Delete" button click
    }
    }