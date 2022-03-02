package com.revature.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.requests.LoginRequest;
import com.revature.dtos.responses.Principal;
import com.revature.services.UserService;
import com.revature.util.exceptions.AuthenticationException;
import com.revature.util.exceptions.ForbiddenException;
import com.revature.util.exceptions.InvalidRequestException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;

    public AuthServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        try {

            LoginRequest loginRequest = mapper.readValue(req.getInputStream(), LoginRequest.class);
            Principal principal = new Principal(userService.login(loginRequest));
            String payload = mapper.writeValueAsString(principal);

            // Stateful session management
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("authUser", principal);
            resp.setContentType("application/json");
            writer.write(payload);
            System.out.println(principal);


        } catch (InvalidRequestException | DatabindException e) {
            resp.setStatus(400);
        } catch (AuthenticationException e) {
            resp.setStatus(401); // UNAUTHORIZED (no user found with provided credentials)
        }catch (ForbiddenException e) {
            resp.setStatus(403); // Forbidden (user isn't allowed to login to an inactive account)
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
