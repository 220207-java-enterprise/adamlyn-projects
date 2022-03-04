package com.revature.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.requests.LoginRequest;
import com.revature.dtos.responses.Principal;
import com.revature.services.TokenService;
import com.revature.services.UserService;
import com.revature.util.exceptions.AuthenticationException;
import com.revature.util.exceptions.ForbiddenException;
import com.revature.util.exceptions.InvalidRequestException;
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

public class AuthServlet extends HttpServlet {

    private final TokenService tokenService;
    private final UserService userService;
    private final ObjectMapper mapper;

    private static Logger logger = LogManager.getLogger(AuthServlet.class);

    public AuthServlet(UserService userService, ObjectMapper mapper, TokenService tokenService) {
        this.userService = userService;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("AuthServlet #doPost invoked with args: " + Arrays.asList(req, resp));
        PrintWriter writer = resp.getWriter();

        try {

            LoginRequest loginRequest = mapper.readValue(req.getInputStream(), LoginRequest.class);
            logger.debug("AuthServlet #doPost created object: " + loginRequest);
            Principal principal = new Principal(userService.login(loginRequest));
            logger.debug("AuthServlet #doPost created object: " + principal);
            String payload = mapper.writeValueAsString(principal);
            logger.debug("AuthServlet #doPost created Token object");
            String token = tokenService.generateToken(principal);

            logger.debug("UserServlet #doPost returned successfully");
            resp.setHeader("Authorization", token);
            resp.setContentType("application/json");
            writer.write(payload);


//            // Stateful session management
//            HttpSession httpSession = req.getSession();
//            httpSession.setAttribute("authUser", principal);
//            resp.setContentType("application/json");
//            writer.write(payload);
//            System.out.println(principal);


        } catch (InvalidRequestException | DatabindException e) {
            resp.setStatus(400);
        } catch (AuthenticationException e) {
            logger.warn("AuthServlet #doPost No users found with provided Credentials");
            resp.setStatus(401); // UNAUTHORIZED (no user found with provided credentials)
        }catch (ForbiddenException e) {
            logger.warn("AuthServlet #doPost Unauthorized login request made by inactive user: ");
            resp.setStatus(403); // Forbidden (user isn't allowed to login to an inactive account)
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
