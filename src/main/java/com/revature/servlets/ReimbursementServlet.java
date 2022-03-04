package com.revature.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.requests.*;
import com.revature.dtos.responses.Principal;
import com.revature.dtos.responses.ReimbursementResponse;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import com.revature.services.TokenService;
import com.revature.util.exceptions.InvalidRequestException;
import com.revature.util.exceptions.ResourceConflictException;
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

public class ReimbursementServlet extends HttpServlet {

    private final ReimbursementService reimbService;
    private final ObjectMapper mapper;
    private final TokenService tokenService;

    private static Logger logger = LogManager.getLogger(AuthServlet.class);

    public ReimbursementServlet(ReimbursementService reimbService, ObjectMapper mapper, TokenService tokenService) {
        this.reimbService = reimbService;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("ReimbursementServlet #doPost invoked with args: " + Arrays.asList(req, resp));
        registerReimb(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("ReimbursementServlet #doPut invoked with args: " + Arrays.asList(req, resp));
        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
        logger.debug("ReimbursementServlet #doPut created new object " + requester);
        if (requester == null) {
            logger.debug("ReimbursementServlet #doPut No user was logged in");
            resp.setStatus(401);
            return;
        }

        try{
            ReimbUpdateRequest reimbUpdateRequest = mapper.readValue(req.getInputStream(), ReimbUpdateRequest.class);
            logger.debug("ReimbursementServlet #doPut created new object " + reimbUpdateRequest);
            if (requester.getRole().equals("MANAGER")) {
                reimbUpdateRequest.setResolver_id(requester.getId());
            }
            else {
                reimbUpdateRequest.setAuthor_id((requester.getId()));
            }

            reimbService.updateReimb(reimbUpdateRequest);

            logger.debug("ReimbursementServlet #doPut returned successfully");
            resp.setStatus(204);
            resp.setContentType("application/json");

        }catch(InvalidRequestException e){
            resp.setStatus(405);
        } catch (Exception e){
            e.printStackTrace();
            resp.setStatus(500);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("ReimbursementServlet #doGet invoked with args: " + Arrays.asList(req, resp));
        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
        logger.debug("ReimbursementServlet #doGet created new object: " + requester);
        if (requester == null) {
            logger.debug("ReimbursementServlet #doPut No user was logged in");
            resp.setStatus(401);
            return;
        }

        ReimbRequest reimbRequest = mapper.readValue(req.getInputStream(), ReimbRequest.class);

        logger.debug("ReimbursementServlet #doGet created new object: " + reimbRequest);
        List<ReimbursementResponse> myReimbs;

        if(requester.getRole().equals("USER") || requester.getRole().equals("ADMIN")) {
            logger.debug("ReimbursementServlet #doGet confirmed user is not a manager, CONTINUING." );

            if(reimbRequest.getStatus_id() != null){
                logger.debug("ReimbursementServlet #doGet filtering by status." );
                myReimbs = reimbService.getUserReimbsByStatus(requester.getId(),reimbRequest.getStatus_id());
            }
            else if (reimbRequest.getType_id() != null){
                logger.debug("ReimbursementServlet #doGet filtering by status." );
                myReimbs = reimbService.getUserReimbsByType(requester.getId(), reimbRequest.getType_id());
            }else {
                logger.debug("ReimbursementServlet #doGet filtering by type." );
                myReimbs = reimbService.getPendingReimbs(requester.getId());
            }
        }
        else if (!requester.getRole().equals("MANAGER")) {
            logger.debug("ReimbursementServlet #doGet confirmed user is not a manager, FORBIDDEN OPERATION." );
            resp.setStatus(403); // FORBIDDEN
            return;
        }
        else if(reimbRequest.getResolver_id() != null){
            logger.debug("ReimbursementServlet #doGet filtering by history." );
            myReimbs = reimbService.getReimbsByHistory(reimbRequest.getResolver_id());
        }
        else if(reimbRequest.getStatus_id() != null){
            logger.debug("ReimbursementServlet #doGet filtering by status." );
            myReimbs = reimbService.getReimbsByStatus(reimbRequest.getStatus_id());
        }
        else if (reimbRequest.getType_id() != null){
            logger.debug("ReimbursementServlet #doGet filtering by type." );
            myReimbs = reimbService.getReimbByType(reimbRequest.getType_id());
        }
//        else if (reimbRequest.getReimb_id() != null) {
//            logger.debug("ReimbursementServlet #doGet getting all reimbursements from User." );
//            myReimbs = reimbService.getUserReimbs(reimbRequest.getAuthor_id());
//        }
        else if (reimbRequest.getAuthor_id() != null) {
            logger.debug("ReimbursementServlet #doGet filtering by user." );
            myReimbs = reimbService.getUserReimbs(reimbRequest.getAuthor_id());
        }
        else{
            logger.debug("ReimbursementServlet #doGet getting all reimbursements." );
            myReimbs = reimbService.getAllReimbs();
        }

        logger.debug("ReimbursementServlet #doGet returned successfully." );
        String payload = mapper.writeValueAsString(myReimbs);
        resp.setContentType("application/json");
        resp.getWriter().write(payload);
    }

    protected void registerReimb(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        PrintWriter respWriter = resp.getWriter();

        //Only registered users can make new reimbursemsents

        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
        logger.debug("ReimbursementServlet #doPut created new object: " + requester);
        if (requester == null) {
            logger.debug("ReimbursementServlet #doPut No user was logged in");
            resp.setStatus(401);
            return;
        }
        try {

            NewReimbRequest newReimbRequest = mapper.readValue(req.getInputStream(),
                    NewReimbRequest.class);
            logger.debug("ReimbursementServlet #doPut created new object: " + newReimbRequest);
            newReimbRequest.setAuthor_id(requester.getId());

            Reimbursement newReimb = reimbService.newReimbursement(newReimbRequest);
            logger.debug("ReimbursementServlet #doPut created new object: " + newReimbRequest);

            logger.debug("ReimbursementServlet #doPut returned successfully: ");
            resp.setStatus(201);
            resp.setContentType("application/json");
            String payload = mapper.writeValueAsString(new ResourceCreationResponse(newReimb.getReimb_id()));
            respWriter.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            logger.debug("UserServlet #doPost server error ");
            resp.setStatus(400); // BAD REQUEST
        } catch (ResourceConflictException e) {
            logger.debug("UserServlet #doPost Conflict error ");
            resp.setStatus(409); // CONFLICT
        } catch (Exception e) {
            logger.debug("UserServlet #doPost server error ");
            resp.setStatus(500);
        }
    }

}