package com.revature.services;

import com.revature.daos.*;
import com.revature.dtos.requests.NewReimbursementRequest;
import com.revature.dtos.requests.ReimbUpdateRequest;
import com.revature.dtos.responses.ReimbursementResponse;
import com.revature.models.*;
import com.revature.util.exceptions.InvalidRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReimbursementService {

    private ReimbursementDAO reimbursementDAO;
    private ReimbursementTypeDAO reimbursementTypeDAO;
    private ReimbursementStatusDAO reimbursementStatusDAO;
    private UserDAO userDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAO, ReimbursementTypeDAO reimbursementTypeDAO,
                                ReimbursementStatusDAO reimbursementStatusDAO, UserDAO userDAO){

        this.reimbursementDAO = reimbursementDAO;
        this.reimbursementTypeDAO = reimbursementTypeDAO;
        this.reimbursementStatusDAO = reimbursementStatusDAO;
    }
    // Any new reimbursement
    public Reimbursement newReimbursement(NewReimbursementRequest reimbursementRequest) throws IOException{
        Reimbursement newRmb = reimbursementRequest.extractReimb();

        newRmb.setAmount(convertAmount(newRmb.getAmount()));
        newRmb.setReimb_id(UUID.randomUUID().toString());


        ReimbursementType myType = reimbursementTypeDAO.getById(reimbursementRequest.getType_id());
        newRmb.setType_id(myType);
        ReimbursementStatus myStatus = reimbursementStatusDAO.getById("1");     // Pending
        newRmb.setStatus_id(myStatus);

        reimbursementDAO.save(newRmb);
        return newRmb;
    }
    //User get all method deprecated
    public List<ReimbursementResponse> getAllUserReimbursements(User authUser){
        List<Reimbursement> rmb = reimbursementDAO.getAllByID(authUser.getUser_id());
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        for (Reimbursement thisrmb : rmb){
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        return rmbResponses;
    }

    //Manager only get ALL
    public List<ReimbursementResponse> getAllReimbursements( ){
        List<Reimbursement> rmb = reimbursementDAO.getAll();
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        for (Reimbursement thisrmb : rmb){
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        return rmbResponses;
    }

    // Manager get by userID
    public List<ReimbursementResponse> getUsersReimbursements(String id){
        List<Reimbursement> rmb = reimbursementDAO.getAllByID(id);
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        for (Reimbursement thisrmb : rmb){
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        return rmbResponses;
    }

    // User get single reimbursement
    public Reimbursement getReimbursementByID(String id){
        return reimbursementDAO.getById(id);
    }

    //Manager Approve/Deny, user edit
    public void updateRemb(ReimbUpdateRequest reimbUpdate){

        Reimbursement newReimb = reimbursementDAO.getById(reimbUpdate.getReimb_id());
        if (reimbUpdate.getStatus_id().equals("APPROVED") || reimbUpdate.getStatus_id().equals("DENIED"))
            throw new InvalidRequestException("Cannot remove admin");
        if (!reimbUpdate.getStatus_id().equals("PENDING") && reimbUpdate.getAuthor_id() != null)
            throw new InvalidRequestException("Cannot update resolved ticket");

        ReimbursementType myType = reimbursementTypeDAO.getById(reimbUpdate.getType_id());
        ReimbursementStatus myStatus = reimbursementStatusDAO.getById(reimbUpdate.getStatus_id());
        User myAuthor = userDAO.getById(reimbUpdate.getAuthor_id());
        User myResolver = userDAO.getById(reimbUpdate.getResolver_id());


        //Check for any updates then prepare User to be updated
        if(reimbUpdate.getAmount() != null)
            newReimb.setAmount(reimbUpdate.getAmount());
        if(reimbUpdate.getSubmitted() != null)
            newReimb.setSubmitted(reimbUpdate.getSubmitted());
        if(reimbUpdate.getDescription() != null)
            newReimb.setDescription(reimbUpdate.getDescription());
        if(reimbUpdate.getReceipt() != null)
            newReimb.setReceipt(reimbUpdate.getReceipt());
        if(reimbUpdate.getResolver_id() != null)
            newReimb.setResolver_id(userDAO.getById(reimbUpdate.getResolver_id()));

        if(reimbUpdate.getType_id() != null )
            newReimb.setType_id(myType);
        if(reimbUpdate.getStatus_id() != null)
            newReimb.setStatus_id(myStatus);

        System.out.println(newReimb);
        reimbursementDAO.update(newReimb);
    }

    //Entered amounts should be formatted to 6.2 spaces
    public float convertAmount(float number) {
        return Float.parseFloat(String.format("%6.2f", number));
    }


}
