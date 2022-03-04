package com.revature.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.requests.UserUpdateRequest;
import com.revature.dtos.requests.NewUserRequest;
import com.revature.dtos.requests.ResourceCreationResponse;
import com.revature.dtos.responses.Principal;
import com.revature.dtos.responses.UserResponse;
import com.revature.models.User;
import com.revature.services.TokenService;
import com.revature.services.UserService;
import com.revature.util.exceptions.ForbiddenException;
import com.revature.util.exceptions.InvalidRequestException;
import com.revature.util.exceptions.ResourceConflictException;
import jdk.nashorn.internal.parser.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;


public class UserServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;
    private final TokenService tokenService;

    private static Logger logger = LogManager.getLogger(UserServlet.class);

    public UserServlet(UserService userService, ObjectMapper mapper, TokenService tokenService) {
        this.userService = userService;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    // Admin get all users
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.debug("UserServlet #doGet invoked with args: " + Arrays.asList(req, resp));
//        HttpSession session = req.getSession(false);
//        if (session == null) {
//            resp.setStatus(401);
//            return;
//        }
//        Principal requester = (Principal) session.getAttribute("authUser");
        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
        logger.debug("UserServlet #doGet makes object: " + requester);
        if (requester == null) {
            resp.setStatus(401);
            return;
        }

        if (!requester.getRole().equals("ADMIN")) {
            logger.warn("Unauthorized request made by user: " + requester.getUsername());
            resp.setStatus(403); // FORBIDDEN
            return;
        }

        List<UserResponse> users = userService.getAllEmployees();
        String payload = mapper.writeValueAsString(users);
        logger.debug("UserServlet #doGet returned successfully");
        resp.setContentType("application/json");
        resp.getWriter().write(payload);
    }

    //Anyone can register new user/manager
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("UserServlet #doPost invoked with args: " + Arrays.asList(req, resp));
        register(req, resp);
    }

    // Admin only update/approve/soft delete user
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("UserServlet #doPut invoked with args: " + Arrays.asList(req, resp));
        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
        if (requester == null) {
            resp.setStatus(401);
            return;
        }
        if (!requester.getRole().equals("ADMIN")) {
            logger.warn("Unauthorized request made by user: " + requester.getUsername());
            resp.setStatus(403); // FORBIDDEN
            return;
        }
        updateUser(req, resp);
    }

    // Admin only update/approve/soft delete user
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("UserServlet #doDelete invoked with args: " + Arrays.asList(req, resp));
        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
        if (requester == null) {
            resp.setStatus(401);
            return;
        }
        if (!requester.getRole().equals("ADMIN")) {
            logger.warn("Unauthorized request made by user: " + requester.getUsername());
            resp.setStatus(403); // FORBIDDEN
            return;
        }
        deleteUser(req, resp);
    }

    protected void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try{
            UserUpdateRequest updateUser = mapper.readValue(req.getInputStream(), UserUpdateRequest.class);

            userService.updateUser(updateUser);

            resp.setStatus(204);
            resp.setContentType("application/json");

        }catch(InvalidRequestException e) {
            resp.setStatus(405);
        }catch(ResourceConflictException e){
            resp.setStatus(409);
        } catch (Exception e){
            e.printStackTrace();
            resp.setStatus(500);
        }

    }

    protected void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserUpdateRequest updateUser = mapper.readValue(req.getInputStream(), UserUpdateRequest.class);
            updateUser.setActive(false);

            userService.updateUser(updateUser);

            resp.setStatus(204);
            resp.setContentType("application/json");

        } catch (InvalidRequestException e) {
            resp.setStatus(405);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }

    // User can register as manager or user
    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        PrintWriter respWriter = resp.getWriter();

        try {

            NewUserRequest newUserRequest = mapper.readValue(req.getInputStream(), NewUserRequest.class);
            User newUser = userService.register(newUserRequest);

            resp.setStatus(201);
            resp.setContentType("application/json");
            String payload = mapper.writeValueAsString(new ResourceCreationResponse(newUser.getUser_id()));
            respWriter.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            logger.debug("UserServlet #doPost server error ");
            resp.setStatus(400); // BAD REQUEST
        } catch (ResourceConflictException e) {
            logger.debug("UserServlet #doPost Conflict error ");
            resp.setStatus(409); // CONFLICT
        } catch (Exception e) {
            logger.debug("UserServlet #doPost server error "); // include for debugging purposes; ideally log it to a file
            resp.setStatus(500);
        }
    }
}

