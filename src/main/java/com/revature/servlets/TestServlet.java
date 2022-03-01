package com.revature.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.requests.NewUserRequest;
import com.revature.models.User;
import com.revature.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/test2") // Annotation-based declarative registration (dependency wiring does not work!)
public class TestServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;

    public TestServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewUserRequest newUserRequest = mapper.readValue(req.getInputStream(), NewUserRequest.class);
        User newUser = userService.register(newUserRequest);
        resp.getWriter().write("<h1>/" + newUserRequest +"</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NewUserRequest newUserRequest = mapper.readValue(req.getInputStream(), NewUserRequest.class);
            resp.getWriter().write("<h1>/" + newUserRequest + "</h1>");
            User newUser = userService.register(newUserRequest);
            resp.setStatus(201);

        }catch(Exception e){
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
