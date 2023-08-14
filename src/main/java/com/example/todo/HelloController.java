package com.example.todo;

import com.example.todo.model.DatabaseConnection;
import com.example.todo.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;


public class HelloController {
    @FXML
    private ListView<Task> tasks_list;
    @FXML
    private TextField name_text;
    @FXML
    private TextArea desc_text;
    @FXML
    private DatePicker deadline_date;
    private ObservableList<Task> tasks = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        //Database connection
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery ="SELECT * FROM task_list";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            while (queryOutput.next()){
                String tName = queryOutput.getString("name");
                String tDescription = queryOutput.getString("description");
                LocalDate tDate = queryOutput.getDate("final_date").toLocalDate();
                tasks.add(new Task(tName,tDescription,tDate));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        // Populate the ListView with task names
        tasks_list.setItems(tasks);
        tasks_list.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task p, boolean empty) {
                super.updateItem(p, empty);
                if (empty || p == null || p.getName() == null) {
                    setText("");
                } else {
                    setText(p.getName());
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
        Date date = Date.valueOf(deadline);
        Task selectedTask = tasks_list.getSelectionModel().getSelectedItem();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (selectedTask == null) {
            try{
                String connectQuery = "INSERT INTO `to-do-list`.`task_list` (`name`, `description`, `final_date`) VALUES (?,?,?)";
                PreparedStatement preparedStatement = connectDB.prepareStatement(connectQuery);
                preparedStatement.setString(1 , taskName);
                preparedStatement.setString(2 , taskDescription);
                preparedStatement.setDate(3 , date);

                preparedStatement.executeUpdate();
            }
            catch (Exception e){
                System.out.println(e);
            }

            Task newTask = new Task(taskName, taskDescription, deadline);
            tasks.add(newTask);
        } else {
            try{
                String connectQuery = "UPDATE `to-do-list`.`task_list` SET `description` = ?, `final_date` = ? WHERE (`name` = ?)";
                PreparedStatement preparedStatement = connectDB.prepareStatement(connectQuery);
                preparedStatement.setString(1 , taskDescription);
                preparedStatement.setDate(2 , date);
                preparedStatement.setString(3 , taskName);
                preparedStatement.executeUpdate();
            }
            catch(Exception e){
                System.out.println(e);
            }
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
        Task selectedTask = tasks_list.getSelectionModel().getSelectedItem();
        String taskName = selectedTask.getName();
        try{
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            String connectQuery = "DELETE FROM `to-do-list`.`task_list` WHERE (`name` = ?)";
            PreparedStatement preparedStatement = connectDB.prepareStatement(connectQuery);
            preparedStatement.setString(1 , taskName);
            preparedStatement.executeUpdate();

            Task selectionToRemove = tasks_list.getSelectionModel().getSelectedItem();
            tasks.remove(selectionToRemove);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void clearTextRefocus(){
        //Auto clear the user Typing textFields.
        name_text.clear();
        desc_text.clear();
        deadline_date.setValue(null);
        tasks_list.getSelectionModel().clearSelection();
        tasks_list.requestFocus(); //Place focus back on the list (stops focus glitch).
    }
    }