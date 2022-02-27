package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.UserService;

import javax.servlet.http.HttpServlet;

public class AuthServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;

    public AuthServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }
}
