package com.example.taskmanager.services;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.UserEntity;

import java.util.List;

public interface TaskService {

    Task createNewTask(Task task,UserEntity user) ;

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    void deleteTask(Long id);

    void updateTask(Long id, Task updatedTask);

    void setTaskCompleted(Long id);

    void setTaskNotCompleted(Long id);

}
