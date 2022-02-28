package com.revature.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.ConfirmUser;
import com.revature.dtos.NewUserRequest;
import com.revature.dtos.ResourceCreationResponse;
import com.revature.dtos.responses.Principal;
import com.revature.dtos.responses.UserResponse;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.UserService;
import com.revature.util.exceptions.InvalidRequestException;
import com.revature.util.exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class UserServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;

    public UserServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setStatus(401);
            return;
        }

        Principal requester = (Principal) session.getAttribute("authUser");
        System.out.println(requester.getRole() + " " );

        if (!requester.getRole().equals("ADMIN")) {
            resp.setStatus(403); // FORBIDDEN
            return;
        }

        List<UserResponse> users = userService.getAllEmployees();
        System.out.println(users);
        String payload = mapper.writeValueAsString(users);
        resp.setContentType("application/json");
        resp.getWriter().write(payload);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] reqFrags = req.getRequestURI().split("/");
        if (reqFrags.length == 4 && reqFrags[3].equals("registerUser")) {
            registerUser(req, resp);
            return; // necessary, otherwise we end up doing more work than was requested
        }
        if(reqFrags.length == 4 && reqFrags[3].equals("registerManager")) {
            registerManager(req, resp);
            return;
        }
        resp.setStatus(404);
}

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setStatus(401);
            return;
        }

        Principal requester = (Principal) session.getAttribute("authUser");
        System.out.println(requester.getRole() + " " );

        if (!requester.getRole().equals("ADMIN")) {
            resp.setStatus(403); // FORBIDDEN
        }
        String[] reqFrags = req.getRequestURI().split("/");
        if (reqFrags.length == 4 && reqFrags[3].equals("confirmUser")) {
            confirmUser(req, resp);
            return; // necessary, otherwise we end up doing more work than was requested
        }
        if (reqFrags.length == 4 && reqFrags[3].equals("deleteUser")) {
            deleteUser(req, resp);
            return; // necessary, otherwise we end up doing more work than was requested
        }

    }

    protected void deleteUser (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ConfirmUser confirmandi = mapper.readValue(req.getInputStream(), ConfirmUser.class);

             userService.deleteUser(confirmandi);

            resp.setStatus(204);
            resp.setContentType("application/json");

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }

    protected void confirmUser (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try{
            ConfirmUser confirmandi = mapper.readValue(req.getInputStream(), ConfirmUser.class);

            userService.confirmUser(confirmandi);

            resp.setStatus(204);
            resp.setContentType("application/json");
        }catch (Exception e){
            e.printStackTrace();
            resp.setStatus(500);
        }

    }

    protected void registerUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        PrintWriter respWriter = resp.getWriter();

        try {

            NewUserRequest newUserRequest = mapper.readValue(req.getInputStream(), NewUserRequest.class);
            UserRole role = new UserRole("2", "USER");
            newUserRequest.setRole(role);
            User newUser = userService.register(newUserRequest);

            resp.setStatus(201);
            resp.setContentType("application/json");
            String payload = mapper.writeValueAsString(new ResourceCreationResponse(newUser.getUser_id()));
            respWriter.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            resp.setStatus(400); // BAD REQUEST
        } catch (ResourceConflictException e) {
            resp.setStatus(409); // CONFLICT
        } catch (Exception e) {
            e.printStackTrace(); // include for debugging purposes; ideally log it to a file
            resp.setStatus(500);
        }
    }

    protected void registerManager(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter respWriter = resp.getWriter();
        try {

            NewUserRequest newUserRequest = mapper.readValue(req.getInputStream(), NewUserRequest.class);
            System.out.println(newUserRequest+ " from managerservlet");
            UserRole role = new UserRole("3", "Manager");
            newUserRequest.setRole(role);
            User newUser = userService.register(newUserRequest);

            resp.setStatus(201);
            resp.setContentType("application/json");
            String payload = mapper.writeValueAsString(new ResourceCreationResponse(newUser.getUser_id()));
            respWriter.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            resp.setStatus(400); // BAD REQUEST
        } catch (ResourceConflictException e) {
            resp.setStatus(409); // CONFLICT
        } catch (Exception e) {
            e.printStackTrace(); // include for debugging purposes; ideally log it to a file
            resp.setStatus(500);
        }
    }
}

