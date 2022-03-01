package com.revature.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.requests.NewReimbRequest;
import com.revature.dtos.requests.ReimbRequest;
import com.revature.dtos.requests.ResourceCreationResponse;
import com.revature.dtos.responses.Principal;
import com.revature.dtos.responses.ReimbursementResponse;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
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

public class ReimbursementServlet extends HttpServlet {
    private final ReimbursementService reimbService;
    private final ObjectMapper mapper;

    public ReimbursementServlet(ReimbursementService reimbService, ObjectMapper mapper) {
        this.reimbService = reimbService;
        this.mapper = mapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Only registered users can make new reimbursemsents
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setStatus(401);
            return;
        }

        registerReimb(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setStatus(401);
            return;
        }
        ReimbRequest reimbRequest = mapper.readValue(req.getInputStream(), ReimbRequest.class);
        Principal requester = (Principal) session.getAttribute("authUser");
        List<ReimbursementResponse> myReimbs;

        if(requester.getRole().equals("USER") || requester.getRole().equals("ADMIN")) {
            myReimbs = reimbService.getUserReimbs(requester.getId());
        }
        else if (!requester.getRole().equals("MANAGER")) {
            resp.setStatus(403); // FORBIDDEN
            return;
        }
        else if(reimbRequest.getStatus_id() != null){
            myReimbs = reimbService.getReimbsByStatus(reimbRequest.getStatus_id());
        }
        else if (reimbRequest.getType_id() != null){
            myReimbs = reimbService.getReimbByType(reimbRequest.getType_id());
        }
        else if (reimbRequest.getReimb_id() != null) {
            myReimbs = reimbService.getUserReimbs(reimbRequest.getAuthor_id());
        }
        else{
            myReimbs = reimbService.getAllReimbs();
        }

        String payload = mapper.writeValueAsString(myReimbs);
        resp.setContentType("application/json");
        resp.getWriter().write(payload);
    }

    protected void registerReimb(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        PrintWriter respWriter = resp.getWriter();
        try {

            NewReimbRequest newReimbRequest = mapper.readValue(req.getInputStream(),
                    NewReimbRequest.class);
            Reimbursement newReimb = reimbService.newReimbursement(newReimbRequest);

            resp.setStatus(201);
            resp.setContentType("application/json");
            String payload = mapper.writeValueAsString(new ResourceCreationResponse(newReimb.getReimb_id()));
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