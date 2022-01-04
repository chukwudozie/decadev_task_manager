package com.example.taskmanager.controller;

import com.example.taskmanager.models.Role;
import com.example.taskmanager.models.UserEntity;
import com.example.taskmanager.serviceImplementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegistrationPage(HttpServletRequest request, Model model){
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showIndexPage(HttpServletRequest request, Model model){
        model.addAttribute("user", new UserEntity());
        return "index";
    }

    @RequestMapping(value = "/adminHome", method = RequestMethod.GET)
    public ModelAndView showAdminDashBoard (HttpServletRequest request){

        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null){
            ModelAndView model = new ModelAndView("index");
            return model;
        }
        ModelAndView model = new ModelAndView("adminHome");
        session.setAttribute("user",user);
        model.addObject("user",user);
        return model;
    }

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public ModelAndView showUserDashBoard (HttpServletRequest request){
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user == null){
            ModelAndView model = new ModelAndView("index");
            return model;
        }
        ModelAndView model = new ModelAndView("userHome");
        session.setAttribute("user",user);
        model.addObject("user",user);
        return model;
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String addNewUser(HttpServletRequest request, @ModelAttribute("user")UserEntity user){

        HttpSession session = request.getSession();
        userService.createNewUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public String loginUser(@ModelAttribute("user")UserEntity user,HttpServletRequest request){
        UserEntity userFromDb = userService.loginUser(user.getEmail(),user.getPassword());

        request.setAttribute("userLogin", new UserEntity());

        HttpSession session = request.getSession();
        if(userFromDb != null){
            session.setAttribute("user",userService.getUserByEmail(user.getEmail()));
            if(userFromDb.getUserRole().equals(Role.ADMIN)){
                return "redirect:/adminHome";
            }
            if(userFromDb.getUserRole().equals(Role.DEVELOPER)) return "redirect:/userHome";
            if(userFromDb.getUserRole().equals(Role.PROJECT_MANAGER)) return "redirect:/managerHome";
        }
        return "redirect:/";

    }





}
