package com.revature.spring.controllers;


import com.revature.spring.dtos.requests.NewUserRequest;
import com.revature.spring.dtos.requests.UserUpdateRequest;
import com.revature.spring.dtos.responses.UserResponse;
import com.revature.spring.models.User;
import com.revature.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // Admin get all users
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public HashMap<String, Object> getAllUsers() {
        HashMap<String, Object> userList = new HashMap<String, Object>();
        List<UserResponse> myUsers = userService.getAllEmployees();
        userList.put("endpoint", " /user");
        userList.put("status", "UP");
        userList.put("providedValues", myUsers);
        return userList;
    }


    //TODO security
    // Admin update user
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(produces = "application/json", consumes = "application/json")
    public void update(@RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(produces = "application/json", consumes = "application/json")
    public void delete(@RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
    }


    // Register as User/Manager
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public HashMap<String, Object> register(@RequestBody NewUserRequest newUserRequest){
        HashMap<String, Object> userList = new HashMap<String, Object>();

        User newUser = userService.register(newUserRequest);
        UserResponse userResponse = new UserResponse(newUser);

        userList.put("endpoint", " /register");
        userList.put("status", "UP");
        userList.put("providedValues", userResponse);
        System.out.println(userList);
        System.out.println(newUser);

        return userList;
    }
}
