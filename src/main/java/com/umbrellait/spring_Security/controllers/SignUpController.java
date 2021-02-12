package com.umbrellait.spring_Security.controllers;

import com.umbrellait.spring_Security.entities.User;
import com.umbrellait.spring_Security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @GetMapping("/sign-up")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String addUser(@ModelAttribute("userForm") /* @Valid */ User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "sign-up";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "sign-up";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "sign-up";
        }

        return "redirect:/";
    }
}
