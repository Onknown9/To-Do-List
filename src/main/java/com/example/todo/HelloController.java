package com.example.todo;

import com.example.todo.model.Task;
import javafx.beans.value.ObservableValue;
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
    private TextField name_text;
    @FXML
    private TextField desc_text;
    @FXML
    private DatePicker deadline_date;
    private ObservableList<Task> tasks = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        // Populate the ListView with task names
        tasks.add(new Task("Name","Desc",LocalDate.now()));
        tasks_list.setItems(tasks);
        tasks_list.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task p, boolean empty) {
                super.updateItem(p, empty);
                if (empty || p == null || p.getName() == null) {
                    setText("");
                } else {
                    setText(p.getName());
                    /*//Change listener implemented.
                    tasks_list.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Task> observable, Task oldValue, Task newValue) -> {
                        if (tasks_list.isFocused()) {
                            desc_text.setText(newValue.getDescription());
                        }
                    });*/
                }
            }
        });
        // Set a listener for when a task is selected
        tasks_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Update the input fields with task information
                name_text.setText(newValue.getName());
                desc_text.setText(newValue.getDescription());
                deadline_date.setValue(newValue.getDeadline());
            } else {
                // Clear the input fields if no task is selected
                name_text.clear();
                desc_text.clear();
                deadline_date.setValue(null);
            }
        });
    }
    @FXML
    private void submitClick(ActionEvent event) {
        String taskName = name_text.getText();
        String taskDescription = desc_text.getText();
        LocalDate deadline = deadline_date.getValue();

        Task selectedTask = tasks_list.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            // No task selected, add a new task
            Task newTask = new Task(taskName, taskDescription, deadline);
            tasks.add(newTask);
        } else {
            // Task selected, update its properties

            selectedTask.setName(taskName);
            selectedTask.setDescription(taskDescription);
            selectedTask.setDeadline(deadline);

            tasks_list.refresh();
            clearTextRefocus();
        }
    }
    @FXML
    private void deleteClick(ActionEvent event) {
        Task selectionToRemove = tasks_list.getSelectionModel().getSelectedItem();
        tasks.remove(selectionToRemove);
    }
    public void clearTextRefocus(){
        //Auto clear the user Typing textFields.
        name_text.clear();
        desc_text.clear();
        deadline_date.setValue(null);

        tasks_list.requestFocus(); //Place focus back on the list (stops focus glitch).
    }
    }