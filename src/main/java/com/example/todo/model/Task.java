package com.example.todo.model;

import java.time.LocalDate;

public class Task {
    private String name;
    private String description;
    private LocalDate deadline;
    public Task(){}
    public Task(String name, String desc, LocalDate deadline){
        this.name = name;
        this.description = desc;
        this.deadline = deadline;
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
