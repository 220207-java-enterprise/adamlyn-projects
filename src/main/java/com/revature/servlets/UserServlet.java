package com.revature.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.requests.UserUpdateRequest;
import com.revature.dtos.requests.NewUserRequest;
import com.revature.dtos.requests.ResourceCreationResponse;
import com.revature.dtos.responses.Principal;
import com.revature.dtos.responses.UserResponse;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.exceptions.ForbiddenException;
import com.revature.util.exceptions.InvalidRequestException;
import com.revature.util.exceptions.ResourceConflictException;

import javax.servlet.ServletException;
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

    // Admin get all users
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

    //Anyone register new user/manager
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        register(req, resp);
    }

    // Admin only update/approve/soft delete user
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
        updateUser(req, resp);
    }


    protected void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try{
            UserUpdateRequest updateUser = mapper.readValue(req.getInputStream(), UserUpdateRequest.class);

            userService.updateUser(updateUser);

            resp.setStatus(204);
            resp.setContentType("application/json");

        }catch(InvalidRequestException e){
            resp.setStatus(405);
        } catch (Exception e){
            e.printStackTrace();
            resp.setStatus(500);
        }

    }

    // User can register as manager or user
    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        PrintWriter respWriter = resp.getWriter();

        try {

            NewUserRequest newUserRequest = mapper.readValue(req.getInputStream(), NewUserRequest.class);
            System.out.println(newUserRequest);
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

