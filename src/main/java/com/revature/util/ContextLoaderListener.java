package com.revature.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.*;
import com.revature.services.ReimbursementService;
import com.revature.services.TokenService;
import com.revature.services.UserService;
import com.revature.servlets.AuthServlet;
import com.revature.servlets.ReimbursementServlet;
import com.revature.servlets.TestServlet;
import com.revature.servlets.UserServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    private static Logger logger = LogManager.getLogger(ContextLoaderListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("Initializing Foundations web application");

        ObjectMapper mapper = new ObjectMapper();
        JwtConfig jwtConfig = new JwtConfig();

        UserDAO userDAO = new UserDAO();
        UserRoleDAO userRoleDAO = new UserRoleDAO();

        ReimbursementDAO reimbDAO = new ReimbursementDAO();
        ReimbursementTypeDAO reimbTypeDAO = new ReimbursementTypeDAO();
        ReimbursementStatusDAO reimbStatusDAO = new ReimbursementStatusDAO();


        TokenService tokenService = new TokenService(jwtConfig);
        UserService userService = new UserService(userDAO, userRoleDAO);
        ReimbursementService reimbService = new ReimbursementService(reimbDAO,
                reimbTypeDAO,reimbStatusDAO, userDAO);


        TestServlet testServlet = new TestServlet(userService, mapper);
        UserServlet userServlet = new UserServlet(userService, mapper, tokenService);
        AuthServlet authServlet = new AuthServlet(userService, mapper, tokenService);
        ReimbursementServlet reimbursementServlet = new ReimbursementServlet(reimbService, mapper, tokenService);

        // Programmatic Servlet Registration
        ServletContext context = sce.getServletContext();
        context.addServlet("UserServlet", userServlet).addMapping("/users/*");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
        context.addServlet("TestServlet", testServlet).addMapping("/test/*");
        context.addServlet("ReimbursementServlet", reimbursementServlet).addMapping("/reimbursements/*");


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("Shutting down Foundations web application");
    }

}
