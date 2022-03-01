package com.revature.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.*;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import com.revature.servlets.*;
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
        UserRoleDAO userRoleDAO = new UserRoleDAO();

        ReimbursementDAO reimbDAO = new ReimbursementDAO();
        ReimbursementTypeDAO reimbTypeDAO = new ReimbursementTypeDAO();
        ReimbursementStatusDAO reimbStatusDAO = new ReimbursementStatusDAO();

        UserService userService = new UserService(userDAO, userRoleDAO);
        ReimbursementService reimbService = new ReimbursementService(reimbDAO,
                reimbTypeDAO,reimbStatusDAO, userDAO);
        TestServlet testServlet = new TestServlet(userService, mapper);
        UserServlet userServlet = new UserServlet(userService, mapper);
        AuthServlet authServlet = new AuthServlet(userService, mapper);
        ReimbursementServlet reimbursementServlet = new ReimbursementServlet(reimbService, mapper);

        // Programmatic Servlet Registration
        ServletContext context = sce.getServletContext();
        context.addServlet("UserServlet", userServlet).addMapping("/users/*");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
        context.addServlet("TestServlet", testServlet).addMapping("/test/*");
        context.addServlet("ReimbursementServlet", reimbursementServlet).addMapping("/reimbursements/*");


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down Foundations web application");
    }

}
