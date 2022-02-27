package com.revature.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.UserDAO;
import com.revature.daos.UserDAO;
import com.revature.services.UserService;
import com.revature.servlets.*;
import com.revature.services.UserService;
import com.revature.servlets.AuthServlet;
import com.revature.servlets.UserServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing Foundations web application");

        ObjectMapper mapper = new ObjectMapper();


        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        TestServlet testServlet = new TestServlet(userService, mapper);
        UserServlet userServlet = new UserServlet(userService, mapper);
        AuthServlet authServlet = new AuthServlet(userService, mapper);

        // Programmatic Servlet Registration
        ServletContext context = sce.getServletContext();
        context.addServlet("UserServlet", userServlet).addMapping("/users/*");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
        context.addServlet("TestServlet", testServlet).addMapping("/test/*");


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down Foundations web application");
    }

}
