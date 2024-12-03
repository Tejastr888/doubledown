package com.red.doubledown.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping
    public String home(){
        return "Home";
    }

    @GetMapping("/api")
    public String secure(){
        return "Secured";
    }
}
