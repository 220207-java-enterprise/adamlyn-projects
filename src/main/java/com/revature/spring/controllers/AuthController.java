package com.revature.spring.controllers;


import com.revature.spring.dtos.requests.LoginRequest;
import com.revature.spring.dtos.responses.Principal;
import com.revature.spring.services.TokenService;
import com.revature.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService, TokenService tokenService){
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "test6", produces = "application/json", consumes = "application/json")
    public String test6(@RequestBody LoginRequest loginRequest){
        System.out.println(loginRequest);

        Principal principal = new Principal(userService.login(loginRequest));
        System.out.println(principal);

        String token = tokenService.generateToken(principal);
        return token;
    }

}
