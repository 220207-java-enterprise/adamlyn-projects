package com.revature.spring.controllers;


import com.revature.spring.models.User;
import com.revature.spring.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

@Transactional
@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepo;

    @Autowired
    public UserController(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @GetMapping
    public String userInterface() {
        return "we did it";
    }

    @GetMapping(value = "test2")
    public String test2() {
        return "we did another it";
    }

    @GetMapping(value = "test3")
    public HashMap<String, Object> test3(@RequestParam String id) {
        HashMap<String, Object> userList = new HashMap<String, Object>();
        userList.put("endpoint", " /test3");
        userList.put("status", "UP");
        userList.put("providedValues", Arrays.asList(id));
        return userList;
    }

    @GetMapping(value = "test4")
    public HashMap<String, Object> test4(@RequestHeader(value = "Authorization", required = false) String token) {
        HashMap<String, Object> userList = new HashMap<String, Object>();
        userList.put("endpoint", " /test4");
        userList.put("status", "UP");
        userList.put("providedValues", token);
        return userList;
    }@

    @GetMapping(value = "test5/{something}")
    public HashMap<String, Object> test5(@PathVariable String something) {
        HashMap<String, Object> userList = new HashMap<String, Object>();
        userList.put("endpoint", " /test5");
        userList.put("status", "UP");
        userList.put("providedValues", something);
        return userList;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "test6", produces = "application/json", consumes = "application/json")
    public HashMap<String, Object> test6(@RequestBody User userData){
        HashMap<String, Object> userList = new HashMap<String, Object>();
        System.out.println(userList);
        userList.put("endpoint", " /test5");
        userList.put("status", "UP");
        userList.put("providedValues", userData);
        userData.setId(UUID.randomUUID().toString());
        System.out.println(userList);
        userRepo.save(userData);
        return userList;
    }
}
