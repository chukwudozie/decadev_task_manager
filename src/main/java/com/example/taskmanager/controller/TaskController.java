package com.example.taskmanager.controller;

import com.example.taskmanager.models.Role;
import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.UserEntity;
import com.example.taskmanager.serviceImplementation.TaskServiceImpl;
import com.example.taskmanager.serviceImplementation.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
public class TaskController {

    private TaskServiceImpl taskService;
    private UserServiceImpl userService;

    @Autowired
    public TaskController(TaskServiceImpl taskService,UserServiceImpl userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @RequestMapping(value = "/newTask", method = RequestMethod.GET)
    public String getTaskCreatPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity)session.getAttribute("user");
        session.setAttribute("user",user);
        List<UserEntity> users =  userService.findAllUsers();
        model.addAttribute("users",users);
        model.addAttribute("userservice",userService);

        model.addAttribute("user",user);
        model.addAttribute("task",new Task());
        return "task_create";
    }

    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public String createTask(@ModelAttribute("task")Task task, HttpServletRequest request,
                             Model model, @RequestParam("userAssigned") Long id){
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        task.setUserAssigned(userService.getUserById(id));
        task.setAdmin(userService.getUserByRole(Role.ADMIN));

        log.info("assigned User Id: {}", id);

//        task.setUserAssigned();
        System.out.println(task.getUserAssigned().getId());
        model.addAttribute("user",user);
//        not working yet
        taskService.createNewTask(task,user);
        return "redirect:/adminHome";
    }

    @RequestMapping(value ="/viewTask",method = RequestMethod.GET)
    public String showAllTasks(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity)session.getAttribute("user");
        List<Task> allTasks = taskService.getAllTasks();
//        Long numberOfDays = taskService.getTaskById();
//        allTasks.forEach(task -> task.daysLeftUntilDeadline(task.getDeadline(),task));

        session.setAttribute("user",user);
        model.addAttribute("user",user);
        model.addAttribute("task",new Task());
        model.addAttribute("tasks", allTasks);
        return "all_tasks";
    }

    @RequestMapping(value = "/all_tasks",method = RequestMethod.GET)
    public String listAllTasks (Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity)session.getAttribute("user");
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("user",user);
        return "all_tasks";
    }

    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity)session.getAttribute("user");
        model.addAttribute("user",user);
        taskService.deleteTask(id);
        return "redirect:/all_tasks";
    }
    @GetMapping("/task/edit/{id}")
    public String showFormForEdit(@PathVariable Long id, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity)session.getAttribute("user");
        List<UserEntity> users =  userService.findAllUsers();
        model.addAttribute("users",users);
        model.addAttribute("task", taskService.getTaskById(id));
        log.info("Id of task to be edited: {}", id);
        model.addAttribute("user",user);
        return "task_edit";
    }

    @PostMapping("/task/edit/{id}")
    public String updateTask(@ModelAttribute("task") Task task, BindingResult bindingResult, @PathVariable Long id, Model model) {
        taskService.updateTask(id, task);
        return "redirect:/all_tasks";
    }
    @GetMapping("/task/mark-done/{id}")
    public String setTaskCompleted(@PathVariable Long id) {
        taskService.setTaskCompleted(id);
        return "redirect:/all_tasks";
    }

    @GetMapping("/task/unmark-done/{id}")
    public String setTaskNotCompleted(@PathVariable Long id) {
        taskService.setTaskNotCompleted(id);
        return "redirect:/all_tasks";
    }
}
