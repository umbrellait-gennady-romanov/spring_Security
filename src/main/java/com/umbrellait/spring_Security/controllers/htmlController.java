package com.umbrellait.spring_Security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class htmlController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/sign-in")
    public String signIn() { return "sign-in"; }

    @GetMapping("/news")
    public String news() {
        return "news";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
