package com.revature.spring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class DummyController {

    @GetMapping
    public String test() {
        return "test endpoint works! :)";
    }

}
