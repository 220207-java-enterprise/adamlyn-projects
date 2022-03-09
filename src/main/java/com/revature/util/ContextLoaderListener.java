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
import config.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.annotation.Annotation;

public class ContextLoaderListener implements ServletContextListener {

    private static Logger logger = LogManager.getLogger(ContextLoaderListener.class);

    //Spring ApplicationContext (IoC Container)
    ApplicationContext appContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("Initializing Foundations web application");

        appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        ObjectMapper mapper = new ObjectMapper();


//        JwtConfig jwtConfig = new JwtConfig();
//
        UserDAO userDAO = new UserDAO();
//        UserRoleDAO userRoleDAO = new UserRoleDAO();
//
        ReimbursementDAO reimbDAO = new ReimbursementDAO();
        ReimbursementTypeDAO reimbTypeDAO = new ReimbursementTypeDAO();
        ReimbursementStatusDAO reimbStatusDAO = new ReimbursementStatusDAO();
//
//
//        TokenService tokenService = new TokenService(jwtConfig);
//        UserService userService = new UserService(userDAO, userRoleDAO);
        ReimbursementService reimbService = new ReimbursementService(reimbDAO,
                reimbTypeDAO,reimbStatusDAO, userDAO);

        TokenService tokenService = appContext.getBean(TokenService.class);
        UserService userService = appContext.getBean(UserService.class);


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

        ((ConfigurableWebApplicationContext)this.appContext).close();
    }


}
