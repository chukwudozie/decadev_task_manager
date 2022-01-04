package com.example.taskmanager.serviceImplementation;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.UserEntity;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Task createNewTask(Task task,UserEntity user) {

      if(!taskRepository.existsById(task.getId())){
//          task.setAdmin( userRepository.findUserEntityByEmail(user.getEmail()));
          return taskRepository.save(task);
      }
      return null;
//        return !taskRepository.existsById(task.getId())?taskRepository.save(task): null;
    }

    @Override
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id){
        return taskRepository.getById(id);
    }

    @Override
    public void deleteTask(Long id){taskRepository.deleteById(id);}

    @Override
    public void updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.getById(id);
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setUserAssigned(updatedTask.getUserAssigned());
        task.setDeadline(updatedTask.getDeadline());
        taskRepository.save(task);
    }

    @Override
    public void setTaskCompleted(Long id) {
        Task task = taskRepository.getById(id);
        task.setActive(true);
        taskRepository.save(task);
    }

    @Override
    public void setTaskNotCompleted(Long id) {
        Task task = taskRepository.getById(id);
        task.setActive(false);
        taskRepository.save(task);
    }

}
