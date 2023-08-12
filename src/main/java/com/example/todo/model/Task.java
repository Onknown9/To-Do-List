package com.example.todo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.util.List;

public class Task {
    private Long id;
    private String name;
    private String description;
    private LocalDate deadline;
    private ObservableList<Task> tasks = FXCollections.observableArrayList();
    public Task(){}
    public Task(String name, String desc, LocalDate deadline){
        this.name = name;
        this.description = desc;
        this.deadline = deadline;
    }
    public void addToList(Task a){
        tasks.add(a);
    }
    public ObservableList<Task> returnList(){
        return tasks;
    }
    public List<String> returnNames(){
        List<String> names = FXCollections.observableArrayList();
        for(Task a : tasks){
            names.add(a.getName());
        }
        return names;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name=name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description=description;
    }
    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline=deadline;
    }
}
