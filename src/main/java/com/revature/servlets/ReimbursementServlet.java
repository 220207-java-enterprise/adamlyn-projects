package com.revature.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.requests.*;
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

        registerReimb(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setStatus(401);
            return;
        }


        try{
            ReimbUpdateRequest reimbUpdateRequest = mapper.readValue(req.getInputStream(), ReimbUpdateRequest.class);

            if (!reimbUpdateRequest.getStatus_id().equals("PENDING"))
                throw new InvalidRequestException("User cannot update resolved ticket");

            Principal requester = (Principal) session.getAttribute("authUser");
            if (requester.getRole().equals("MANAGER")) {
                reimbUpdateRequest.setResolver_id(requester.getId());
            }
            else {
                reimbUpdateRequest.setAuthor_id((requester.getId()));
            }

            reimbService.updateRemb(reimbUpdateRequest);

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
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setStatus(401);
            return;
        }

        ReimbRequest reimbRequest = mapper.readValue(req.getInputStream(), ReimbRequest.class);
        Principal requester = (Principal) session.getAttribute("authUser");
        List<ReimbursementResponse> myReimbs;

        if(requester.getRole().equals("USER") || requester.getRole().equals("ADMIN")) {
            System.out.println("NOT A MANAGER");
            if(reimbRequest.getStatus_id() != null){
                System.out.println("STATUS");
                myReimbs = reimbService.getUserReimbsByStatus(requester.getId(),reimbRequest.getStatus_id());
            }
            else if (reimbRequest.getType_id() != null){
                System.out.println("TYPE");
                myReimbs = reimbService.getUserReimbsByType(requester.getId(), reimbRequest.getType_id());
            }else {
                System.out.println("ELSE");
                myReimbs = reimbService.getUserReimbs(requester.getId());
            }
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
            System.out.println("MANAGER getall");
            myReimbs = reimbService.getUserReimbs(reimbRequest.getAuthor_id());
        }
        else if (reimbRequest.getAuthor_id() != null) {
            myReimbs = reimbService.getReimbsByHistory(reimbRequest.getAuthor_id());
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

        //Only registered users can make new reimbursemsents
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setStatus(401);
            return;
        }
        Principal requester = (Principal) session.getAttribute("authUser");
        try {

            NewReimbRequest newReimbRequest = mapper.readValue(req.getInputStream(),
                    NewReimbRequest.class);
            newReimbRequest.setAuthor_id(requester.getId());

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