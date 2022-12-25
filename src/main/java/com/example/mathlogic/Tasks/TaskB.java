package com.example.mathlogic.Tasks;

public class TaskB {
    private static TaskB taskBInstance = null;
    private TaskB(){}
    public TaskB getInstance(){
        if (taskBInstance == null) taskBInstance = new TaskB();

        return taskBInstance;
    }
}
