package com.example.mathlogic.Tasks;

public class TaskC {
    private static TaskC TaskCInstance = null;
    private TaskC(){}
    public TaskC getInstance(){
        if (TaskCInstance == null) TaskCInstance = new TaskC();

        return TaskCInstance;
    }
}
